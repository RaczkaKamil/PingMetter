package com.wsiz.pingmetter;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PingObiect {
    private String time;
    private String date;
    private String host;



    public PingObiect(String time, String date,String host) {
        this.time = time;
        this.date = date;
        this.host = host;
    }

    public Double getTime() {
        return Double.valueOf(time);
    }

    public String getDate() {
        return date;
    }

    public String getHost() {
        return host;
    }

    @Override
    public String toString() {
        return (time)+"~~"+date+"~~"+host;
    }
}
