package com.example.PongPatterns;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Typeface;
import android.view.MotionEvent;
import sheep.collision.CollisionLayer;
import sheep.collision.CollisionListener;
import sheep.game.Sprite;
import sheep.game.State;
import sheep.game.World;
import sheep.graphics.Color;
import sheep.graphics.Font;
import sheep.input.TouchListener;

public class GameState extends State implements TouchListener, CollisionListener {

    Font font = new Font(255, 255, 255, 20, Typeface.MONOSPACE, Typeface.NORMAL);
    private Sprite paddle1, paddle2, ball;
    private Point boardSize;
    private CollisionLayer collisionLayer = new CollisionLayer();
    private World world = new World();
    private boolean resetSpriteStates = true;
    private boolean firstDrawExecuted = false;

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

    public void draw(Canvas canvas) {
        firstDrawExecuted = true;
        boardSize = new Point(canvas.getWidth(), canvas.getHeight());

        canvas.drawPaint(Color.BLACK);

        canvas.drawText(Score.getString1(), 20, 30, font);
        canvas.drawText(Score.getString2(), boardSize.x - 350, boardSize.y - 20, font);

        paddle1.draw(canvas);
        paddle2.draw(canvas);
        ball.draw(canvas);
        world.draw(canvas);
    }

    public void update(float dt) {
        // Update before first draw causes a crash because the board size is set in the draw method
        if (!firstDrawExecuted) return;

        if (resetSpriteStates) {
            setInitialSpriteStates();
            resetSpriteStates = false;
        }

        if (isGameOver()) {
            endGame();
        } else {
            checkPlayerPoint();
        }

        Ball.checkBallWallCollision(boardSize);

        ball.update(dt);
        paddle1.update(dt);
        paddle2.update(dt);
        world.update(dt);
    }

    public boolean onTouchMove(MotionEvent event) {
        if (event.getY() < boardSize.y / 2) {
            if (event.getX() < (boardSize.x - (Paddle.size().x / 2)) && event.getX() > (Paddle.size().x / 2)) {
                paddle1.setPosition(event.getX(), paddle1.getPosition().getY());
                return true;
            }
        } else {
            if (event.getX() < (boardSize.x - (Paddle.size().x / 2)) && event.getX() > (Paddle.size().x / 2)) {
                paddle2.setPosition(event.getX(), paddle2.getPosition().getY());
                return true;
            }
        }

        return false;
    }

    public void collided(Sprite a, Sprite b) {

        // Checking for collision with player 2 paddle
        if (ball.getY() > boardSize.y - (boardSize.y / 5) - Paddle.size().y - (Ball.size().y / 2)) {
            ball.setYSpeed(-ball.getSpeed().getY());
            ball.setPosition(ball.getX(), ball.getY() - ((int) Ball.size().y));
        }

        // Checking for collision with player 1 paddle
        if (ball.getY() < (boardSize.y / 5) + Paddle.size().y + (Ball.size().y / 2)) {
            ball.setYSpeed(-ball.getSpeed().getY());
            ball.setPosition(ball.getX(), ball.getY() + ((int) Ball.size().y));
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
