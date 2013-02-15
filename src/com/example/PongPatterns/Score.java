package com.example.PongPatterns;

/**
 * Created by:
 * User: Einar
 * Date: 15.02.13
 * Time: 12:37
 * Project: Pong_test
 * Package: com.example.PongPatterns
 */
public class Score {
    private static int score1 = 0;
    private static int score2 = 0;

    public static int getScore1() {
        return score1;
    }

    public static int getScore2() {
        return score2;
    }

    public static String getString1() {
        return ("Player 1: " + score1);
    }

    public static String getString2() {
        return ("Player 2: " + score2);
    }

    public static void increment1() {
        score1++;
    }

    public static void increment2() {
        score2++;
    }

    public static void resetScores() {
        score1 = 0;
        score2 = 0;
    }

}
