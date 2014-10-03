package com.jamesparkfamily.tictactoe;

import java.util.Random;

/**
 * Created by James on 9/23/2014.
 */
public class TicTacToeGame {

    private char mBoard[];
    private final static int BOARD_SIZE = 9;

    public static final char HUMAN_PLAYER = 'X';
    public static final char ANDRIOD_PLAYER = 'O';
    public static final char EMPTY_SPACE = ' ';

    private Random mRand;

    public static int getBOARD_SIZE() {
        return BOARD_SIZE;
    }

    public TicTacToeGame() {

        mBoard = new char[BOARD_SIZE];

        for (int i = 0; i < BOARD_SIZE; i++) {
            mBoard[i] = EMPTY_SPACE;
        }

        mRand = new Random();
    }

    public void clearBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            mBoard[i] = EMPTY_SPACE;
        }
    }

    public void setMove(char player, int location) {
        mBoard[location] = player;
    }

    public int getComputerMove() {
        int move;

        // Check to see if there is a move that will allow the computer to win.
        for (int i = 0; i < getBOARD_SIZE(); i++) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != ANDRIOD_PLAYER) {
                char curr = mBoard[i];
                mBoard[i] = ANDRIOD_PLAYER;
                if (checkForWinner() == 3) {
                    setMove(ANDRIOD_PLAYER, i);
                    return i;
                }
                else
                    mBoard[i] = curr;
            }
        }

        // Check to see if there is a move that will cause the human to win, in order to block it.
        for (int i = 0; i < getBOARD_SIZE(); i++) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != ANDRIOD_PLAYER) {
                char curr = mBoard[i];
                mBoard[i] = HUMAN_PLAYER;
                if (checkForWinner() == 2) {
                    setMove(ANDRIOD_PLAYER, i);
                    return i;
                }
                else
                    mBoard[i] = curr;
            }
        }

        // Get a random spot that isn't already taken.
        do {
            move = mRand.nextInt(getBOARD_SIZE());
        } while (mBoard[move] == HUMAN_PLAYER || mBoard[move] == ANDRIOD_PLAYER);

        setMove(ANDRIOD_PLAYER, move);
        return move;
    }

    public int checkForWinner() {
        // Check horizontally for a win.
        for (int i = 0; i <= 6; i += 3) {
            if (mBoard[i] == HUMAN_PLAYER &&
                mBoard[i+1] == HUMAN_PLAYER &&
                mBoard[i+2] == HUMAN_PLAYER)
                return 2;
            if (mBoard[i] == ANDRIOD_PLAYER &&
                mBoard[i+1] == ANDRIOD_PLAYER &&
                mBoard[i+2] == ANDRIOD_PLAYER)
                return 3;
        }

        // Check vertically for a win.
        for (int i = 0; i <= 2; i++) {
            if (mBoard[i] == HUMAN_PLAYER &&
                mBoard[i+3] == HUMAN_PLAYER &&
                mBoard[i+6] == HUMAN_PLAYER)
                return 2;
            if (mBoard[i] == ANDRIOD_PLAYER &&
                mBoard[i+3] == ANDRIOD_PLAYER &&
                mBoard[i+6] == ANDRIOD_PLAYER)
                return 3;
        }

        // Check diagonally for a winner
        if ((mBoard[0] == HUMAN_PLAYER &&
             mBoard[4] == HUMAN_PLAYER &&
             mBoard[8] == HUMAN_PLAYER) ||
            (mBoard[2] == HUMAN_PLAYER &&
             mBoard[4] == HUMAN_PLAYER &&
             mBoard[6] == HUMAN_PLAYER))
            return 2;
        if ((mBoard[0] == ANDRIOD_PLAYER &&
             mBoard[4] == ANDRIOD_PLAYER &&
             mBoard[8] == ANDRIOD_PLAYER) ||
            (mBoard[2] == ANDRIOD_PLAYER &&
             mBoard[4] == ANDRIOD_PLAYER &&
             mBoard[6] == ANDRIOD_PLAYER))
            return 3;

        // Check for empty spaces
        for(int i = 0; i < getBOARD_SIZE(); i++) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != ANDRIOD_PLAYER)
                return 0;
        }

        return 1;
    }

}
