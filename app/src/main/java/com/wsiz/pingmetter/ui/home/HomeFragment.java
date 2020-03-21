package com.wsiz.pingmetter.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.wsiz.pingmetter.Ping;
import com.wsiz.pingmetter.R;

public class HomeFragment extends Fragment {

    private Button btn_start;
    private ProgressBar progressBar;
    private EditText et_host;
    private TextView tf_pingMax;
    private TextView tf_pingMin;
    private TextView tf_pingSr;
    private TextView tf_host;
    private TextView tf_connection;
    ConstraintLayout pingInfo;

    Ping ping;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        btn_start = root.findViewById(R.id.bt_start);
        et_host = root.findViewById(R.id.et_host);
        pingInfo = root.findViewById(R.id.pingInfo);
        pingInfo.setVisibility(View.GONE);
        progressBar = root.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        tf_pingMax = root.findViewById(R.id.tf_pingMax);
        tf_pingMin = root.findViewById(R.id.tf_pingMin);
        tf_pingSr = root.findViewById(R.id.tf_pingSr);
        tf_host = root.findViewById(R.id.tf_host);
        tf_connection = root.findViewById(R.id.tf_connection);
        btn_start.setOnClickListener(v->{
            progressBar.setVisibility(View.VISIBLE);
            pingInfo.setVisibility(View.GONE);
            startConnection(et_host.getText().toString());
        });
        return root;
    }

    private void startConnection(String host) {
        Thread thread = new Thread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                ping=new Ping(host,getContext());

                while (!ping.isDone){
                    try {
                            Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(ping.pingObiectList.getPingList().size()>0){
                    tf_connection.post(() -> tf_connection.setText(ping.getNetWorkType()));
                    tf_host.post(() -> tf_host.setText(ping.getHost()));
                    tf_pingMax.post(() -> tf_pingMax.setText(ping.pingObiectList.getMax() +" ms"));
                    tf_pingMin.post(() -> tf_pingMin.setText(ping.pingObiectList.getMin() +" ms"));
                    tf_pingSr.post(() -> tf_pingSr.setText(ping.pingObiectList.getSr() +" ms"));
                    pingInfo.post(()->pingInfo.setVisibility(View.VISIBLE));
                    ping.pingObiectList.loadPing();
                    ping.pingObiectList.savePing();

                    System.out.println("Długość listy: "+ping.pingObiectList.getPingList().size());

                }else
                {
                    tf_connection.post(() ->   Toast.makeText(getContext(),"Nie znaleziono serwera! " , Toast.LENGTH_SHORT).show());
                }
                progressBar.post(()->progressBar.setVisibility(View.GONE));
            }
        });
        thread.start();

    }


}
