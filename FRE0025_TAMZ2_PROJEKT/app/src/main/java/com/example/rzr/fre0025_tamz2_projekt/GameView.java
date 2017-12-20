package com.example.rzr.fre0025_tamz2_projekt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by RZr on 16.12.2017.
 */

public class GameView extends View {

    Bitmap[] bmp;

    int rows = 5;
    int cols = 5;
    private int progress = 0;
    private int next = 1;
    private int progressArr[] = new int[25];
    int width;
    int height;

    float x1,y1;

    public static int[] lvl = new int[25];
    public String[] level = new String[25];

    public int lastBoxNumber = 0;
    boolean wasSuccess = false;




    private void generateNumbers(){
        Random r = new Random();
        List<Integer> uniq = new ArrayList<>();
        while(uniq.size() != 25){
            for(int i = 0; i < 25; i++)
            {
                int n = r.nextInt(25-1+1)+1;
                if(!uniq.contains(n))
                    uniq.add(n);
            }
        }
        System.out.println("Size: "+uniq.size());

        for (int i = 0; i < lvl.length; i++) {
            lvl[i] = uniq.get(i);

        }
        //for(int num : lvl)
        //    System.out.println(num);

        level = Arrays.toString(lvl).split("[\\[\\]]")[1].split(", ");

    }


    public int[] levelInt = {
            0,0,0,0,0,
            0,0,0,0,0,
            0,0,0,0,0,
            0,0,0,0,0,
            0,0,0,0,0
    };

