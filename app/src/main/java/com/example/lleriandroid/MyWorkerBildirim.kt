package com.example.lleriandroid

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorkerBildirim(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        bildirimOlustur(applicationContext)
        return Result.success()
    }
    fun bildirimOlustur(context: Context) {
        val builder: NotificationCompat.Builder
        val bildirimYoneticisi =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val kanalId = "kanalId"
            val kanalAdi = "kanalAdi"
            val kanalTani = "kanalTani"
            val kanalOnceligi = NotificationManager.IMPORTANCE_HIGH

            var kanal: NotificationChannel? = bildirimYoneticisi.getNotificationChannel(kanalId)

            if (kanal == null) {
                kanal = NotificationChannel(kanalId, kanalAdi, kanalOnceligi)
                kanal.description = kanalTani
                bildirimYoneticisi.createNotificationChannel(kanal)
            }
            builder = NotificationCompat.Builder(context, kanalId)
            builder.setContentTitle("Başlık")
                .setContentText("İçerik")
                .setSmallIcon(R.drawable.resim)
                .setAutoCancel(true)
        } else {
            builder = NotificationCompat.Builder(context)
            builder.setContentTitle("Başlık")
                .setContentText("İçerik")
                .setSmallIcon(R.drawable.resim)
                .setAutoCancel(true).priority = Notification.PRIORITY_HIGH
        }
        bildirimYoneticisi.notify(1, builder.build())

    }
}