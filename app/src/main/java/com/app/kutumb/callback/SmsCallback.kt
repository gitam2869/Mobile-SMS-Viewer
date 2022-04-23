package com.app.kutumb.callback

import com.app.kutumb.model.dataclass.MessageData

interface SmsCallback {
    fun onSmsClick(position : Int, list: List<MessageData>)
}