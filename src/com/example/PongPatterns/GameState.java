package com.example.PongPatterns;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Typeface;
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

    Font font = new Font(255, 255, 255, 30, Typeface.MONOSPACE, Typeface.NORMAL);
    private Sprite paddle1, paddle2, ball;
    private Point boardSize;
    private Canvas canvas;
    private CollisionLayer collisionLayer = new CollisionLayer();
    private World world = new World();
    private Image paddleImage = new Image(R.drawable.paddle);
    private boolean resetSpriteStates = true;

    public GameState() {

        getSprites();
        addSpritesToCollisionLayer();
        addCollisionListeners();


        world.addLayer(collisionLayer);
    }

    private void addCollisionListeners() {
        paddle1.addCollisionListener(this);
        paddle2.addCollisionListener(this);
        ball.addCollisionListener(this);
    }

    private void getSprites() {
        paddle1 = Paddle.getPaddle1();
        paddle2 = Paddle.getPaddle2();
        ball = Ball.getBall();
    }

    private void addSpritesToCollisionLayer() {
        collisionLayer.addSprite(paddle1);
        collisionLayer.addSprite(paddle2);
        collisionLayer.addSprite(ball);
    }

    private void setInitialSpriteStates() {
        Ball.setInitialState(boardSize);
        Paddle.setInitialPaddlePositions(boardSize);
    }

    public void draw(Canvas canv) {
        this.canvas = canv;
        boardSize = new Point(canvas.getWidth(), canvas.getHeight());

        canvas.drawPaint(Color.BLACK);

        canvas.drawText("Score player 1: " + Score.getScore1(), 20, 30, font);
        canvas.drawText("Score player 2: " + Score.getScore2(), boardSize.x - 350, boardSize.y - 20, font);

        paddle1.draw(canvas);
        paddle2.draw(canvas);
        ball.draw(canvas);
        world.draw(canvas);
    }

    public void update(float dt) {
        if (canvas != null) {

            if (resetSpriteStates) {
                setInitialSpriteStates();
                resetSpriteStates = false;
            }

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
        if (event.getY() < boardSize.y / 2) {
            if (event.getX() < (boardSize.x - (paddleImage.getWidth() / 2)) && event.getX() > (paddleImage.getWidth() / 2)) {
                paddle1.setPosition(event.getX(), paddle1.getPosition().getY());
                return true;
            }
        } else {
            if (event.getX() < (boardSize.x - (paddleImage.getWidth() / 2)) && event.getX() > (paddleImage.getWidth() / 2)) {
                paddle2.setPosition(event.getX(), paddle2.getPosition().getY());
                return true;
            }
        }

        return false;
    }

    public void collided(Sprite a, Sprite b) {

        // Checking for collision with player 2 paddle
        if (ball.getY() > boardSize.y - (boardSize.y / 5) - paddleImage.getHeight() - (Ball.getHeight() / 2)) {
            ball.setYSpeed(-ball.getSpeed().getY());
            ball.setPosition(ball.getX(), ball.getY() - ((int) Ball.getHeight()));
        }

        // Checking for collision with player 1 paddle
        if (ball.getY() < (boardSize.y / 5) + paddleImage.getHeight() + (Ball.getHeight() / 2)) {
            ball.setYSpeed(-ball.getSpeed().getY());
            ball.setPosition(ball.getX(), ball.getY() + ((int) Ball.getHeight()));
        }
    }

    public void checkWallCollision() {
        if (ball.getX() >= (boardSize.x - Ball.getWidth()) || ball.getX() <= (Ball.getWidth())) {
            ball.setSpeed(-ball.getSpeed().getX(), ball.getSpeed().getY());
        }
    }

    public boolean isGameOver() {
        int winScore = 3;
        return Score.getScore1() >= winScore || Score.getScore2() >= winScore;
    }

    private void checkPlayerPoint() {
        if (ball.getY() >= boardSize.y) {
            Score.increment1();
            resetSpriteStates = true;
        } else if (ball.getY() <= 0) {
            Score.increment2();
            resetSpriteStates = true;
        }
    }


    private void endGame() {
        getGame().popState();
        getGame().pushState(new GameOverState());
    }


}
