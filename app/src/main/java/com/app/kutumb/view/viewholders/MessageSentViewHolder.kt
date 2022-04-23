package com.app.kutumb.view.viewholders

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.kutumb.R
import com.app.kutumb.callback.SmsCallback
import com.app.kutumb.databinding.ItemSmsBinding
import com.app.kutumb.databinding.ItemSmsSentBinding
import com.app.kutumb.model.dataclass.MessageData
import com.app.kutumb.model.dataclass.SMS
import com.app.kutumb.utils.DateTimeUtility

class MessageSentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val TAG = "LanguageViewHolder"
    val itemLanguageBinding = ItemSmsSentBinding.bind(itemView)

    @SuppressLint("SetTextI18n")
    fun bind(
        holder: RecyclerView.ViewHolder,
        position: Int,
        messageData: MessageData
    ) {

        itemLanguageBinding.run {
            tvAddress.text = messageData.message
            tvDate.text = DateTimeUtility.getDateFromTime(DateTimeUtility.SMS_DATE_PATTERN, messageData.time)
        }

    }

    companion object {
        fun createViewHolder(parent: ViewGroup): MessageSentViewHolder {
            val view: View =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_sms_sent, parent, false)
            return MessageSentViewHolder(view)
        }
    }
}