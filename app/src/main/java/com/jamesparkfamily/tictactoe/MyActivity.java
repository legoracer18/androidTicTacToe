package com.jamesparkfamily.tictactoe;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MyActivity extends Activity {

    private TicTacToeGame mGame;

    private Button mBoardButtons[];

    private TextView mInfoTextView;
    private TextView mHumanCount;
    private TextView mTieCount;
    private TextView mAndroidCount;

    private int mHumanCounter = 0;
    private int mTieCounter = 0;
    private int mAndroidCounter = 0;

    private boolean mHumanFirst = true;
    private boolean mGameOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        mBoardButtons = new Button[mGame.getBOARD_SIZE()];
        mBoardButtons[0] = (Button) findViewById(R.id.one);
        mBoardButtons[1] = (Button) findViewById(R.id.two);
        mBoardButtons[2] = (Button) findViewById(R.id.three);
        mBoardButtons[3] = (Button) findViewById(R.id.four);
        mBoardButtons[4] = (Button) findViewById(R.id.five);
        mBoardButtons[5] = (Button) findViewById(R.id.six);
        mBoardButtons[6] = (Button) findViewById(R.id.seven);
        mBoardButtons[7] = (Button) findViewById(R.id.eight);
        mBoardButtons[8] = (Button) findViewById(R.id.nine);

        mInfoTextView = (TextView) findViewById(R.id.information);
        mHumanCount = (TextView) findViewById(R.id.human_count);
        mTieCount = (TextView) findViewById(R.id.tie_count);
        mAndroidCount = (TextView) findViewById(R.id.android_count);

        mHumanCount.setText(Integer.toString(mHumanCounter));
        mTieCount.setText(Integer.toString(mTieCounter));
        mAndroidCount.setText(Integer.toString(mAndroidCounter));

        mGame = new TicTacToeGame();

        startNewGame();
    }


    private void startNewGame() {
        mGame.clearBoard();

        for (int i = 0; i < mBoardButtons.length; i++) {
//            Keep the following line if there was text on the buttons
//            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
            mBoardButtons[i].setBackground(getResources().getDrawable(R.drawable.blank));
        }

        if (mHumanFirst) {
            mInfoTextView.setText(R.string.first_human);
            mHumanFirst = false;
        } else {
            mInfoTextView.setText(R.string.turn_computer);
            int move = mGame.getComputerMove();
            setMove(mGame.ANDRIOD_PLAYER, move);
            mHumanFirst = true;
        }

        mGameOver = false;
    }

    private class ButtonClickListener implements View.OnClickListener {
        int location;

        public ButtonClickListener(int location) {
            this.location = location;
        }

        public void onClick(View view) {
            // Check to make sure the game is not over
            if (!mGameOver) {
                // Check to make sure the button is enabled.
                if (mBoardButtons[location].isEnabled()) {
                    setMove(mGame.HUMAN_PLAYER, location);

                    int winner = mGame.checkForWinner();

                    if (winner == 0) {
                        mInfoTextView.setText(R.string.turn_computer);
                        int move = mGame.getComputerMove();
                        setMove(mGame.ANDRIOD_PLAYER, move);
                        winner = mGame.checkForWinner();
                    }

                    if (winner == 0)
                        mInfoTextView.setText(R.string.turn_human);
                    else if (winner == 1) {
                        mInfoTextView.setText(R.string.result_tie);
                        mTieCounter++;
                        mTieCount.setText(Integer.toString(mTieCounter));
                        mGameOver = true;
                    } else if (winner == 2) {
                        mInfoTextView.setText(R.string.result_human_wins);
                        mHumanCounter++;
                        mHumanCount.setText(Integer.toString(mHumanCounter));
                        mGameOver = true;
                    } else {
                        mInfoTextView.setText(R.string.result_android_wins);
                        mAndroidCounter++;
                        mAndroidCount.setText(Integer.toString(mAndroidCounter));
                        mGameOver = true;
                    }
                }
            }
        }
    }

    private void setMove(char player, int location) {
        mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
//        mBoardButtons[location].setText(String.valueOf(player));
        if (player == mGame.HUMAN_PLAYER) {
//            mBoardButtons[location].setTextColor(Color.GREEN);
            mBoardButtons[location].setBackground(getResources().getDrawable(R.drawable.x));
        }
        else {
//            mBoardButtons[location].setTextColor(Color.RED);
            mBoardButtons[location].setBackground(getResources().getDrawable(R.drawable.o));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newGame:
                startNewGame();
                break;
            case R.id.exitGame:
                MyActivity.this.finish();
                break;
        }

        return true;
    }
}
