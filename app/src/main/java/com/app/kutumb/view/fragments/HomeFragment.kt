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
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.kutumb.R
import com.app.kutumb.callback.SmsCallback
import com.app.kutumb.databinding.FragmentHomeBinding
import com.app.kutumb.model.dataclass.MessageData
import com.app.kutumb.model.dataclass.SMS
import com.app.kutumb.utils.Constant
import com.app.kutumb.view.activity.MainActivity
import com.app.kutumb.view.adapter.SmsAdapter


class HomeFragment : BaseFragment() {

    private val TAG = "HomeFragment"
    private lateinit var binding: FragmentHomeBinding
    private var isFragmentCreated = false

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var smsAdapter: SmsAdapter

    private lateinit var smsList: MutableList<SMS>
    private lateinit var smsHashMap: LinkedHashMap<Int, MutableList<MessageData>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        isFragmentCreated = this::binding.isInitialized

        if (!isFragmentCreated) {
            binding = FragmentHomeBinding.inflate(inflater, container, false)
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
        hideAllUi()
        hidePermissionAskUI()

        activityMainBinding = (activity as MainActivity).binding
        smsList = mutableListOf()
        smsHashMap = linkedMapOf()

        linearLayoutManager = LinearLayoutManager(requireContext())
        binding.rvSmsList.layoutManager = linearLayoutManager

        smsAdapter = SmsAdapter(object : SmsCallback{
            override fun onSmsClick(position: Int, list: List<MessageData>) {
                Log.d(TAG, "onSmsClick: "+position)

                val bundle = bundleOf(
                    Constant.KEY_MESSAGE_LIST to list
                )

                NavHostFragment.findNavController(this@HomeFragment).navigate(R.id.MessageFragment, bundle)
            }
        })

        binding.rvSmsList.adapter = smsAdapter

        checkRequiredPersmissions()

    }

    override fun callListeners() {
        binding.btnAllow.setOnClickListener {
            checkRequiredPersmissions()
        }
    }

    private fun checkRequiredPersmissions() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_SMS
            ) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.RECEIVE_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            hideAllUi()
            showPermissionAskUI()
            requestPermissions(
                arrayOf(
                    Manifest.permission.READ_SMS,
                    Manifest.permission.RECEIVE_SMS
                ),
                Constant.MY_PERMISSIONS_REQUEST_SMS
            )
        } else {
            getAllSms()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        val granted = checkPermissionGranted(
            requestCode,
            permissions,
            grantResults
        )

        if (granted) {
            getAllSms()
        }else{
            hideAllUi()
            showPermissionAskUI()
        }

    }

    private fun checkPermissionGranted(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ): Boolean {
        when (requestCode) {
            Constant.MY_PERMISSIONS_REQUEST_SMS -> {
                // If request is cancelled, the result arrays are empty.
                return (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            }
        }
        return false
    }


    fun getAllSms() {
        hideAllUi()
        hidePermissionAskUI()
        showProgressbar()

        val cr: ContentResolver = requireActivity().contentResolver
        val c: Cursor? = cr.query(Telephony.Sms.CONTENT_URI, null, null, null, null)
        var totalSMS = 0
        if (c != null) {
            totalSMS = c.count
            if (c.moveToFirst()) {
                for (j in 0 until totalSMS) {

                    val threadId: Int = c.getInt(c.getColumnIndexOrThrow(Telephony.Sms.THREAD_ID))
                    val date: Long = c.getLong(c.getColumnIndexOrThrow(Telephony.Sms.DATE))
                    val number: String = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.ADDRESS))
                    val body: String = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.BODY))
                    val type: Int = c.getInt(c.getColumnIndexOrThrow(Telephony.Sms.TYPE))

                    val messageData = MessageData(
                        threadId,
                        number,
                        body,
                        type,
                        date
                    )

                    if (smsHashMap.containsKey(threadId)) {
                        smsHashMap[threadId]?.add(messageData)
                    } else {
                        val list = mutableListOf<MessageData>()
                        list.add(messageData)
                        smsHashMap[threadId] = list
                    }

                    c.moveToNext()
                }
            }
            c.close()
        } else {
            Toast.makeText(requireContext(), "No message to show!", Toast.LENGTH_SHORT).show()
        }

        for ((key, value) in smsHashMap) {
            smsList.add(
                SMS(
                    value[0],
                    value
                )
            )
        }

        smsAdapter.submitList(smsList)
        showHeading()
        showRecyclerview()
        hideProgressbar()
        hidePermissionAskUI()
        Log.d(TAG, "getAllSms: "+smsList.size)
    }
    
    private fun showProgressbar(){
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressbar(){
        binding.progressBar.visibility = View.GONE
    }

    private fun showHeading(){
        binding.tvHeading.visibility = View.VISIBLE
    }

    private fun hideHeading(){
        binding.tvHeading.visibility = View.GONE
    }

    private fun showRecyclerview(){
        binding.rvSmsList.visibility = View.VISIBLE
    }

    private fun hideRecyclerView(){
        binding.rvSmsList.visibility = View.GONE
    }

    private fun showPermissionAskUI(){
        binding.btnAllow.visibility = View.VISIBLE
        binding.tvMessage.visibility = View.VISIBLE
    }

    private fun hidePermissionAskUI(){
        binding.btnAllow.visibility = View.GONE
        binding.tvMessage.visibility = View.GONE
    }
    
    private fun hideAllUi(){
        hideProgressbar()
        hideHeading()
        hideRecyclerView()
    }

}