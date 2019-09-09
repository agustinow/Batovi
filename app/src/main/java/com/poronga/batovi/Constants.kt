package com.poronga.batovi

import android.graphics.Color
import com.poronga.batovi.model.json.Achievement
import com.poronga.batovi.viewmodel.main.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

const val PREF_NAME: String = "sharedprefs"
const val PREF_USER: String = "user"
const val PREF_PROJECTS: String = "projects"
const val DIFF_JUNIOR: Int = 1
const val DIFF_NORMAL: Int = 2
const val DIFF_COMPLEX: Int = 3
const val DIFF_PRO: Int = 4

val colors = mutableListOf(
    (Color.parseColor("#FFCDD2")),
    (Color.parseColor("#F8BBD0")),
    (Color.parseColor("#E1BEE7")),
    (Color.parseColor("#D1C4E9")),
    (Color.parseColor("#C5CAE9")),
    (Color.parseColor("#BBDEFB")),
    (Color.parseColor("#B3E5FC")),
    (Color.parseColor("#B2EBF2")),
    (Color.parseColor("#B2DFDB")),
    (Color.parseColor("#C8E6C9")),
    (Color.parseColor("#DCEDC8")),
    (Color.parseColor("#F0F4C3")),
    (Color.parseColor("#FFF9C4")),
    (Color.parseColor("#FFECB3")),
    (Color.parseColor("#FFE0B2")),
    (Color.parseColor("#FFCCBC"))
)

fun formatDate(date: Date): String = SimpleDateFormat("MM/dd/yyyy", Locale.US).format(date)
