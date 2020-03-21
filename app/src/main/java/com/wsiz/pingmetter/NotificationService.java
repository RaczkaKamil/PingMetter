package com.wsiz.pingmetter;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.HttpsURLConnection;

public class NotificationService extends Service {
    Ping ping;
    private Toast toast;
    private Timer timer;
    private TimerTask timerTask;
    private String TOKEN;
    private NotificationManager mNotificationManager;

    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            System.out.println("connect");
            startConntect();
        }
    }



    private void writeToLogs(String message) {
        Log.d("HelloServices", message);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("OnCREATE");
        writeToLogs("Called onCreate() method.");
        timer = new Timer();
        toast = Toast.makeText(this, "PING Started", Toast.LENGTH_SHORT);
        Toast.makeText(this, "PING Started", Toast.LENGTH_SHORT).show();

    }

    private void startConntect(){
        Thread thread = new Thread(() -> {

            ping=new Ping("wsi.edu.pl",this);

            while (!ping.isDone){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if(ping.pingObiectList.getPingList().size()>0){
                ping.pingObiectList.loadPing();
                ping.pingObiectList.savePing();
                System.out.println("dlugosc: "+ping.pingObiectList.getPingList().size());
            }

            System.out.println("POBRANO PING");

        });
        thread.start();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        writeToLogs("Called onStartCommand() methond");
        System.out.println("ROZPOCZETO SERWIS");
        clearTimerSchedule();
        initTask();

        //18
        timer.scheduleAtFixedRate(timerTask, 1800000,  1800000);
        return super.onStartCommand(intent, flags, startId);
    }

    private void clearTimerSchedule() {
        if(timerTask != null) {
            timerTask.cancel();
            timer.purge();
        }
    }

    private void initTask() {
        timerTask = new MyTimerTask();
    }

    @Override
    public void onDestroy() {
        writeToLogs("Called onDestroy() method");
        clearTimerSchedule();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
