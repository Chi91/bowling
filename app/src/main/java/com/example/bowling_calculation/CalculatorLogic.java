package com.example.bowling_calculation;

import android.util.Log;
import java.util.Arrays;
import java.util.Random;

/**
 * Class CalculatorLogic represent the logic of an bowling game.
 * @author Chi Thien Pham
 * @version 1.0
 * */
public class CalculatorLogic {

    private int firstScore = 0;
    private int secondScore = 0;
    private int amountThrow = 0;
    private int round = 0;
    private boolean roundOver = false;
    private Random randomNum = new Random();
    private String[][] displayRoundThrowScores = new String[10][2];
    private int[] totalValue = new int[10];
    private boolean strike = false;
    private boolean spare = false;
    private boolean gameOver = false;
    private int thirdScore = 0;
    private boolean wasStrike = false;

    /**
     * simulate an throw of an player
     * limits the possible score
     * */
    public void throwBall(){
        if(round < 10 ){
            gameOver=false;
            if(roundOver){
                secondScore = 0;
                round++;
                amountThrow=0;
                if(round == 10){
                    gameOver = true;
                    resetGame();
                }
            }
            if(amountThrow == 0){
                int result = randomNum.nextInt(11);
                checkResult(result);
            }
            else if( round == 9 && strike || spare ){
                int result = randomNum.nextInt(11);
                checkResult(result);
            }
            else{
                int leftPins = 10 - firstScore;
                int result = randomNum.nextInt(leftPins + 1);
                checkResult(result);
            }
        }
    }
    /**
     *  Check which throw it is and what kind of score it is
     * */
    private void checkResult(int number){
        amountThrow++;
        //first throw
        if(amountThrow == 1){
            if(number == 10){
                isStrike();
            }
            else{
                firstScore = number;
                roundOver = false;
                if(spare){
                    calculateScore();
                }
            }
        }
        // second & third throw (round 9)
        else{
            //
            if(round == 9 && spare){
                thirdScore = number;
                calculateScore();
                roundOver = true;
            }
            if(round == 9 && strike){
                firstScore = number;
                strike = false;
                wasStrike = true;
            }
            else if((10 -(number + firstScore)) == 0){
                isSpare();
            }
            else{
                secondScore = number;
                addValueToCollection(Integer.toString(firstScore),Integer.toString(secondScore));
                calculateScore();
                roundOver = true;
            }
        }
    }
    /**
     * save the first and second throw in an 2D array
     * */
    private void addValueToCollection(String firstScoreValue, String secondScoreVale){
        displayRoundThrowScores[round][0] = firstScoreValue;
        displayRoundThrowScores[round][1] = secondScoreVale;
    }
    /**
     * how to calculate the score
     * different way for calculate spare or strike depends on which round the player is
     * */
    private void calculateScore(){
        int result = firstScore + secondScore;
        if(round == 0){
            totalValue[round] = result;
        }
        else if( spare && round == 1){
            totalValue[round -1] = 10 + firstScore;
            calcCurrentScore(result);
            spare = false;
        }
        else if( spare && round > 1 && round < 9){
            totalValue[round-1] = 10 + totalValue[round-2] + firstScore;
            calcCurrentScore(result);
            spare = false;
        }
        else if(spare && round == 9){
            totalValue[round] = totalValue[round-1] + 10 + thirdScore;
            spare = false;
        }
        else if(strike && round == 1){
            totalValue[round-1] = 10 + result;
            calcCurrentScore(result);
            strike = false;
        }
        else if(strike && round>1 && round < 9){
            totalValue[round-1] = 10 + totalValue[round-2] +result;
            calcCurrentScore(result);
            strike = false;
        }
        else if(wasStrike){
            totalValue[round] = result + 10 + totalValue[round -1];
            wasStrike = false;
        }
        else{
            calcCurrentScore(result);
        }
    }
    /**
     * Calculate the current score
     * take the previous score and add the current points of the first throw and the second throw
     * */
    private void calcCurrentScore(int value){
        totalValue[round] = value + totalValue[ round - 1];
    }
    /**
     * How to deal with a strike
     * */
    private void isStrike(){
        strike = true;
        if(round != 9){
            addValueToCollection("X"," - ");
            roundOver = true;
        }
    }
    /**
     * How to deal with a spare
     * */
    private void isSpare(){
        spare = true;
        addValueToCollection(Integer.toString(firstScore),"/");
        if(round != 9){
            roundOver= true;
        }
    }

    /**
     * after round 10, the game will be reseted
     * */
    public void resetGame(){
        round = 0;
        amountThrow = 0;
        firstScore = 0;
        secondScore = 0;
        thirdScore = 0;
        roundOver = false;
        strike = false;
        spare = false;
        wasStrike = false;
        Arrays.fill(totalValue,0);
        displayRoundThrowScores = new String[10][2];
    }

    /**
     * All getter */
    public int getAmountThrow(){
        return amountThrow ;
    }

    public int getRound(){
        return round ;
    }

    public int firstScore(){
       return firstScore;
    }

    public int getSecondScore(){
        return secondScore;
    }

    public int getCurrentScore(){
        return firstScore + secondScore;
    }

    public int getTotalScore(){
        if(amountThrow == 1 && round >0){
            return totalValue[round-1];
        }
        else{
            return totalValue[round];
        }
    }
    public boolean getStrike(){
        return strike;
    }
    public boolean getSpare(){
        return spare;
    }
    public boolean getGameOver(){
        return gameOver;
    }
}
