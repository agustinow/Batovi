package com.poronga.batovi

import java.text.SimpleDateFormat
import java.util.*

const val PREF_NAME: String = "sharedprefs"
const val PREF_USER: String = "user"
const val PREF_PROJECTS: String = "projects"

fun formatDate(date: Date): String = SimpleDateFormat("MM/dd/yyyy", Locale.US).format(date)