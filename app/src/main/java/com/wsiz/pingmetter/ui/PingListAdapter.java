package com.wsiz.pingmetter.ui;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.wsiz.pingmetter.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PingListAdapter extends ArrayAdapter<String> {

    public PingListAdapter(ArrayList<String> data, Context context) {
        super(context, R.layout.fragment_notifications, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String dataModel = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.pinglist, parent, false);
            viewHolder.data = convertView.findViewById(R.id.textView8);
            viewHolder.max = convertView.findViewById(R.id.textView14);
            viewHolder.sr = convertView.findViewById(R.id.textView15);
            viewHolder.min = convertView.findViewById(R.id.textView16);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        System.out.println("DATAMODEL");
        System.out.println(dataModel);
        try {
            assert dataModel != null;
            String [] split = dataModel.split("~~");

            viewHolder.data.setText(split[0]);
            viewHolder.max.setText(split[1]);
            java.text.DecimalFormat df=new java.text.DecimalFormat("0.0");

            viewHolder.sr.setText(df.format(Double.parseDouble(split[2])));
            viewHolder.min.setText(split[3]);


        } catch (ArrayIndexOutOfBoundsException e) {
            e.fillInStackTrace();
        }

        return convertView;
    }

    private class ViewHolder {
        TextView data;
        TextView min;
        TextView sr;
        TextView max;

    }


}