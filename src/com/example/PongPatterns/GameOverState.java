package com.example.PongPatterns;

import android.graphics.Canvas;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.view.MotionEvent;
import sheep.game.State;
import sheep.game.World;
import sheep.graphics.Color;
import sheep.graphics.Font;
import sheep.input.TouchListener;

public class GameOverState extends State implements TouchListener {

    Font font = new Font(255, 255, 255, 20, Typeface.MONOSPACE, Typeface.NORMAL);
    private World world = new World();
    private int winner;
    private String textLine1 = "Game over";
    private String textline3 = "Touch the screen to start a new game";

    public GameOverState() {
        if (Score.getScore1() > Score.getScore2()) {
            winner = 1;
        } else winner = 2;
        font.setTextAlign(Align.CENTER);
    }

    public void draw(Canvas canvas) {

        canvas.drawPaint(Color.BLACK);

        canvas.drawText(textLine1, getGame().getWidth() / 2, getGame().getHeight() / 2, font);
        canvas.drawText("Player" + winner + "won!", getGame().getWidth() / 2, (getGame().getHeight() / 2) + 30, font);
        canvas.drawText(textline3, getGame().getWidth() / 2, (getGame().getHeight() / 2) + 100, font);
    }

    public void update(float dt) {
        world.update(dt);
    }

    public boolean onTouchDown(MotionEvent event) {
        getGame().popState();
        Score.resetScores();
        getGame().pushState(new GameState());
        return true;
    }
}