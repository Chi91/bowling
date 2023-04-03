package com.example.bowling_calculation;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalculatorLogic player1 = new CalculatorLogic();
        Button btnThrow = findViewById(R.id.btnThrow);
        Button btnReset = findViewById(R.id.btnReset);
        TextView turn = findViewById(R.id.turn);
        TextView round = findViewById(R.id.round);
        TextView firstScore = findViewById(R.id.firstScore);
        TextView secondScore = findViewById(R.id.secondScore);
        TextView currentScore = findViewById(R.id.currentScore);
        TextView totalScore = findViewById(R.id.totalScore);

        btnThrow.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            public void onClick(View v) {
                player1.throwBall();
                if(player1.getSpare()){
                    Toast.makeText(MainActivity.this,"Spare",Toast.LENGTH_SHORT).show();
                }
                if(player1.getStrike()){
                    Toast.makeText(MainActivity.this,"Strike",Toast.LENGTH_SHORT).show();
                }
                turn.setText("turn: " + player1.getAmountThrow());
                firstScore.setText("first score: " + player1.firstScore());
                secondScore.setText("second score: " +player1.getSecondScore());
                currentScore.setText("current score: " + player1.getCurrentScore());
                round.setText("round: " + player1.getRound());
                totalScore.setText("total score: " + player1.getTotalScore());

                if(player1.getGameOver()){
                    turn.setText(("turn: 0"));
                    firstScore.setText("first score: 0");
                    secondScore.setText("second score: 0");
                    currentScore.setText("current score: 0");
                    round.setText("round: 0");
                    totalScore.setText("total score: 0");
                    Toast.makeText(MainActivity.this,"Game Over",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                player1.resetGame();
                turn.setText(("turn: 0"));
                firstScore.setText("first score: 0");
                secondScore.setText("second score: 0");
                currentScore.setText("current score: 0");
                round.setText("round: 0");
                totalScore.setText("total score: 0");
            }
        });
    }
}