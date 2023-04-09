package com.fangga.favorite.favorite

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.fangga.core.base.BaseActivity
import com.fangga.core.domain.model.User
import com.fangga.core.ui.adapter.UserAdapter
import com.fangga.core.utils.ScreenOrientation
import com.fangga.favorite.databinding.ActivityFavoriteBinding
import com.fangga.favorite.di.favoriteModule
import com.fangga.githubuserapp.presentation.ui.detail.DetailActivity
import com.fangga.githubuserapp.utils.Constant
import com.fangga.githubuserapp.utils.ViewStateCallback
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext.loadKoinModules


@Suppress("DEPRECATION")
class FavoriteActivity : BaseActivity<ActivityFavoriteBinding>() {
    private val viewModel: FavoriteViewModel by viewModel()
    private val rvAdapter = UserAdapter()

    override fun inflateViewBinding(): ActivityFavoriteBinding {
        return ActivityFavoriteBinding.inflate(layoutInflater)
    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }

    override fun ActivityFavoriteBinding.binder() {
        loadKoinModules(favoriteModule)

        rvFavorite.apply {
            adapter = rvAdapter
            layoutManager =
                LinearLayoutManager(this@FavoriteActivity, LinearLayoutManager.VERTICAL, false)
        }

        viewModel.getFavorites().observe(this@FavoriteActivity) {
            if (!it.isNullOrEmpty()) callback.onSuccess(it)
            else if (it.isNullOrEmpty()) callback.onError("No favorites")
            else callback.onLoading()
        }

        supportActionBar?.hide()
    }

    private val callback = object : ViewStateCallback<List<User>> {
        override fun onLoading() {
            binding.apply {
                favoriteProgressBar.visibility = visible
                rvFavorite.visibility = invisible
                ivEmpty.visibility = invisible
                tvEmpty.visibility = invisible
            }
        }

        override fun onSuccess(data: List<User>) {
            binding.apply {
                rvAdapter.submitData(data)
                rvAdapter.setOnItemClickedCallback(object : UserAdapter.OnItemClickedCallback {
                    override fun onItemClicked(user: User) {
                        val intentToDetail = Intent(this@FavoriteActivity, DetailActivity::class.java)
                        intentToDetail.putExtra(Constant.EXTRA_USER, user.username)
                        startActivity(intentToDetail)
                    }
                }
                )
                rvFavorite.visibility = visible
                ivEmpty.visibility = invisible
                tvEmpty.visibility = invisible
                favoriteProgressBar.visibility = invisible
            }
        }

        override fun onError(message: String?) {
            binding.apply {
                favoriteProgressBar.visibility = invisible
                rvFavorite.visibility = invisible
                ivEmpty.visibility = visible
                tvEmpty.visibility = visible
            }
        }

    }

    override fun onResume() {
        viewModel.getFavorites().observe(this@FavoriteActivity) {
            if (!it.isNullOrEmpty()) callback.onSuccess(it)
            else if (it.isNullOrEmpty()) callback.onError("No favorites")
            else callback.onLoading()
        }
        super.onResume()
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
}