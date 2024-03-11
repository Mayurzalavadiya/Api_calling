package com.restapi.ui.activity.demo2.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.restapi.R
import com.restapi.databinding.UserHorizontalListViewBinding
import com.restapi.databinding.UserListViewBinding
import com.restapi.ui.activity.demo2.pojo.SampleData
import com.squareup.picasso.Picasso

class PaginationAdapter : RecyclerView.Adapter<PaginationAdapter.ViewHolder>() {
    private var list = mutableListOf<SampleData.User>()

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(items: List<SampleData.User>) {
        this.list.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: UserHorizontalListViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: SampleData.User) = with(binding) {
            Glide.with(imageViewImage).load(item.profilePicture)
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(imageViewImage)
//            Picasso.get().load(item.profilePicture).into(imageViewImage);
            textViewUserName.text = "${item.firstName} ${item.lastName}"
            textViewUserPantoneValue.text = item.email
            textViewUserYear.text = item.city
            textViewUserId.setTextColor(Color.parseColor("#000000"))
            textViewUserId.text = item.id.toString()
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

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

}