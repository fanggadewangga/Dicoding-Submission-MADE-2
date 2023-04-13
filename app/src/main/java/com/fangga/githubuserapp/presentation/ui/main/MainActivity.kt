package com.fangga.githubuserapp.presentation.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.fangga.core.data.Resource
import com.fangga.core.domain.model.User
import com.fangga.core.ui.adapter.UserAdapter
import com.fangga.githubuserapp.R
import com.fangga.githubuserapp.databinding.ActivityMainBinding
import com.fangga.githubuserapp.presentation.ui.detail.DetailActivity
import com.fangga.githubuserapp.presentation.ui.setting.SettingActivity
import com.fangga.githubuserapp.utils.Constant.EXTRA_USER
import com.fangga.githubuserapp.utils.ViewStateCallback
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var callback: ViewStateCallback<List<User>>
    private val viewModel: MainViewModel by viewModel()
    private lateinit var userQuery: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val rvAdapter = UserAdapter()
        binding.apply {
            callback = object : ViewStateCallback<List<User>> {
                override fun onLoading() {
                    includeMain.apply {
                        rvListUser.visibility = invisible
                        tvFind.visibility = invisible
                        ivFind.visibility = invisible
                        progressBar.visibility = visible
                    }
                }

                override fun onSuccess(data: List<User>) {
                    rvAdapter.submitData(data)
                    rvAdapter.setOnItemClickedCallback(object : UserAdapter.OnItemClickedCallback {
                        override fun onItemClicked(user: User) {
                            val intentToDetail =
                                Intent(this@MainActivity, DetailActivity::class.java)
                            intentToDetail.putExtra(EXTRA_USER, user.username)
                            startActivity(intentToDetail)
                        }
                    }
                    )
                    includeMain.apply {
                        rvListUser.visibility = visible
                        tvFind.visibility = invisible
                        ivFind.visibility = invisible
                        progressBar.visibility = invisible
                    }
                    includeMain.rvListUser.apply {
                        adapter = rvAdapter
                        layoutManager =
                            LinearLayoutManager(
                                this@MainActivity,
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                    }
                }

                override fun onError(message: String?) {
                    includeMain.apply {
                        rvListUser.visibility = invisible
                        tvFind.visibility = visible
                        tvFind.text = getString(R.string.search_cant_find)
                        ivFind.visibility = visible
                        ivFind.setImageResource(R.drawable.search_fail)
                        progressBar.visibility = invisible
                    }
                }
            }

            searchView.apply {
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        userQuery = query.toString()
                        clearFocus()
                        viewModel.searchUsers(query = userQuery).observe(this@MainActivity) {
                            when (it) {
                                is Resource.Error -> callback.onError(it.message)
                                is Resource.Loading -> callback.onLoading()
                                is Resource.Success -> it.data?.let { it1 -> callback.onSuccess(it1) }
                            }
                        }
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return false
                    }
                })
            }

            btnFavorite.setOnClickListener {
                val uri = Uri.parse("githubuserapp://favorite")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }

            btnSettings.setOnClickListener {
                val intent = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(intent)
            }

            supportActionBar?.hide()
        }
    }
}