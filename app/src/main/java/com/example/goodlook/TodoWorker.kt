package com.example.goodlook

import android.content.Context


class TodoWorker(val context: Context, val params: WorkerParameters): Worker(context,params) {
    override fun doWork(): Result {
        NotificationHelper(context)
            .createNotification(inputData.getString("title").toString(),
                inputData.getString("message").toString())

        return Result.success()
    }
}
