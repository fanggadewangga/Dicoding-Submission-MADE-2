package com.fangga.githubuserapp.presentation.ui.following

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fangga.core.data.Resource
import com.fangga.core.domain.model.User
import com.fangga.core.ui.adapter.UserAdapter
import com.fangga.core.base.BaseFragment
import com.fangga.githubuserapp.databinding.FragmentFollowingBinding
import com.fangga.githubuserapp.presentation.ui.detail.DetailActivity
import com.fangga.githubuserapp.utils.Constant
import com.fangga.core.utils.ScreenOrientation
import com.fangga.githubuserapp.utils.ViewStateCallback
import org.koin.androidx.viewmodel.ext.android.viewModel


class FollowingFragment : BaseFragment<FragmentFollowingBinding>() {

    companion object {
        private const val KEY_BUNDLE = "USERNAME"

        fun getInstance(username: String): Fragment {
            return FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_BUNDLE, username)
                }
            }
        }
    }

    private val viewModel: FollowingViewModel by viewModel()
    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(KEY_BUNDLE)
        }
    }

    override fun inflateViewBinding(container: ViewGroup?): FragmentFollowingBinding =
        FragmentFollowingBinding.inflate(layoutInflater, container, false)

    override fun FragmentFollowingBinding.binder() {
        val rvAdapter = UserAdapter()

        val callback = object : ViewStateCallback<List<User>> {
            override fun onLoading() {
                followingProgressBar.visibility = visible
            }

            override fun onSuccess(data: List<User>) {
                rvAdapter.submitData(data)
                rvAdapter.setOnItemClickedCallback(object : UserAdapter.OnItemClickedCallback {
                    override fun onItemClicked(user: User) {
                        val intentToDetail = Intent(this@FollowingFragment.activity, DetailActivity::class.java)
                        intentToDetail.putExtra(Constant.EXTRA_USER, user.username)
                        startActivity(intentToDetail)
                    }
                }
                )
                rvFollowing.visibility = visible
                followingProgressBar.visibility = invisible
            }

            override fun onError(message: String?) {
                followingProgressBar.visibility = invisible
                ivSearchFail.visibility = visible
                tvFind.visibility = visible
            }
        }

        rvFollowing.apply {
            adapter = rvAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        viewModel.getUserFollowing(username = username.toString()).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> callback.onError(it.message)
                is Resource.Loading -> callback.onLoading()
                is Resource.Success -> it.data?.let { it1 -> callback.onSuccess(it1) }
            }
        }

    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }
}