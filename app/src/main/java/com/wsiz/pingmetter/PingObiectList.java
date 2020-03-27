package com.wsiz.pingmetter;

import android.annotation.SuppressLint;
import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PingObiectList {
    ArrayList<PingObiect> PingListHour;
    ArrayList<PingObiect> pingList;
    Context ctx;

    public PingObiectList(Context ctx) {
        this.ctx = ctx;
        pingList = new ArrayList<>();
        PingListHour = new ArrayList<>();
    }

    public void add(PingObiect pingObiect) {
        pingList.add(pingObiect);
    }

    public double getMax() {
        Double max = 0.0;
        try {
            for (int i = 0; i < pingList.size(); i++) {
                if (pingList.get(i).getTime() > max) {
                    max = pingList.get(i).getTime();
                }
            }

        } catch (NullPointerException e) {
            e.fillInStackTrace();
        }
        return max;
    }

    public double getMin() {
        Double min = 10000.0;
        try {
            for (int i = 0; i < pingList.size(); i++) {
                if (pingList.get(i).getTime() < min) {
                    min = pingList.get(i).getTime();
                }
            }
        } catch (NullPointerException e) {
            e.fillInStackTrace();
        }


        return min;
    }

    public int getSr() {
        int result = 0;
        int value = 0;
        int count = pingList.size();
        try {

            for (int i = 0; i < pingList.size(); i++) {
                value += pingList.get(i).getTime();
            }

            result = value / count;
        } catch (NullPointerException | ArithmeticException e ) {
            e.fillInStackTrace();
        }


        return result;
    }


    public ArrayList<PingObiect> getPingList() {
        return pingList;
    }

    public void setPingListByHour(int hour) {
        PingListHour.clear();
        for (int i = 0; i < pingList.size(); i++) {
            if(Integer.parseInt(pingList.get(i).getDate())==hour){
                System.out.println(hour +"="+ pingList.get(i).getDate());
                PingListHour.add(pingList.get(i));
                System.out.println("DODANO: " + pingList.get(i).getDate());
                System.out.println("DODANO2: " + pingList.get(i).getTime());
            }

        }
    }

    public void showPingListByHour() {
        System.out.println("PING LIST HOUR");
        for (int i = 0; i < PingListHour.size(); i++) {
            System.out.println(PingListHour.get(i).getTime());
        }
    }

    public double getValueOfListByHour(){
        double result = 0;
        for (int i = 0; i < PingListHour.size(); i++) {
            result+=PingListHour.get(i).getTime();
        }
        return result/PingListHour.size();
    }

    public void savePing() {
        Date dateBasic2;
        dateBasic2 = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date2 = format2.format(dateBasic2);


        try {
            FileOutputStream fileOutputStream;
            fileOutputStream = ctx.openFileOutput(date2, Context.MODE_PRIVATE);
            for (int i = 0; i < pingList.size(); i++) {
                fileOutputStream.write(pingList.get(i).toString().getBytes());
                fileOutputStream.write("#".getBytes());
            }

            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.fillInStackTrace();
        }
    }

    public void loadPing() {
        System.out.println("FILE LIST ----------------------------");
        for (int i = 0; i < ctx.fileList().length; i++) {
            System.out.println(ctx.fileList()[i].toString());
        }
        System.out.println("----------------------------");
        PingReader();
    }

    private void PingReader() {
        if (getFileID() < ctx.fileList().length) {
            try {
                FileInputStream fileInputStream;
                fileInputStream = ctx.openFileInput(ctx.fileList()[getFileID()]);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuffer = new StringBuilder();
                String data;
                while ((data = bufferedReader.readLine()) != null) {
                    stringBuffer.append(data).append("\n");
                    String splited = stringBuffer.toString();
                    String[] splited2 = splited.split("#");
                    for (int i = 0; i < splited2.length - 1; i++) {
                        String[] splited3 = splited2[i].split("~~");
                        PingObiect pingObiect = new PingObiect(splited3[0], splited3[1], splited3[2]);
                        pingList.add(pingObiect);
                    }


                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ArrayIndexOutOfBoundsException e) {
                e.fillInStackTrace();
            }
        }
    }

    private int getFileID() {
        Date dateBasic2;
        dateBasic2 = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date2 = format2.format(dateBasic2);
        int FileNumber = ctx.fileList().length + 1;
        for (int i = 0; i < ctx.fileList().length; i++) {
            if (ctx.fileList()[i].contains(date2)) {
                FileNumber = i;
                return FileNumber;
            }

        }
        return FileNumber;
    }
}
