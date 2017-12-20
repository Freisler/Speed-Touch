package com.example.rzr.fre0025_tamz2_projekt;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainMenu extends Activity{

    String nick;
    TextView nickTV;

    Button btn1;
    Button btn2;
    Button btn3;
    Button logout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        Intent i = getIntent();
        nick = i.getStringExtra("nick");
        nickTV = (TextView)findViewById(R.id.nick);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "Cedarville-Cursive.ttf");
        nickTV.setTypeface(typeface);
        nickTV.setText(nick);

        btn1 = (Button)findViewById(R.id.menuBtn1);
        btn2 = (Button)findViewById(R.id.menuBtn2);
        btn3 = (Button)findViewById(R.id.menuBtn3);
        logout = (Button)findViewById(R.id.menuBtnLogout);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Game.class);
                i.putExtra("nick",nick);
                startActivity(i);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Score.class);
                i.putExtra("nick",nick);
                finish();
                startActivity(i);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Login.class);
                finish();
                startActivity(i);
            }
        });


    }
}
