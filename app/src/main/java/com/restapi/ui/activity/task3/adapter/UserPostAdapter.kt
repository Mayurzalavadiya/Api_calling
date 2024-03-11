package com.restapi.ui.activity.task3.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.restapi.databinding.UserPostListViewBinding
import com.restapi.ui.activity.task3.interfaces.ClickListener
import com.restapi.ui.activity.task3.pojo.response.UserPost
import com.restapi.ui.activity.task3.pojo.response.UserPosts

class UserPostAdapter(val clickListener: ClickListener) :
    RecyclerView.Adapter<UserPostAdapter.ViewHolder>() {

    private val postList = mutableListOf<UserPost>()
    val filterPostAdapter by lazy { FilterPostAdapter() }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(items: MutableList<UserPost>) {
        this.postList.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: UserPostListViewBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        var id: Int? = null


        fun bind(userId: UserPost) = with(binding) {
            textViewID.text = userId.userId.toString()
            itemView.setOnClickListener {
                recyclerViewUserPostList.visibility = View.VISIBLE
                id = userId.userId
                onClick(textViewID)
            }
        }

        private fun setAdapter(userPosts: MutableList<UserPosts.UserPostsItem>?) = with(binding) {
            recyclerViewUserPostList.adapter = filterPostAdapter
            recyclerViewUserPostList.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
            filterPostAdapter.addItem(userPosts)
        }

        override fun onClick(v: View?) {
            clickListener.onClick(id, adapterPosition, v)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            UserPostListViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = postList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(postList[position])
    }


    fun setFilterPostList(userPosts: MutableList<UserPosts.UserPostsItem>) {
        filterPostAdapter.addItem(userPosts)
    }
}