package com.amanarora.notificationtest

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v4.app.TaskStackBuilder
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button

val TEST_DATA = "Test Data"


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val LOG_TAG = MainActivity::class.java.simpleName

    private var currentNotificationID = 0
    private var btnSimpleNotification: Button? = null
    private var btnNotificationTaskBuilder: Button? = null
    private var notificationManager: NotificationManager? = null
    private var notificationBuilder: NotificationCompat.Builder? = null
    private var notificationTitle: String? = null
    private var notificationText: String? = null
    private var icon: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (intent != null) {
            val data = intent.getStringExtra(TEST_DATA)
            Log.e(LOG_TAG, "Data $data")
        }
        btnSimpleNotification = findViewById(R.id.btn_notification_simple)
        btnNotificationTaskBuilder = findViewById(R.id.btn_notifcation_task)

        notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        icon = BitmapFactory.decodeResource(
            this.resources,
            R.mipmap.ic_launcher
        )
        btnSimpleNotification!!.setOnClickListener(this)
        btnNotificationTaskBuilder!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        setNotificationData()
        when (v.id) {
            R.id.btn_notification_simple -> sendNotificationSimple()
            R.id.btn_notifcation_task -> sendNotificationTaskBuilder()
        }

    }

    private fun sendNotificationSimple() {
        setupNotification()
        val nextIntent = Intent(this, Main2Activity::class.java)
        nextIntent.putExtra(TEST_DATA, "This is test data")

        val contentIntent = PendingIntent.getActivity(this, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        notificationBuilder!!.setContentIntent(contentIntent)
        val notification = notificationBuilder!!.build()
        notification.flags = notification.flags or Notification.FLAG_AUTO_CANCEL

        currentNotificationID++
        var notificationId = currentNotificationID
        if (notificationId == Integer.MAX_VALUE - 1)
            notificationId = 0

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "channel_id"
            val channel = NotificationChannel(
                channelId,
                "Channel title", NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager!!.createNotificationChannel(channel)
            notificationBuilder!!.setChannelId(channelId)
        }


        notificationManager!!.notify(notificationId, notification)
    }

    private fun sendNotificationTaskBuilder() {
        setupNotification()
        val notificationIntent = Intent(this, MainActivity::class.java)
        val nextIntent = Intent(this, Main2Activity::class.java)
        nextIntent.putExtra(TEST_DATA, "This is test data")
        val contentIntent: PendingIntent? = TaskStackBuilder.create(this).run {
            addNextIntent(notificationIntent).addNextIntent(nextIntent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        notificationBuilder!!.setContentIntent(contentIntent)
        val notification = notificationBuilder!!.build()
        notification.flags = notification.flags or Notification.FLAG_AUTO_CANCEL

        currentNotificationID++
        var notificationId = currentNotificationID
        if (notificationId == Integer.MAX_VALUE - 1)
            notificationId = 0

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "channel_id"
            val channel = NotificationChannel(
                channelId,
                "Channel title", NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager!!.createNotificationChannel(channel)
            notificationBuilder!!.setChannelId(channelId)
        }


        notificationManager!!.notify(notificationId, notification)
    }

    private fun setNotificationData() {
        notificationTitle = this.getString(R.string.app_name)
        notificationText = "Hello..This is a Notification Test"
    }

    private fun setupNotification() {
        notificationBuilder = NotificationCompat.Builder(this, "channel_id")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setLargeIcon(icon)
            .setContentTitle(notificationTitle)
            .setContentText(notificationText)
    }
}
