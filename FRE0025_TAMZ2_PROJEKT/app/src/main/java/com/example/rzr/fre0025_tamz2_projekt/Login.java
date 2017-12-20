package com.example.rzr.fre0025_tamz2_projekt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;

public class Login extends AppCompatActivity {

    TextView nickET;
    Button btn1;
    String newNick;

    EditText existingNickET;
    Button btn2;
    String existingNick;

    SharedPreferences mySharedPref;
    SharedPreferences.Editor mySharedEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nickET = (EditText)findViewById(R.id.menuNewNick);

        btn1 = (Button)findViewById(R.id.menuButton1);

        existingNickET = (EditText)findViewById(R.id.menuExistingNick);
        existingNickET.setFocusable(false);
        existingNickET.setEnabled(false);
        existingNickET.setClickable(false);

        btn2 = (Button)findViewById(R.id.menuButton2);
        mySharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        existingNick = mySharedPref.getString("nick",null);
        existingNickET.setText(existingNick);


        Map<String, ?> prefsMap = mySharedPref.getAll();
        for (Map.Entry<String, ?> entry: prefsMap.entrySet())
        {
            Log.e("SharedPreferences: ", entry.getKey() + ": " + entry.getValue().toString());
        }

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newNick = nickET.getText().toString();
                if(newNick.length() < 3)
                    nickET.setError("Přezdívka musí mít alespoň 3 znaky!");
                else{
                    mySharedEditor = mySharedPref.edit();
                    mySharedEditor.putString("nick", newNick);
                    mySharedEditor.apply();
                    Intent i = new Intent(getApplicationContext(), MainMenu.class);
                    i.putExtra("nick", newNick);
                    finish();
                    startActivity(i);
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MainMenu.class);
                i.putExtra("nick",existingNick);
                finish();
                startActivity(i);
            }
        });

    }





}
