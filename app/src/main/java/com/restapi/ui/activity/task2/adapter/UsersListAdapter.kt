package com.restapi.ui.activity.task2.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.restapi.databinding.UserHorizontalListViewBinding
import com.restapi.ui.activity.task2.interfaces.ClickListenerPosition
import com.restapi.ui.activity.task2.pojo.response.UsersList

class UsersListAdapter(val clickListenerPosition: ClickListenerPosition) :
    RecyclerView.Adapter<UsersListAdapter.ViewHolder>() {

    private val usersList = mutableListOf<UsersList.Data?>()

    @SuppressLint("NotifyDataSetChanged")
    fun addUser(items: List<UsersList.Data?>) {
        this.usersList.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: UserHorizontalListViewBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        var id: Int? = null
        fun bind(item: UsersList.Data) = with(binding) {
            constraintLayout.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor(item.color))
            textViewUserName.text = item.name
            textViewUserId.text = item.id.toString()
            textViewUserYear.text = item.year.toString()
            textViewUserPantoneValue.text = item.pantoneValue

            itemView.setOnClickListener {
                id = item.id
                onClick(textViewUserId)
            }
        }

        override fun onClick(p0: View?) {
            clickListenerPosition.onClick(id, p0)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            UserHorizontalListViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = usersList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        usersList[position]?.let { holder.bind(it) }
    }

    fun clearData() {
        usersList.clear()
    }
}