    public GameView(Context context) {
        super(context);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context) {
        bmp = new Bitmap[2];
        bmp[0] = BitmapFactory.decodeResource(getResources(), R.drawable.square1);
        bmp[1] = BitmapFactory.decodeResource(getResources(), R.drawable.empty);
        generateNumbers();


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                x1 = event.getX();
                y1 = event.getY();
                //Toast.makeText(getContext(), "X: "+x1+" ,Y: "+y1, Toast.LENGTH_SHORT).show();

                getBox(x1,y1);
            }
            case MotionEvent.ACTION_UP:{
                if(wasSuccess){
                    levelInt[lastBoxNumber] = 1;
                }
                checkIfWin();

            }

        }
        invalidate();
        return super.onTouchEvent(event);
    }


    private void checkIfWin() {
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++                              "+Game.win);
        if(Game.win == true){
            Game.textTimer.setVisibility(INVISIBLE);
            Game.winnerIn.setVisibility(VISIBLE);
            Game.winnerTime.setVisibility(VISIBLE);
            Game.winnerTime.setText(""+Game.mins+":"+String.format("%2d",Game.secs)+":"
                    +String.format("%3d",Game.milisecs));
            Game.btnSaveGame.setVisibility(VISIBLE);


        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w / rows;
        height = h / cols;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        canvas.save();
        paint.setColor(Color.BLACK);


        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                canvas.drawBitmap(bmp[levelInt[i*rows + j]], null,
                        new Rect(j*width, i*height,(j+1)*width, (i+1)*height), null);
            }
        }

        paint.setTextSize(100);
        paint.setColor(Color.BLACK);
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {

                canvas.drawText(level[i*rows + j],(float)j * width + (width/Game.cols), (float)i * height + height/2, paint);
            }
        }
        invalidate();


    }

    private void checkIfYes(int number, int boxNumber){
        //Toast.makeText(getContext(),"Delka arraye:"+progressArr.length,Toast.LENGTH_SHORT).show();
        if(number == next){
            next++;
            progress = number;
            levelInt[boxNumber] = 1;
            //progressArr[number-1] = number;
            level[boxNumber] = "";
            wasSuccess = true;
            lastBoxNumber = boxNumber;
            Game.yesSound.start();

        }else{
            wasSuccess = false;
            lastBoxNumber = boxNumber;
            Game.noSound.start();
        }
        if(progress == 25) {
            progress = 0;
            next = 1;
            Game.timeSwapBuff += Game.timeInMS;
            Game.handler.removeCallbacks(Game.updateTimerThread);
            Game.win = true;
        }
    }

    private void getBox(float touchedX, float touchedY){
        //int a = 0;
        //int b = 216;
        //int c = 432;
        //int d = 648;
        //int e = 864;
        if(touchedX < 216 && touchedY < 216) {
            //Toast.makeText(getContext(), "Dotkl ses pole 0, obsahující číslo: " + lvl[0], Toast.LENGTH_SHORT).show();
            checkIfYes(lvl[0],0);
        }
        if(touchedX < 432 && touchedX > 216 && touchedY < 216){
            //Toast.makeText(getContext(),"Dotkl ses pole 1, obsahující číslo: "+lvl[1],Toast.LENGTH_SHORT).show();
            checkIfYes(lvl[1],1);
        }
        if(touchedX < 648 && touchedX > 432 && touchedY < 216){
            //Toast.makeText(getContext(),"Dotkl ses pole 2, obsahující číslo: "+lvl[2],Toast.LENGTH_SHORT).show();
            checkIfYes(lvl[2],2);
        }
        if(touchedX < 864 && touchedX > 648 && touchedY < 216){
            //Toast.makeText(getContext(),"Dotkl ses pole 3, obsahující číslo: "+lvl[3],Toast.LENGTH_SHORT).show();
            checkIfYes(lvl[3],3);
        }
        if(touchedX > 864 && touchedY < 216){
            //Toast.makeText(getContext(),"Dotkl ses pole 4, obsahující číslo: "+lvl[4],Toast.LENGTH_SHORT).show();
            checkIfYes(lvl[4],4);
        }

        if(touchedX < 216 && touchedY < 432 && touchedY > 216) {
            //Toast.makeText(getContext(), "Dotkl ses pole 5, obsahující číslo: " + lvl[5], Toast.LENGTH_SHORT).show();
            checkIfYes(lvl[5],5);
        }
        if(touchedX < 432 && touchedX > 216 && touchedY < 432 && touchedY > 216) {
            //Toast.makeText(getContext(), "Dotkl ses pole 6, obsahující číslo: " + lvl[6], Toast.LENGTH_SHORT).show();
            checkIfYes(lvl[6],6);
        }
        if(touchedX < 648 && touchedX > 432 && touchedY < 432 && touchedY > 216) {
            //Toast.makeText(getContext(), "Dotkl ses pole 7, obsahující číslo: " + lvl[7], Toast.LENGTH_SHORT).show();
            checkIfYes(lvl[7],7);
        }
        if(touchedX < 864 && touchedX > 648 && touchedY < 432 && touchedY > 216) {
            //Toast.makeText(getContext(), "Dotkl ses pole 8, obsahující číslo: " + lvl[8], Toast.LENGTH_SHORT).show();
            checkIfYes(lvl[8],8);
        }
        if(touchedX > 864  && touchedY < 432 && touchedY > 216) {
            //Toast.makeText(getContext(), "Dotkl ses pole 9, obsahující číslo: " + lvl[9], Toast.LENGTH_SHORT).show();
            checkIfYes(lvl[9],9);
        }

        if(touchedX < 216 && touchedY < 648 && touchedY > 432) {
            //Toast.makeText(getContext(), "Dotkl ses pole 10, obsahující číslo: " + lvl[10], Toast.LENGTH_SHORT).show();
            checkIfYes(lvl[10],10);
        }
        if(touchedX < 432 && touchedX > 216 && touchedY < 648 && touchedY > 432) {
            //Toast.makeText(getContext(), "Dotkl ses pole 11, obsahující číslo: " + lvl[11], Toast.LENGTH_SHORT).show();
            checkIfYes(lvl[11],11);
        }
        if(touchedX < 648 && touchedX > 432 && touchedY < 648 && touchedY > 432) {
            //Toast.makeText(getContext(), "Dotkl ses pole 12, obsahující číslo: " + lvl[12], Toast.LENGTH_SHORT).show();
            checkIfYes(lvl[12],12);
        }
        if(touchedX < 864 && touchedX > 648 && touchedY < 648 && touchedY > 432) {
            //Toast.makeText(getContext(), "Dotkl ses pole 13, obsahující číslo: " + lvl[13], Toast.LENGTH_SHORT).show();
            checkIfYes(lvl[13],13);
        }
        if(touchedX > 864  && touchedY < 648 && touchedY > 432) {
            //Toast.makeText(getContext(), "Dotkl ses pole 14, obsahující číslo: " + lvl[14], Toast.LENGTH_SHORT).show();
            checkIfYes(lvl[14],14);
        }

        if(touchedX < 216 && touchedY < 864 && touchedY > 648) {
            //Toast.makeText(getContext(), "Dotkl ses pole 15, obsahující číslo: " + lvl[15], Toast.LENGTH_SHORT).show();
            checkIfYes(lvl[15],15);
        }
        if(touchedX < 432 && touchedX > 216 && touchedY < 864 && touchedY > 648) {
            //Toast.makeText(getContext(), "Dotkl ses pole 16, obsahující číslo: " + lvl[16], Toast.LENGTH_SHORT).show();
            checkIfYes(lvl[16],16);
        }
        if(touchedX < 648 && touchedX > 432 && touchedY < 864 && touchedY > 648) {
            //Toast.makeText(getContext(), "Dotkl ses pole 17, obsahující číslo: " + lvl[17], Toast.LENGTH_SHORT).show();
            checkIfYes(lvl[17],17);
        }
        if(touchedX < 864 && touchedX > 648 && touchedY < 864 && touchedY > 648) {
            //Toast.makeText(getContext(), "Dotkl ses pole 18, obsahující číslo: " + lvl[18], Toast.LENGTH_SHORT).show();
            checkIfYes(lvl[18],18);
        }
        if(touchedX > 864  && touchedY < 864 && touchedY > 648) {
            //Toast.makeText(getContext(), "Dotkl ses pole 19, obsahující číslo: " + lvl[19], Toast.LENGTH_SHORT).show();
            checkIfYes(lvl[19],19);
        }

        if(touchedX < 216 && touchedY > 864) {
            //Toast.makeText(getContext(), "Dotkl ses pole 20, obsahující číslo: " + lvl[20], Toast.LENGTH_SHORT).show();
            checkIfYes(lvl[20],20);
        }
        if(touchedX < 432 && touchedX > 216 && touchedY > 864) {
            //Toast.makeText(getContext(), "Dotkl ses pole 21, obsahující číslo: " + lvl[21], Toast.LENGTH_SHORT).show();
            checkIfYes(lvl[21],21);
        }
        if(touchedX < 648 && touchedX > 432 && touchedY > 864) {
            //Toast.makeText(getContext(), "Dotkl ses pole 22, obsahující číslo: " + lvl[22], Toast.LENGTH_SHORT).show();
            checkIfYes(lvl[22],22);
        }
        if(touchedX < 864 && touchedX > 648 && touchedY > 864) {
            //Toast.makeText(getContext(), "Dotkl ses pole 23, obsahující číslo: " + lvl[23], Toast.LENGTH_SHORT).show();
            checkIfYes(lvl[23],23);
        }
        if(touchedX > 864 && touchedY > 864) {
            //Toast.makeText(getContext(), "Dotkl ses pole 24, obsahující číslo: " + lvl[24], Toast.LENGTH_SHORT).show();
            checkIfYes(lvl[24],24);
        }
    }


}
