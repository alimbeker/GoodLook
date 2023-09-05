package com.example.goodlook

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra("ToDo") ?: "Default Message"
        Log.d("ToDo", "Alarm message: $message")

        // Create and show a notification using NotificationHelper
        val notificationHelper = NotificationHelper(context)
        notificationHelper.createNotification("$message", "Deadline is coming!")
    }
}