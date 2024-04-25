package com.nbc.messenger

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.nbc.messenger.model.User
import java.util.Calendar

private const val channelId = "one-channel"
private const val channelName = "My Channel One"
private val NOTIFICATION_ID = 1000
private const val ACTION_NOTIFICATION_CLICKED = "action_notification_clicked"

fun Context.createNotificationChannel(item: User, delayMinutes: Int) {

    val calendar = Calendar.getInstance()
    calendar.add(Calendar.MINUTE, delayMinutes)


    // 알림 클릭 시 MainActivity로 돌아가기 위한 Intent 설정
    var intentItem = Intent(this, MainActivity::class.java).apply {
//            action = ACTION_NOTIFICATION_CLICKED
        putExtra("item", item)
    }
    val pendingIntent =
        PendingIntent.getActivity(
            this,
            0,
            intentItem,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )


// 알림의 기본 정보... 빌더의 옵션들
    val builder = buildNotificationChannel()

    builder.run {
        setSmallIcon(R.drawable.ic_profile_default)
        //알람 발생 시각 : 현재 시각
        setWhen(calendar.timeInMillis)
        setContentTitle("${item.name} 에게서 문자 수신")
        setContentText("카페 가는중!!")
        //setPriority(NotificationCompat.PRIORITY_DEFAULT)
        setAutoCancel(true)
        setContentIntent(pendingIntent) // 알림 클릭 시 PendingIntent 설정

        //addAction(R.mipmap.ic_launcher, "Action", pendingIntent)
    }

    //알림 실행... permission 추가하기!
    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(11, builder.build())
}


private fun Context.buildNotificationChannel() : NotificationCompat.Builder {
    val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val builder: NotificationCompat.Builder

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
        return NotificationCompat.Builder(this)
    }


    // 26 버전 이상
    checkNotificationPermission()
    //채널을 하나 만든다. NotificationChannel로.
    val channel = NotificationChannel(
        channelId,
        channelName,
        NotificationManager.IMPORTANCE_DEFAULT
    ).apply {
        // 채널에 다양한 정보 설정
        description = "My Channel One Description"

        // 빨간색으로 1, 2 뜨는거...?
        setShowBadge(true)

        //알람 울리게. 링톤
        val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        //오디오 들어가 있는거... 이건 기본 소리니까 바꾸고 싶으면 mp3 다운받아 바꾸기
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_ALARM)
            .build()

        //사운드에 오디오 넣기
        setSound(uri, audioAttributes)

        //진동 넣을건지
        enableVibration(true)
    }
    // 채널을 manager를 통해 NotificationManager에 등록
    manager.createNotificationChannel(channel)

    // 채널을 이용하여 builder 생성, builder 통해 채널 아이디 넣어줌
    return NotificationCompat.Builder(this, channelId)
}

private fun Context.checkNotificationPermission() {
    // 추가한 코드... 사용자 권한 요청 부분. 설정으로 안내함
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
            // 알림 권한이 없다면, 사용자에게 권한 요청
            // Setting 는 android.provider 로 선택하기
            val intent22 = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                putExtra(Settings.EXTRA_APP_PACKAGE, this@checkNotificationPermission.packageName)
            }
            startActivity(intent22)
        }
    }
}