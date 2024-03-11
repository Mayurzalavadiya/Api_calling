package com.restapi.ui.activity.task1.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.restapi.databinding.FactListViewBinding
import com.restapi.ui.activity.task1.interfaces.ClickListener
import com.restapi.ui.activity.task1.pojo.FactData

class FactAdapter(val clickListener: ClickListener) :
    RecyclerView.Adapter<FactAdapter.ViewHolder>() {

    private var itemList = mutableListOf<FactData>()

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(list: MutableList<FactData>) {
        this.itemList = list
        notifyDataSetChanged()
    }


    inner class ViewHolder(val binding: FactListViewBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bind(item: FactData) = with(binding) {
            textViewFact.text = item.fact

            if (item.isLength ) {
                textViewFactLength.text = item.length.toString()
            }

            textViewFact.setOnClickListener {
                item.isLength = true
                itemList[adapterPosition].isLength = true
                textViewFactLength.text = item.length.toString()
            }

            if (item.text !== null) {
                textView.visibility = View.VISIBLE
                textView.text = item.text
            }

            setClickListener()
        }

        private fun setClickListener() = with(binding) {
            buttonGet.setOnClickListener(this@ViewHolder)
        }

        override fun onClick(p0: View?) {
            clickListener.onClick(adapterPosition, p0)
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FactListViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }
}