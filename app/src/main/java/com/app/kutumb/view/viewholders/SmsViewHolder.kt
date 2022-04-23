package com.app.kutumb.view.viewholders

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.kutumb.R
import com.app.kutumb.callback.SmsCallback
import com.app.kutumb.databinding.ItemSmsBinding
import com.app.kutumb.model.dataclass.SMS

class SmsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val TAG = "LanguageViewHolder"
    val itemLanguageBinding = ItemSmsBinding.bind(itemView)

    @SuppressLint("SetTextI18n")
    fun bind(
        holder: RecyclerView.ViewHolder,
        position: Int,
        sms: SMS,
        smsCallback: SmsCallback
    ) {

        itemLanguageBinding.run {
            tvAddress.text = sms.messageData.number
            tvLastMessage.text = sms.messageData.message

            cvSms.setOnClickListener {
                smsCallback.onSmsClick(
                    holder.adapterPosition,
                    sms.list
                )
            }
        }

    }

    companion object {
        fun createViewHolder(parent: ViewGroup): SmsViewHolder {
            val view: View =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_sms, parent, false)
            return SmsViewHolder(view)
        }
    }
}