package com.app.kutumb.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.kutumb.callback.SmsCallback
import com.app.kutumb.model.dataclass.SMS
import com.app.kutumb.view.viewholders.SmsViewHolder


class SmsAdapter(val smsCallback: SmsCallback) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = "LanguageAdapter"

    private val differCallback = object : DiffUtil.ItemCallback<SMS>() {
        override fun areItemsTheSame(
            oldItem: SMS,
            newItem: SMS
        ): Boolean {
            return oldItem.messageData.threadId == newItem.messageData.threadId
        }

        override fun areContentsTheSame(
            oldItem: SMS,
            newItem: SMS
        ): Boolean {
            return oldItem === newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SmsViewHolder.createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val languageViewHolder = holder as SmsViewHolder
        languageViewHolder.bind(
            holder,
            position,
            differ.currentList.get(position),
            smsCallback
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    fun submitList(list: List<SMS>) {
        differ.submitList(list)
    }
}