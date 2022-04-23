package com.app.kutumb.view.adapter

import android.provider.Telephony
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.kutumb.model.dataclass.MessageData
import com.app.kutumb.model.dataclass.SMS
import com.app.kutumb.view.viewholders.MessageReceiveViewHolder
import com.app.kutumb.view.viewholders.MessageSentViewHolder


class MessageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = "LanguageAdapter"

    private val differCallback = object : DiffUtil.ItemCallback<MessageData>() {
        override fun areItemsTheSame(
            oldItem: MessageData,
            newItem: MessageData
        ): Boolean {
            return oldItem.threadId == newItem.threadId
        }

        override fun areContentsTheSame(
            oldItem: MessageData,
            newItem: MessageData
        ): Boolean {
            return oldItem === newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (Telephony.Sms.MESSAGE_TYPE_SENT == viewType)
            MessageSentViewHolder.createViewHolder(parent)
        else MessageReceiveViewHolder.createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is MessageSentViewHolder) {
            val messageSentViewHolder = holder
            messageSentViewHolder.bind(
                holder,
                position,
                differ.currentList.get(position)
            )
        } else {
            val messageReceiveViewHolder = holder as MessageReceiveViewHolder
            messageReceiveViewHolder.bind(
                holder,
                position,
                differ.currentList.get(position)
            )
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return differ.currentList.get(position).type
    }

    fun submitList(list: List<MessageData>) {
        differ.submitList(list)
    }
}