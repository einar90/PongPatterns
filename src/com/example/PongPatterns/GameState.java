package com.example.PongPatterns;

import android.graphics.Canvas;
import android.graphics.Typeface;
import android.view.Display;
import android.view.MotionEvent;
import com.example.Pong_test.R;
import sheep.collision.CollisionLayer;
import sheep.collision.CollisionListener;
import sheep.game.Sprite;
import sheep.game.State;
import sheep.game.World;
import sheep.graphics.Color;
import sheep.graphics.Font;
import sheep.graphics.Image;
import sheep.input.TouchListener;

public class GameState extends State implements TouchListener, CollisionListener {

    private int canvasHeight, canvasWidth;
    private int player1Score, player2Score;
    private int winScore = 21, winner = 0;
    private Sprite paddle1, paddle2, ball;
    private Display display;
    private Canvas canvas;
    private CollisionLayer collisionLayer = new CollisionLayer();
    private World world = new World();
    private Image paddleImage = new Image(R.drawable.paddle);
    private Image ballImage = new Image(R.drawable.ball);
    Font font = new Font(255, 255, 255, 30, Typeface.MONOSPACE, Typeface.NORMAL);


    public GameState(Display display) {
        this.display = display;

        paddle1 = new Sprite(paddleImage);
        paddle2 = new Sprite(paddleImage);
        ball = new Sprite(ballImage);
        //ball = Ball.instance(ballImage);

        collisionLayer.addSprite(paddle1);
        collisionLayer.addSprite(paddle2);
        collisionLayer.addSprite(ball);

        player1Score = 0;
        player2Score = 0;

        paddle1.setPosition(display.getHeight() / 2, display.getHeight() / 4);
        paddle2.setPosition(display.getHeight() / 2, display.getHeight() - (display.getHeight() / 4));

        ball.setPosition(display.getWidth() / 2, display.getHeight() / 2);
        ball.setSpeed(100, 100);

        paddle1.addCollisionListener(this);
        paddle2.addCollisionListener(this);
        ball.addCollisionListener(this);

        world.addLayer(collisionLayer);
    }


    public void draw(Canvas canv) {
        this.canvas = canv;
        canvasHeight = canvas.getHeight();
        canvasWidth = canvas.getWidth();

        canvas.drawPaint(Color.BLACK);

        canvas.drawText("Score player 1: " + player1Score, 20, 30, font);
        canvas.drawText("Score player 2: " + player2Score, canvasWidth - 350, canvasHeight - 20, font);

        paddle1.draw(canvas);
        paddle2.draw(canvas);
        ball.draw(canvas);
        world.draw(canvas);
    }


    public void update(float dt) {
        if (canvas != null) {

            checkWallCollision();

            if (!isGameOver()) {
                checkPlayerPoint();
            } else {
                endGame();
            }

            ball.update(dt);
            paddle2.update(dt);
            world.update(dt);
        }
    }

    public boolean onTouchMove(MotionEvent event) {
        if (event.getY() < display.getHeight() / 2) {
            if (event.getX() < (display.getWidth() - (paddleImage.getWidth() / 2)) && event.getX() > (paddleImage.getWidth() / 2)) {
                paddle1.setPosition(event.getX(), paddle1.getPosition().getY());
                return true;
            }
        } else {
            if (event.getX() < (display.getWidth() - (paddleImage.getWidth() / 2)) && event.getX() > (paddleImage.getWidth() / 2)) {
                paddle2.setPosition(event.getX(), paddle2.getPosition().getY());
                return true;
            }
        }

        return false;
    }

    public void collided(Sprite a, Sprite b) {

        // Checking for collision with player 2 paddle
        if (ball.getY() > display.getHeight() - (display.getHeight() / 4) - paddleImage.getHeight() - (ballImage.getHeight() / 2)) {
            ball.setYSpeed(-ball.getSpeed().getY());
            ball.setPosition(ball.getX(), ball.getY() - (ballImage.getHeight() / 2));
        }

        // Checking for collision with player 1 paddle
        if (ball.getY() < (display.getHeight() / 4) + paddleImage.getHeight() + (ballImage.getHeight() / 2)) {
            ball.setYSpeed(-ball.getSpeed().getY());
            ball.setPosition(ball.getX(), ball.getY() + (ballImage.getHeight() / 2));
        }
    }

    public void checkWallCollision() {
        if (ball.getX() >= (canvasWidth - ballImage.getWidth() / 2) || ball.getX() <= (ballImage.getWidth() / 2)) {
            ball.setSpeed(-ball.getSpeed().getX(), ball.getSpeed().getY());
        }
    }

    public boolean isGameOver() {
        if (player1Score >= winScore || player2Score >= winScore) return true;
        else return false;
    }

    private void checkPlayerPoint() {
        if (ball.getY() >= canvasHeight) {
            player1Score++;
            resetBall();
        } else if (ball.getY() <= 0) {
            player2Score++;
            resetBall();
        }
    }

    private void resetBall() {
        ball.setPosition(display.getWidth() / 2, display.getHeight() / 2);
        ball.setYSpeed(-ball.getSpeed().getY());
    }

    private void endGame() {
        if (player1Score == winScore) {
            winner = 1;
        }
        if (player2Score == winScore) {
            winner = 2;
        }
        getGame().popState();
        getGame().pushState(new GameOverState(winner, display));
    }


}
