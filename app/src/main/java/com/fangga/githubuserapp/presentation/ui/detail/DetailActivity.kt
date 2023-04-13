package com.fangga.githubuserapp.presentation.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fangga.core.data.Resource
import com.fangga.core.domain.model.User
import com.fangga.githubuserapp.R
import com.fangga.githubuserapp.databinding.ActivityDetailBinding
import com.fangga.githubuserapp.presentation.ui.follower.FollowerFragment
import com.fangga.githubuserapp.presentation.ui.following.FollowingFragment
import com.fangga.githubuserapp.utils.Constant.EXTRA_USER
import com.fangga.githubuserapp.utils.Constant.TAB_TITLES
import com.fangga.githubuserapp.utils.ViewStateCallback
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel


@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var callback: ViewStateCallback<User>
    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            callback = object : ViewStateCallback<User> {
                fun addOrRemoveFavorite(user: User) {
                    if (user.isFavorite == true) {
                        user.isFavorite = false
                        viewModel.deleteFavoritedUser(user)
                        Toast.makeText(
                            this@DetailActivity,
                            "Delete ${user.username} from Favorite",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        user.isFavorite = true
                        viewModel.insertFavoritedUser(user)
                        Toast.makeText(
                            this@DetailActivity,
                            "Insert ${user.username} to Favorite",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                fun changedFavorite(statusFavorite: Boolean) {
                    if (statusFavorite)
                        btnFavorite.setBackgroundResource(R.drawable.favorite)
                    else
                        btnFavorite.setBackgroundResource(R.drawable.unfavorite)
                }

                override fun onLoading() {
                    makeInvisible()
                    progressBar.visibility = visible
                }

                @SuppressLint("SetTextI18n")
                override fun onSuccess(data: User) {
                    makeVisible()

                    data.username?.let {
                        viewModel.getDetailState(it)?.observe(
                            this@DetailActivity
                        ) { user ->
                            data.isFavorite = user.isFavorite == true
                            user.isFavorite?.let { it1 -> changedFavorite(it1) }
                        }
                    }

                    if (data.company == null) {
                        ivCompany.visibility = View.GONE
                        tvCompany.visibility = View.GONE
                    }

                    if (data.location == null) {
                        ivLocation.visibility = View.GONE
                        tvLocation.visibility = View.GONE
                    }

                    tvUsername.text = data.username
                    tvName.text = data.name
                    tvCompany.text = data.company
                    tvLocation.text = data.location
                    tvRepository.text = "${data.repository} Repositories"
                    tvFollower.text = "${data.follower} Followers"
                    tvFollowing.text = "${data.following} Following"
                    if (data.isFavorite!!)
                        btnFavorite.setBackgroundResource(R.drawable.favorite)
                    else
                        btnFavorite.setBackgroundResource(R.drawable.unfavorite)
                    btnFavorite.visibility = visible

                    Glide.with(this@DetailActivity)
                        .load(data.avatar)
                        .apply(RequestOptions.circleCropTransform())
                        .into(ivUserAvatar)

                    ivCantLoad.visibility = invisible
                    tvCantLoad.visibility = invisible
                    progressBar.visibility = invisible

                    btnFavorite.setOnClickListener {
                        addOrRemoveFavorite(data)
                        changedFavorite(data.isFavorite!!)
                    }
                }

                override fun onError(message: String?) {
                    makeInvisible()
                    progressBar.visibility = invisible
                }

                fun makeInvisible() {
                    tvUsername.visibility = View.INVISIBLE
                    tvName.visibility = View.INVISIBLE
                    tvCompany.visibility = View.INVISIBLE
                    tvLocation.visibility = View.INVISIBLE
                    tvRepository.visibility = View.INVISIBLE
                    tvFollower.visibility = View.INVISIBLE
                    tvFollowing.visibility = View.INVISIBLE
                    ivCompany.visibility = View.GONE
                    ivLocation.visibility = View.GONE
                    ivUserAvatar.visibility = View.GONE
                    ivFollower.visibility = View.GONE
                    ivFollowing.visibility = View.GONE
                    ivRepository.visibility = View.GONE
                    ivCantLoad.visibility = View.INVISIBLE
                    tvCantLoad.visibility = View.INVISIBLE
                    tabs.visibility = invisible
                    btnFavorite.visibility = invisible
                }

                fun makeVisible() {
                    tvUsername.visibility = View.VISIBLE
                    tvName.visibility = View.VISIBLE
                    tvCompany.visibility = View.VISIBLE
                    tvLocation.visibility = View.VISIBLE
                    tvRepository.visibility = View.VISIBLE
                    tvFollower.visibility = View.VISIBLE
                    tvFollowing.visibility = View.VISIBLE
                    ivCompany.visibility = View.VISIBLE
                    ivLocation.visibility = View.VISIBLE
                    ivUserAvatar.visibility = View.VISIBLE
                    ivFollower.visibility = View.VISIBLE
                    ivFollowing.visibility = View.VISIBLE
                    ivRepository.visibility = View.VISIBLE
                    ivCantLoad.visibility = View.VISIBLE
                    tvCantLoad.visibility = View.VISIBLE
                    tabs.visibility = visible
                    btnFavorite.visibility = visible
                }
            }
        }

        val username = intent.getStringExtra(EXTRA_USER)

        if (username != null) {
            viewModel.getUserDetail(username).observe(this@DetailActivity) {
                when (it) {
                    is Resource.Loading -> callback.onLoading()
                    is Resource.Error -> callback.onError(it.message)
                    is Resource.Success -> it.data?.let { it1 -> callback.onSuccess(it1) }
                }
            }
        }

        val pagerAdapter = FollowPagerAdapter(this@DetailActivity, username.toString())
        binding.viewPager.adapter = pagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tabs, position ->
            tabs.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = username
            elevation = 0f
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    inner class FollowPagerAdapter(activity: AppCompatActivity, private val username: String) :
        FragmentStateAdapter(activity) {
        override fun getItemCount(): Int {
            return TAB_TITLES.size
        }

        override fun createFragment(position: Int): Fragment {
            var fragment: Fragment? = null
            when (position) {
                0 -> fragment = FollowerFragment.getInstance(username)
                1 -> fragment = FollowingFragment.getInstance(username)
            }
            return fragment as Fragment
        }
    }
}