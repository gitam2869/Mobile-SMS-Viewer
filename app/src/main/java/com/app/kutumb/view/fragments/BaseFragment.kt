package com.app.kutumb.view.fragments

import androidx.fragment.app.Fragment
import com.app.kutumb.databinding.ActivityMainBinding

abstract class BaseFragment : Fragment() {

    lateinit var activityMainBinding: ActivityMainBinding

    /*
    Initialize activity layout binding and other components
     */
    abstract fun initComponents()

    /*
    Call listeners for activity views and fragment views
     */
    abstract fun callListeners()

    /**
     * Could handle back press.
     * @return true if back press was handled
     */
    open fun onBackPressed(): Boolean {
        return false
    }

}