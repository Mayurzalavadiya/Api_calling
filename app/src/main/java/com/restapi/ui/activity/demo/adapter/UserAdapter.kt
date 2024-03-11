package com.restapi.ui.activity.demo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.restapi.databinding.UserListViewBinding
import com.restapi.ui.activity.demo.pojo.response.User

class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private var list = mutableListOf<User.Datum>()


    @SuppressLint("NotifyDataSetChanged")
    fun addItem(items: List<User.Datum>?) {
        if (items != null) {
            this.list.addAll(items)
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: UserListViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: User.Datum) = with(binding) {
            Glide.with(imageViewImage).load(item.avatar).diskCacheStrategy(DiskCacheStrategy.ALL) .into(imageViewImage)
            textViewUserName.text = "${item.firstName} ${item.lastName}"
            textViewUserEmail.text = item.email
            textViewUserId.text = item.id.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            UserListViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearItems() {
        list.clear()
    }
}