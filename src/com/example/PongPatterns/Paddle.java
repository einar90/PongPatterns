package com.example.PongPatterns;

import android.graphics.Point;
import com.example.Pong_test.R;
import sheep.game.Sprite;
import sheep.graphics.Image;

/**
 * Created by:
 * User: Einar
 * Date: 15.02.13
 * Time: 12:25
 * Project: Pong_test
 * Package: com.example.PongPatterns
 */
public class Paddle extends Sprite {

    private static Image paddleImage = new Image(R.drawable.paddle);
    private static final Sprite paddle1 = new Sprite(paddleImage);
    private static final Sprite paddle2 = new Sprite(paddleImage);

    public static Sprite getPaddle1() {
        return paddle1;
    }

    public static Sprite getPaddle2() {
        return paddle2;
    }

    public static void setInitialPaddlePositions(Point size) {
        paddle1.setPosition(size.x / 2, size.y / 5);
        paddle2.setPosition(size.x / 2, size.y - size.y / 5);
    }

    public static Point size() {
        return new Point((int) paddleImage.getWidth(), (int) paddleImage.getHeight());
    }

}
