package com.poronga.batovi.viewmodel.project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.poronga.batovi.model.json.Project

class ProjectViewModel: ViewModel() {
    var project: Project? = null

    //no me acuerdo que verga era esto xD
    val isBusy: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

}