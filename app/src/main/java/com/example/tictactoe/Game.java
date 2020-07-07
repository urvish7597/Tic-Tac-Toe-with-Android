package com.example.tictactoe;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class Game  extends AppCompatActivity implements View.OnClickListener {

    MediaPlayer mySong;
    private Button[][] buttons = new Button[3][3];
    protected static int GameCount=1;
    private boolean player1Turn = true;

    private int roundCount;

    private int player1Points;
    private int player2Points;

    private Button History;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    protected TextView turn;
    protected String P1;
    protected String P2;
    protected String P1Mark = "X";
    protected String P2Mark = "O";
    protected  static final String fileName="log.txt";
    MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        player = MediaPlayer.create(this,R.raw.song);
        player.start();

        FileIO.deleteLog(this,fileName);
        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);
        turn = findViewById(R.id.text_view_turn);
         P1 = getIntent().getStringExtra("player1");
         P2 = getIntent().getStringExtra("player2");
        textViewPlayer1.setText(P1 + " :");
        textViewPlayer2.setText(P2+" :");
        turn.setText(P1+"'s turn (X)");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
        History = (Button) findViewById(R.id.button_records);
        History.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent gameLogIntent = new Intent(Game.this,GameLog.class);
                startActivity(gameLogIntent);
            }
        });
        resetGame();
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (player1Turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        roundCount++;

        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }

        setTurnTextView(player1Turn);
    }

    private void  setTurnTextView(boolean player1Turn)
    {
        if(player1Turn){
            turn.setText(P1+"'s turn (X)");
        }else{
            turn.setText(P2+"'s turn (O)");
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private void player1Wins() {
        player1Points++;
        Toast.makeText(this, P1+" wins!", Toast.LENGTH_SHORT).show();
        FileIO.save(this,fileName,"\nGame :"+GameCount+" => Result :"+P1+" wins!");
        updatePointsText();
        resetBoard();
        player1Turn = false;
        setTurnTextView(player1Turn);
        GameCount++;
    }

    private void player2Wins() {
        player2Points++;
        Toast.makeText(this, P2+" wins!", Toast.LENGTH_SHORT).show();
        FileIO.save(this,fileName,"\nGame :"+GameCount+" => Result :"+P2+" wins!");
        updatePointsText();
        resetBoard();
        player1Turn = true;
        setTurnTextView(player1Turn);
        GameCount++;
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        FileIO.save(this,fileName,"\nGame : "+GameCount+" => Result :"+"Draw between "+P1+" and "+P2);
        List<String> data = FileIO.load(this,fileName);
        resetBoard();
        player1Turn = !player1Turn;
        setTurnTextView(player1Turn);
        GameCount++;
    }

    private void updatePointsText() {
        textViewPlayer1.setText(P1 + " :" + player1Points);
        textViewPlayer2.setText(P2 + " :" + player2Points);
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        player1Turn = true;
    }

    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        GameCount = 1;
        updatePointsText();
        resetBoard();
        turn.setText(P1+"'s turn (X)");
        FileIO.deleteLog(this,fileName);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}