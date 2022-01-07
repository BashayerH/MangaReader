package com.tuwaiq.mangareader

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.BADGE_ICON_NONE
import androidx.core.app.NotificationCompat.BADGE_ICON_SMALL
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters


private const val TAG = "WorkManager"
class WorkManager(context: Context,workerParameters: WorkerParameters):Worker(context,workerParameters) {
    override fun doWork(): Result {

        Log.e(TAG , "from the worker to us")

        val msg = inputData.getString("in the fancy words")
        showNotification("welcom to the app",msg.toString())

        return Result.success()
    }

    private fun showNotification(title:String,desc:String) {

        val notifiManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val  notifChannel = NotificationChannel("1","channel",NotificationManager.IMPORTANCE_DEFAULT)
        notifiManager.createNotificationChannel(notifChannel)

        val builder = NotificationCompat.Builder(applicationContext,"cahnnel_id")
            .setContentTitle("just test")
            .setContentText("try to notify")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_launcher_background)

        with(NotificationManagerCompat.from(applicationContext)){
            notify(1,builder.build())


        }


    }

    companion object{
        const val  NOTIFICATION_ACTION = "com.tuwaiq.photosgallery.SHOW_NOTIFICATION"
        const val  NOTIFICATION_PERM = "com.tuwaiq.photogallery.PRIVATE"
        const val NOTIFICATION_KEY = "NOTIFICATION_KEY"
    }


}