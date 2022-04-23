package com.app.kutumb.view.fragments

import android.Manifest
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.Telephony
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.kutumb.callback.SmsCallback
import com.app.kutumb.databinding.FragmentHomeBinding
import com.app.kutumb.databinding.FragmentMessageBinding
import com.app.kutumb.model.dataclass.MessageData
import com.app.kutumb.model.dataclass.SMS
import com.app.kutumb.utils.Constant
import com.app.kutumb.view.activity.MainActivity
import com.app.kutumb.view.adapter.MessageAdapter
import com.app.kutumb.view.adapter.SmsAdapter


class MessageFragment : BaseFragment() {

    private val TAG = "MessageFragment"
    private lateinit var binding: FragmentMessageBinding
    private var isFragmentCreated = false

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var messageAdapter: MessageAdapter

    private var messageList: MutableList<MessageData>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        isFragmentCreated = this::binding.isInitialized

        if (!isFragmentCreated) {
            binding = FragmentMessageBinding.inflate(inflater, container, false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isFragmentCreated) {
            initComponents()
        }
        callListeners()
    }

    override fun initComponents() {
        activityMainBinding = (activity as MainActivity).binding
        messageList = mutableListOf()

        linearLayoutManager = LinearLayoutManager(requireContext())
        binding.rvSmsList.layoutManager = linearLayoutManager
        linearLayoutManager.reverseLayout = true
//        linearLayoutManager.stackFromEnd = true
        messageAdapter = MessageAdapter()
        binding.rvSmsList.adapter = messageAdapter

        messageList = arguments?.getParcelableArrayList<MessageData>(Constant.KEY_MESSAGE_LIST)!!

        messageList?.let {
            messageAdapter.submitList(it)

            if(it.isNotEmpty())
                binding.tvHeading.text = it.get(0).number
        }

    }

    override fun callListeners() {

    }
}