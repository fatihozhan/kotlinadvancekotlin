package com.example.lleriandroid

import android.app.LocaleConfig
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.example.lleriandroid.ui.theme.LleriAndroidTheme
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LleriAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Sayfa()
                }
            }
        }
    }
}

@Composable
fun Sayfa() {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { bildirimOlustur(context) }) {
            Text(text = "Bildirim Oluştur")
        }
        Button(onClick = {
/*            val calismaKosulu = Constraints
                .Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val istek = OneTimeWorkRequestBuilder<MyWorker>()
                .setInitialDelay(3, TimeUnit.SECONDS)
                .setConstraints(calismaKosulu)
                .build()
            WorkManager.getInstance(
                context
            ).enqueue(istek)*/

            val istek =
                PeriodicWorkRequestBuilder<MyWorkerBildirim>(15, TimeUnit.SECONDS)
                    .setInitialDelay(10, TimeUnit.SECONDS)
                    .build()
            WorkManager.getInstance(context).enqueue(istek)
        }) {
            Text(text = "YAP")
        }
    }
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LleriAndroidTheme {
        Sayfa()
    }
}