package com.example.rzr.fre0025_tamz2_projekt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by RZr on 17.12.2017.
 */

public class Score extends Activity {

    TextView score;
    Button backToMenu;
    String nick;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Intent i = getIntent();
        nick = i.getStringExtra("nick");

        score = (TextView)findViewById(R.id.score);
        backToMenu = (Button)findViewById(R.id.backToMenu);

        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MainMenu.class);
                finish();
                i.putExtra("nick",nick);
                startActivity(i);
            }
        });
        List<String> list = new ArrayList<>();
        BufferedReader r = null;
        try {
            r = new BufferedReader(new InputStreamReader(openFileInput("save.txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        String line;

        try {
            while((line = r.readLine()) != null){

                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(list);
        int row = 1;
        for(String s : list){
            if(row > 10)
                break;
            sb.append(row+". ");
            sb.append(s);
            sb.append("\n");
            row++;
        }

        score.setText(sb);



    }

    public void deleteTable(View view) {
        try {
            //open file for writing
            OutputStreamWriter out = new OutputStreamWriter(openFileOutput("save.txt", MODE_PRIVATE));
            out.write("");
            out.close();
            //Toast.makeText(getApplicationContext(),"Content deleted",Toast.LENGTH_LONG).show();

        } catch (java.io.IOException e) {
            //Toast.makeText(getApplicationContext(), "Text Could not be deleted",Toast.LENGTH_LONG).show();
        }
        finish();
        startActivity(getIntent());

    }
}
