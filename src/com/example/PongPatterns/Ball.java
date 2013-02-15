package com.example.PongPatterns;

import android.graphics.Point;
import com.example.Pong_test.R;
import sheep.game.Sprite;
import sheep.graphics.Image;

/**
 * Created by:
 * User: Einar
 * Date: 14.02.13
 * Time: 00:57
 * Project: Pong_test
 * Package: com.example.PongPatterns
 */
public class Ball extends Sprite {

    private static Image ballGraphics = new Image(R.drawable.ball);
    private static final Sprite ball = new Sprite(ballGraphics);

    protected Ball() {
    }

    public static Sprite getBall() {
        return ball;
    }

    public static void setInitialState(Point size) {
        ball.setPosition(size.x / 2, size.y / 2);
        if (ball.getSpeed().getY() == 0) {
            ball.setSpeed(75, 50);
        } else {
            ball.setYSpeed(-ball.getSpeed().getY());
        }
    }

    public static Point size() {
        return new Point((int) ballGraphics.getWidth(), (int) ballGraphics.getHeight());
    }

    public static void wallBounce(Point boardSize) {
        if (ball.getX() + size().x / 2 >= boardSize.x) {
            ball.setPosition(boardSize.x - size().x / 2, ball.getY());
            ball.setXSpeed(-ball.getSpeed().getX());
        }

        if (ball.getX() - size().x / 2 <= 0) {
            ball.setPosition(size().x / 2, ball.getY());
            ball.setXSpeed(-ball.getSpeed().getX());
        }
    }

    public static void paddleBounce() {
        // Checking for collision with player 1 paddle
        if (ball.getY() - size().y / 2 < Paddle.paddle1BounceLine()) {
            ball.setYSpeed(-ball.getSpeed().getY());
            ball.setPosition(ball.getX(), Paddle.paddle1BounceLine() + size().y / 2);
        }
        // Checking for collision with player 2 paddle
        if (ball.getY() + size().y / 2 > Paddle.paddle2BounceLine()) {
            ball.setYSpeed(-ball.getSpeed().getY());
            ball.setPosition(ball.getX(), Paddle.paddle2BounceLine() - size().y / 2);
        }


    }
}
