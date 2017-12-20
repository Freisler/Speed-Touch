package com.example.rzr.fre0025_tamz2_projekt;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Game extends Activity{

    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }

    static int cols = 5;
    static int rows = 5;
    public static TextView textTimer;
    public static TextView winnerIn;
    public static TextView winnerTime;


    ImageView coverImg;
    static Button btnStart;
    static Button btnBackToMenu;
    static Button btnSaveGame;

    static boolean win = false;



    static Handler handler = new Handler();

    static long startTime = 0L, timeInMS = 0L, timeSwapBuff = 0L, updateTime = 0L;

    static int mins, secs, milisecs;

    static Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMS = SystemClock.uptimeMillis() - startTime;
            updateTime = timeSwapBuff + timeInMS;
            secs = (int) (updateTime/1000);
            mins = secs / 60;
            secs %= 60;
            milisecs = (int) (updateTime%1000);
            textTimer.setText(""+mins+":"+String.format("%2d",secs)+":"
                                         +String.format("%3d",milisecs));
            handler.postDelayed(this,0);
        }
    };

    String nick;


    static MediaPlayer yesSound;
    static MediaPlayer noSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        askPermissions();

        yesSound = MediaPlayer.create(this,R.raw.yes);
        noSound = MediaPlayer.create(this,R.raw.no);
        startTime = 0L;
        timeInMS = 0L;
        timeSwapBuff = 0L;
        updateTime = 0L;
        Intent i = getIntent();
        nick  = i.getStringExtra("nick");

        textTimer = (TextView)findViewById(R.id.timerValue);
        btnStart = (Button)findViewById(R.id.btnStart);
        coverImg = (ImageView)findViewById(R.id.coverImg);
        winnerIn = (TextView)findViewById(R.id.winnerIn);
        winnerTime = (TextView)findViewById(R.id.winnerTime);
        btnBackToMenu = (Button)findViewById(R.id.btnBackToMenu);
        btnSaveGame = (Button) findViewById(R.id.btnSaveGame);
        winnerIn.setVisibility(View.INVISIBLE);
        winnerTime.setVisibility(View.INVISIBLE);
        btnSaveGame.setVisibility(View.INVISIBLE);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime = SystemClock.uptimeMillis();
                handler.postDelayed(updateTimerThread, 0);

                coverImg.setVisibility(View.INVISIBLE);
                btnStart.setEnabled(false);
                btnStart.setVisibility(View.INVISIBLE);

            }
        });

        btnBackToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeSwapBuff += timeInMS;
                handler.removeCallbacks(updateTimerThread);
                win = false;
                Intent i = new Intent(getApplicationContext(),MainMenu.class);
                i.putExtra("nick",nick);
                //i.putExtra("mins",mins);
                //i.putExtra("secs",secs);
                //i.putExtra("milisecs",milisecs);
                finish();
                startActivity(i);
            }
        });

        btnSaveGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getBaseContext(),getExternalFilesDir(null),Toast.LENGTH_SHORT).show();

                String FILENAME = "speedtouch_scoreboard";
                String stringToSave = nick+" - "+mins+":"+secs+"."+milisecs;

                try {
                    //open file for writing
                    OutputStreamWriter out = new OutputStreamWriter(openFileOutput("save.txt", MODE_APPEND));


                    //write information to file
                    out.write(stringToSave);
                    out.write('\n');

                    //close file
                    out.close();
                    //Toast.makeText(getApplicationContext(),"Text Saved",Toast.LENGTH_LONG).show();





                } catch (java.io.IOException e) {
                    //if caught
                    //Toast.makeText(getApplicationContext(), "Text Could not be added",Toast.LENGTH_LONG).show();
                }

                Intent i = new Intent(getApplicationContext(),Score.class);
                i.putExtra("nick",nick);
                i.putExtra("mins",mins);
                i.putExtra("secs",secs);
                i.putExtra("milisecs",milisecs);
                //Toast.makeText(getApplicationContext(),nick+" - "+mins+":"+secs+"."+milisecs,Toast.LENGTH_SHORT).show();
                startActivity(i);

            }
        });


    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:{

            }
            case MotionEvent.ACTION_UP:{
                if(win == true){

                }


            }

        }
        return super.onTouchEvent(event);
    }
}
