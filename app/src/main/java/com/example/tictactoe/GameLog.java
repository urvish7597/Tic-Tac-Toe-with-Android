package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class GameLog extends AppCompatActivity {

    ListView gameLog;
    protected  static final String fileName="log.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_log);

        gameLog = (ListView)findViewById(R.id.game_log);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,FileIO.load(this,fileName)){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.WHITE);
                return view;
            }
        };
        gameLog.setAdapter(adapter);
    }
}
