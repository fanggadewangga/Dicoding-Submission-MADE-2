package com.fangga.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fangga.core.ui.UserDiffUtil
import com.fangga.core.databinding.ItemListUserBinding
import com.fangga.core.domain.model.User
import com.fangga.core.base.BaseRecyclerViewAdapter

class UserAdapter : BaseRecyclerViewAdapter<ItemListUserBinding, User>() {
    override fun inflateViewBinding(parent: ViewGroup): ItemListUserBinding =
        ItemListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    private lateinit var onItemClickCallback: OnItemClickedCallback

    interface OnItemClickedCallback {
        fun onItemClicked(user: User)
    }

    fun setOnItemClickedCallback(onItemClickCallback: OnItemClickedCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override val binder: (User, ItemListUserBinding) -> Unit = { data, binding ->
        binding.apply {
            tvUsername.text = data.username
            itemView.let {
                Glide.with(it.context)
                    .load(data.avatar)
                    .apply(RequestOptions.circleCropTransform())
                    .into(binding.ivUserAvatar)
            }

            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(data)
            }
        }
    }

    override val diffUtilBuilder: (List<User>, List<User>) -> DiffUtil.Callback = { old, new ->
        UserDiffUtil(old, new)
    }
}
