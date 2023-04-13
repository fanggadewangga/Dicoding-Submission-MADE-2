package com.fangga.favorite.favorite

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fangga.core.domain.model.User
import com.fangga.core.ui.adapter.UserAdapter
import com.fangga.favorite.databinding.ActivityFavoriteBinding
import com.fangga.favorite.di.favoriteModule
import com.fangga.githubuserapp.presentation.ui.detail.DetailActivity
import com.fangga.githubuserapp.utils.Constant
import com.fangga.githubuserapp.utils.ViewStateCallback
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext.loadKoinModules


@Suppress("DEPRECATION")
class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var callback: ViewStateCallback<List<User>>
    private val viewModel: FavoriteViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(favoriteModule)
        val rvAdapter = UserAdapter()
        callback = object : ViewStateCallback<List<User>> {
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

        binding.apply {
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