package com.wsiz.pingmetter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.Nullable;

import com.github.mikephil.charting.data.BarEntry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Ping {


    public String host;
    public boolean isConnected;
    public boolean isDone = false;
    public String response;
    public String ip = "";
    private Context ctx;
    private String netWorkType;

    public PingObiectList pingObiectList;

    public Ping(String host, Context ctx) {
        this.host = host;
        this.ctx = ctx;
        startList();
    }

    public Ping(Context ctx) {
        this.ctx = ctx;
        startList();
    }

    private void startList() {
        pingObiectList = new PingObiectList(ctx);
        Thread thread = new Thread(() -> {
            try {
                for (int i = 0; i < 4; i++) {
                    isConnected = executeCommand();
                    Thread.sleep(250);
                }

                isDone = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public boolean executeCommand() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process mIpAddrProcess = runtime.exec("/system/bin/ping -c 1 " + host);
            int mExitValue = mIpAddrProcess.waitFor();

            BufferedReader r = new BufferedReader(new InputStreamReader(mIpAddrProcess.getInputStream()));
            StringBuilder total = new StringBuilder();
            for (String line; (line = r.readLine()) != null; ) {
                total.append(line).append('\n');
            }
            String[] split = total.toString().split(":");
            String[] split2 = split[1].split("-");

            this.response = split2[0];


            convertPingTimeToObiect(this.response);

            this.netWorkType = getNetworkType();
            return mExitValue == 0;
        } catch (InterruptedException | IOException io) {
            io.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException ar) {
            ar.fillInStackTrace();
        }
        return false;

    }

    private void convertPingTimeToObiect(String response) {
        String split[] = response.split("=");
        Double time = Double.parseDouble(split[3].replace("ms", ""));
        Date dateBasic = new Date();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format1 = new SimpleDateFormat("HH", Locale.getDefault());
        String date1 = format1.format(dateBasic);

        PingObiect obiect = new PingObiect(String.valueOf(time), date1, getHost());
        pingObiectList.add(obiect);

    }

    @Nullable
    private String getNetworkType() {
        ConnectivityManager cm =
                (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            return activeNetwork.getTypeName();
        }
        return null;
    }


    public String getNetWorkType() {
        return netWorkType;
    }

    public String getHost() {
        return host;
    }


    public String getResponse() {
        return response;
    }

}