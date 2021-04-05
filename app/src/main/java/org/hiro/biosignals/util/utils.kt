package org.hiro.biosignals.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun formatTime(time: Long): String = SimpleDateFormat("MMM dd',' HH:mm").format(Date(time))
