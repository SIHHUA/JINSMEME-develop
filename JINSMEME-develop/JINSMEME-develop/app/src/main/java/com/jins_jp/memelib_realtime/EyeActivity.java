package com.jins_jp.memelib_realtime;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import com.jins_jp.meme.MemeLib;
import com.jins_jp.meme.MemeRealtimeData;
import com.jins_jp.meme.MemeRealtimeListener;

import java.util.ArrayList;

public class EyeActivity extends AppCompatActivity {
    private static String TAG = "EyeActivity";
    private MemeLib memeLib;
    // 上中、下中、左中、右中、左上中、右下中、右上中、左下中、順時、逆時
    private boolean[] step = {false,false,false,false,false,false,false,false,false,false};
    //private int[][] data = new int[40][4]; //一秒20個
    //private ArrayList[][] data = new ArrayList[40][4];
    private ArrayList<ArrayList<Integer>> data;
    private ArrayList<ArrayList<Integer>> temp;
    private int eyemoveUP,eyemoveDOWN,eyemoveLEFT,eyemoveRIGHT;
    private int front = 0,back,capacity = 40; // queue
    private ImageView eyePIC;
    private ArrayList<Integer> pic;
    private Integer index=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eye);

        memeLib = MemeLib.getInstance();
        eyePIC = findViewById(R.id.eyepic);
        Button start = findViewById(R.id.start);
        Log.d(TAG, memeLib.isConnected()+"");


        pic = new ArrayList<>();
        pic.add(R.drawable.up);
        pic.add(R.drawable.mid);
        pic.add(R.drawable.down);
        pic.add(R.drawable.mid);
        pic.add(R.drawable.left);
        pic.add(R.drawable.mid);
        pic.add(R.drawable.right);
        pic.add(R.drawable.mid);
        pic.add(R.drawable.left_up);
        pic.add(R.drawable.mid);
        pic.add(R.drawable.right_down);
        pic.add(R.drawable.mid);
        pic.add(R.drawable.right_up);
        pic.add(R.drawable.mid);
        pic.add(R.drawable.left_down);
        pic.add(R.drawable.mid);
        pic.add(R.drawable.clockwise);
        pic.add(R.drawable.reverse);

//        change();


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change();
                //Log.d("data1","test");
               // eyePIC.setImageDrawable(getResources().getDrawable( R.drawable.down ));

//                for(int i = 0;i<10;i++)
//                            Thread.sleep(200);
//                {
//                    int check = 0;
//                    while(step[i] == false)
//                    {
//                        eyePIC.setImageDrawable(getResources().getDrawable( R.drawable.down ));
//                        try {
//                            Thread.sleep(200);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        eyePIC.setImageDrawable(getResources().getDrawable( R.drawable.down ));
//                        try {
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        step[i] = true;
//                    }
//                }
//
//                int i = 0;
//                if(step[0] == false)
//                {
//                    if(eyemoveUP != 0 && eyemoveDOWN == 0 && eyemoveLEFT == 0 && eyemoveRIGHT == 0)
//                    {
//                        eyePIC.setImageDrawable(getResources().getDrawable( R.drawable.down ));
//
//                    }
//                }
            }
        });
    }

    void init() {
        memeLib = MemeLib.getInstance();
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        //Starts receiving realtime data
        memeLib.startDataReport(new MemeRealtimeListener() {
            @Override
            public void memeRealtimeCallback(MemeRealtimeData memeRealtimeData) {


                Log.d("ap123", memeRealtimeData.getBlinkStrength() + ", " +
                        memeRealtimeData.getBlinkSpeed() + ", " +
                        memeRealtimeData.getEyeMoveUp() + ", " +
                        memeRealtimeData.getEyeMoveDown() + ", " +
                        memeRealtimeData.getEyeMoveLeft() + ", " +
                        memeRealtimeData.getEyeMoveRight() );

                if(front < capacity)
                {
                    data.get(front).add(memeRealtimeData.getEyeMoveUp());
                    data.get(front).add(memeRealtimeData.getEyeMoveDown());
                    data.get(front).add(memeRealtimeData.getEyeMoveLeft());
                    data.get(front).add(memeRealtimeData.getEyeMoveRight());
                    front = front + 1;
                }
                else
                {
                    temp = data;
                    data.remove(0);
                    data.get(front).add(memeRealtimeData.getEyeMoveUp());
                    data.get(front).add(memeRealtimeData.getEyeMoveDown());
                    data.get(front).add(memeRealtimeData.getEyeMoveLeft());
                    data.get(front).add(memeRealtimeData.getEyeMoveRight());

                }
            }

        });
    }

    void change() {
        new CountDownTimer(19000,1000){

            @Override
            public void onFinish() {
            }

            @Override
            public void onTick(long millisUntilFinished) {
                eyePIC.setImageDrawable(getResources().getDrawable(pic.get(index)));
                index++;
            }

        }.start();
    }
}
