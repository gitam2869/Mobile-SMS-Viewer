package com.app.kutumb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.kutumb.model.repository.KutumbRepository
import com.app.kutumb.utils.LoadingState

class KutumbViewModel(private val kutumbRepository: KutumbRepository) {

    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState

}