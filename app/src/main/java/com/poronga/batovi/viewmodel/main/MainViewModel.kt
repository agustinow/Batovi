package com.poronga.batovi.viewmodel.main

import androidx.lifecycle.ViewModel
import com.poronga.batovi.model.json.Project

class MainViewModel: ViewModel() {
    var newProjectDifficulty: Int = 0
    var selectedFrag = 1
    var heartbeatSpeedLevel = 10f
    var projecto:Project?= null
}