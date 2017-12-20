package com.example.rzr.fre0025_tamz2_projekt;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Created by RZr on 17.12.2017.
 */

public class Score extends Activity {

    TextView score;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        score = (TextView)findViewById(R.id.score);

        BufferedReader r = null;
        try {
            r = new BufferedReader(new InputStreamReader(openFileInput("save.txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder total = new StringBuilder();
        String line;
        try {
            while((line = r.readLine()) != null){
                total.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Toast.makeText(getApplicationContext(), total,Toast.LENGTH_LONG).show();
        score.setText(total);
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
