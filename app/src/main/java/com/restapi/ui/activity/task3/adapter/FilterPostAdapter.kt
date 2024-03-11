package com.restapi.ui.activity.task3.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.restapi.databinding.UserFilterPostListViewBinding
import com.restapi.ui.activity.task3.pojo.response.UserPosts

class FilterPostAdapter :
    RecyclerView.Adapter<FilterPostAdapter.ViewHolder>() {
    private val postList = mutableListOf<UserPosts.UserPostsItem>()


    @SuppressLint("NotifyDataSetChanged")
    fun addItem(items: MutableList<UserPosts.UserPostsItem>?) {
        Log.e("TAG", "addItem: ${Gson().toJson(items)}", )
        Log.e("TAG", "addItem: ${items?.size}", )
        this.postList.clear()
        if (items != null) {
            this.postList.addAll(items)
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: UserFilterPostListViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserPosts.UserPostsItem) = with(binding) {
            textViewTitle.text = user.title
            textViewBody.text = user.body
        }


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(postList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            UserFilterPostListViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun getItemCount(): Int = postList.size

}