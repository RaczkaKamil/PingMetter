package com.wsiz.pingmetter.ui.dashboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.utils.ColorTemplate;
import com.wsiz.pingmetter.Ping;
import com.wsiz.pingmetter.R;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private TextView tf_records;
    private TextView tf_hour;
    private Ping ping;
    private BarChart chart;
    private TextView tf_pingMax;
    private TextView tf_pingMin;
    private TextView tf_pingSr;
    private TextView tf_host;
    private TextView tf_connection;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        tf_records = root.findViewById(R.id.tf_records);
        tf_hour = root.findViewById(R.id.tf_hour);
        tf_pingMax = root.findViewById(R.id.tf_pingMax);
        tf_pingMin = root.findViewById(R.id.tf_pingMin);
        tf_pingSr = root.findViewById(R.id.tf_pingSr);
        tf_host = root.findViewById(R.id.tf_host);
        tf_connection = root.findViewById(R.id.tf_connection);


        RotateAnimation rotate= (RotateAnimation) AnimationUtils.loadAnimation(getContext(),R.anim.rotateanimation);
        tf_hour.setAnimation(rotate);

        chart = root.findViewById(R.id.barchart);
        reloadPing();

        return root;
    }

    @SuppressLint("SetTextI18n")
    private void reloadPing() {
        ping = new Ping(getContext());
        ping.pingObiectList.loadPing();
try{
    tf_host.post(() -> tf_host.setText("wsi.edu.pl"));
    tf_pingMax.post(() -> tf_pingMax.setText(ping.pingObiectList.getMax() +" ms"));
    tf_pingMin.post(() -> tf_pingMin.setText(ping.pingObiectList.getMin() +" ms"));
    tf_pingSr.post(() -> tf_pingSr.setText(ping.pingObiectList.getSr() +" ms"));
    tf_connection.post(() -> tf_connection.setText("WIFI/TRANSFER"));

    tf_records.setText( String.valueOf(ping.pingObiectList.getPingList().size()));
    showGraph();
}catch (ArithmeticException e){
    e.fillInStackTrace();
}

    }

    private void showGraph() {

        ArrayList<BarEntry> NoOfEmp = new ArrayList<>();


        NoOfEmp.add(new BarEntry(0, getPing(0)));
        NoOfEmp.add(new BarEntry(1, getPing(1)));
        NoOfEmp.add(new BarEntry(2, getPing(2)));
        NoOfEmp.add(new BarEntry(3, getPing(3)));
        NoOfEmp.add(new BarEntry(4, getPing(4)));
        NoOfEmp.add(new BarEntry(5, getPing(5)));
        NoOfEmp.add(new BarEntry(6, getPing(6)));
        NoOfEmp.add(new BarEntry(7, getPing(7)));
        NoOfEmp.add(new BarEntry(8, getPing(8)));
        NoOfEmp.add(new BarEntry(9, getPing(9)));
        NoOfEmp.add(new BarEntry(10, getPing(10)));
        NoOfEmp.add(new BarEntry(11, getPing(11)));
        NoOfEmp.add(new BarEntry(12, getPing(12)));
        NoOfEmp.add(new BarEntry(13, getPing(13)));
        NoOfEmp.add(new BarEntry(14, getPing(14)));
        NoOfEmp.add(new BarEntry(15, getPing(15)));
        NoOfEmp.add(new BarEntry(16, getPing(16)));
        NoOfEmp.add(new BarEntry(17, getPing(17)));
        NoOfEmp.add(new BarEntry(18, getPing(18)));
        NoOfEmp.add(new BarEntry(19, getPing(19)));
        NoOfEmp.add(new BarEntry(20, getPing(20)));
        NoOfEmp.add(new BarEntry(21, getPing(21)));
        NoOfEmp.add(new BarEntry(22, getPing(22)));
        NoOfEmp.add(new BarEntry(23, getPing(23)));


        BarDataSet barDataSet = new BarDataSet(NoOfEmp, "");
        barDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        BarData data = new BarData(barDataSet);
        chart.setNoDataText("Brak pomiarów");
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.getXAxis().setGranularity(1);
        chart.getXAxis().setAvoidFirstLastClipping(true);
        chart.setVisibility(View.GONE);
        chart.setVisibility(View.VISIBLE);
        chart.setDrawValueAboveBar(false);
        chart.setFitBars(true);
        chart.setData(data);
        chart.invalidate();
    }

    private float getPing(int hour){
        ping.pingObiectList.setPingListByHour(hour);
        ping.pingObiectList.showPingListByHour();
        System.out.println("WYNIK: " + ping.pingObiectList.getValueOfListByHour());
        return (float) ping.pingObiectList.getValueOfListByHour();
    }

}