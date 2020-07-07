package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private EditText player1;
    private EditText player2;
    private Button play;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //song = MediaPlayer.create(this,);
        player1 = (EditText)findViewById(R.id.text_one);
        player2 = (EditText)findViewById(R.id.text_two);
        play = (Button)findViewById(R.id.button);
        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String playerOne = player1.getText().toString();
                String playerTwo = player2.getText().toString();
                Intent gameIntent = new Intent(MainActivity.this,Game.class);
                gameIntent.putExtra("player1",playerOne);
                gameIntent.putExtra("player2",playerTwo);
                startActivity(gameIntent);
            }
        });

    }
}

