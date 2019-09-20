package com.jins_jp.memelib_realtime;

import android.Manifest;
import android.app.AlertDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jins_jp.meme.MemeConnectListener;
import com.jins_jp.meme.MemeLib;
import com.jins_jp.meme.MemeRealtimeData;
import com.jins_jp.meme.MemeRealtimeListener;
import com.jins_jp.meme.MemeScanListener;
import com.jins_jp.meme.MemeStatus;
import com.jins_jp.memelib_realtime.service.DataMonitorService;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String JOB_ID = "0001";
    private MemeLib memeLib;
    private Button eye, body;
    private DatabaseReference mdatabase;
    private String account;
    private ImageView growup;
    private ArrayList<Integer> pic;
    private Integer index=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitView();
        pic = new ArrayList<>();
        pic.add(R.drawable.seed_1);
        pic.add(R.drawable.tree1);
        pic.add(R.drawable.tree1_fail_1);
        pic.add(R.drawable.tree1_fail_2);
        pic.add(R.drawable.tree1_fail_3);
        pic.add(R.drawable.seed_2);
        pic.add(R.drawable.tree2);
        pic.add(R.drawable.tree2_fail_1);
        pic.add(R.drawable.tree2_fail_2);
        pic.add(R.drawable.tree2_fail_3);
        pic.add(R.drawable.tree3);
        pic.add(R.drawable.tree4);


        Intent intent = this.getIntent();
        account = intent.getStringExtra("account");

        mdatabase = FirebaseDatabase.getInstance().getReference();
        mdatabase.child("Users").child(account).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("ap546", dataSnapshot.child("pos_score").toString());
                Log.d("ap546", dataSnapshot.child("nag_score").toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EyeActivity.class));
            }
        });

        body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DataActivity.class));
            }
        });


//        init();
//        Intent intent = new Intent(MainActivity.this, DataMonitorService.class);
//        startService(intent);
//        JobScheduler scheduler = (JobScheduler) getApplicationContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
//
//        ComponentName componentName = new ComponentName(getApplicationContext(), DataMonitorService.class);
//        JobInfo job = new JobInfo.Builder(1000, componentName)
//                .setPersisted(true) // 重開機後是否執行
//                .setPeriodic(60000*30)
//                .build();
//        scheduler.schedule(job);
    }

    @Override
    protected void onResume() {
        super.onResume();
        change();
    }

    private void InitView(){
        eye = findViewById(R.id.eye);
        body = findViewById(R.id.body);
        growup = findViewById(R.id.growup);
    }

    void change() {
        new CountDownTimer(13000,1000){

            @Override
            public void onFinish() {
            }

            @Override
            public void onTick(long millisUntilFinished) {
                growup.setImageDrawable(getResources().getDrawable(pic.get(index)));
                index++;
            }

        }.start();
    }
    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.calendar_view, null);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        MaterialCalendarView calendarview = dialogView.findViewById(R.id.calendarView);
    }

}
