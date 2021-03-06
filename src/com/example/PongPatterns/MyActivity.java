package com.example.PongPatterns;

import android.app.Activity;
import android.os.Bundle;
import sheep.game.Game;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Game game = new Game(this, null);
        //Push the main state.
        game.pushState(new GameState());
        //View the game.
        setContentView(game);
    }
}
