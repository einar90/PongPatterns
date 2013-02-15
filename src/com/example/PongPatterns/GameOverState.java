package com.example.PongPatterns;

import android.graphics.Canvas;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.view.Display;
import android.view.MotionEvent;
import sheep.game.State;
import sheep.game.World;
import sheep.graphics.Color;
import sheep.graphics.Font;
import sheep.input.TouchListener;

public class GameOverState extends State implements TouchListener {

    private World world = new World();
    Font font = new Font(255, 255, 255, 30, Typeface.MONOSPACE, Typeface.NORMAL);
    private int winner;
    private Display display;

    public GameOverState(int winner) {
        this.winner = winner;
        font.setTextAlign(Align.CENTER);
    }


    public void draw(Canvas canvas) {

        canvas.drawPaint(Color.BLACK);

        canvas.drawText("Game over", getGame().getWidth() / 2, getGame().getHeight() / 2, font);
        canvas.drawText("Player " + winner + " won!", getGame().getWidth() / 2, (getGame().getHeight() / 2) + 30, font);
        canvas.drawText("Touch the screen to start a new game", getGame().getWidth() / 2, (getGame().getHeight() / 2) + 100, font);
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