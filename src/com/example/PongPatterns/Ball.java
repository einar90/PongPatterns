package com.example.PongPatterns;

import android.graphics.Point;
import android.view.Display;
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
    private static final Ball ball = (Ball) new Sprite(new Image(R.drawable.ball));

    protected Ball() {
    }

    public static Ball getBall() {
        return ball;
    }

    public static void setInitialState(Display display) {
        Point size = new Point();
        display.getSize(size);
        ball.setPosition(size.x / 2, size.y / 2);
        ball.setSpeed(150, 100);
    }
}
