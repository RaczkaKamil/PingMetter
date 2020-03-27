package com.wsiz.pingmetter.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.wsiz.pingmetter.R;
import com.wsiz.pingmetter.ui.PingListAdapter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class NotificationsFragment extends Fragment {
    private ArrayList<String> MessageslistOfString = new ArrayList<>();
    private ArrayList<String> MessageslistOfPing = new ArrayList<>();
    private PingListAdapter customAdapterr;
    String result;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        customAdapterr = new PingListAdapter(MessageslistOfString, getContext());
        final ListView online_list = root.findViewById(R.id.ping_list);
        online_list.setAdapter(customAdapterr);
        online_list.setClickable(false);
        customAdapterr.notifyDataSetChanged();



        for (int i = 0; i < getContext().fileList().length; i++) {
            if(getContext().fileList()[i].contains("2020")){
                fileReader(i);
            }

        }

        getList();
        return root;
    }

    private void getList() {
        System.out.println("LISTA: ");
        for (int i = 0; i < MessageslistOfString.size(); i++) {
            System.out.println(MessageslistOfString.get(i));
        }
    }

    private void fileReader(int number) {
        String data;

            try {
                FileInputStream fileInputStream;
                fileInputStream = getContext().openFileInput(getContext().fileList()[number]);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuffer = new StringBuilder();


                while ((data = bufferedReader.readLine()) != null) {
                    stringBuffer.append(data).append("\n");
                    this.result = stringBuffer.toString();
                    setResult(result,number);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    private void setResult(String result, int number) {
        MessageslistOfPing.clear();
        String[] split = result.split("#");
        for (int i = 0; i < split.length; i++) {
            String[] split2 = split[i].split("~~");
            System.out.println(split[i]);
                MessageslistOfPing.add(split2[0]);
                System.out.println("ADDED: " + split2[0]);




        }

        setMinMax(number);

    }

    private void setMinMax(int number) {
        double min=10000;
        double sr=0;
        double Rsr=0;
        double max=0;

    for (int i = 0; i < MessageslistOfPing.size(); i++) {
        try{
            if(min>Double.parseDouble(MessageslistOfPing.get(i))){
                min=Double.parseDouble(MessageslistOfPing.get(i));
            }
        }catch (NumberFormatException e){
            e.fillInStackTrace();
        }

        }

        for (int i = 0; i < MessageslistOfPing.size(); i++) {
            try{
                if(max<Double.parseDouble(MessageslistOfPing.get(i))){
                    max=Double.parseDouble(MessageslistOfPing.get(i));
                }
            }catch (NumberFormatException e){
                e.fillInStackTrace();
            }

        }

        for (int i = 0; i < MessageslistOfPing.size(); i++) {
            try{
                sr=sr+Double.parseDouble(MessageslistOfPing.get(i));
            }catch (NumberFormatException e){
                e.fillInStackTrace();
            }
        }

        Rsr = sr/MessageslistOfPing.size();


        MessageslistOfString.add(getContext().fileList()[number]+"~~"+max+"~~"+Rsr+"~~"+min);
    System.out.println("DLUGOSC LSITY: "+ MessageslistOfString.size());
        customAdapterr.notifyDataSetChanged();


    }
}