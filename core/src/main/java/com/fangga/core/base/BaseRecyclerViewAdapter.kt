package com.fangga.core.base

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseRecyclerViewAdapter<VB: ViewBinding, ListType>
    : RecyclerView.Adapter<BaseRecyclerViewAdapter<VB, ListType>.BaseViewHolder>() {

    lateinit var itemView: View

    private val itemList = arrayListOf<ListType>().toMutableList()

    protected abstract fun inflateViewBinding(
        parent: ViewGroup
    ): VB

    protected abstract val binder: (ListType, VB) -> Unit

    protected abstract val diffUtilBuilder: (List<ListType>, List<ListType>) -> DiffUtil.Callback

    private var position: Int? = null
    val size get() = itemList.size

    fun submitData(data: List<ListType>) {
        val diffCallback = diffUtilBuilder(itemList, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        itemList.apply {
            clear()
            addAll(data)
        }

        diffResult.dispatchUpdatesTo(this)
    }

    inner class BaseViewHolder(private val view: VB): RecyclerView.ViewHolder(view.root) {
        fun bind(item: ListType) {
            this@BaseRecyclerViewAdapter.itemView = itemView
            binder(item, view)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = inflateViewBinding(parent)
        return BaseViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        @SuppressLint("RecyclerView") position: Int,
    ) {
        this.position = position
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size
}