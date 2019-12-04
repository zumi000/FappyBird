package com.victoriaserect.fappybird;

import com.badlogic.gdx.Game;

public class FappyBirdGame extends Game {

    public FirebaseConnector firebaseConnector;

    public FappyBirdGame(FirebaseConnector firebaseConnector) {
        this.firebaseConnector = firebaseConnector;
    }

    @Override
    public void create () {
        this.setScreen(new HomeScreen(this, firebaseConnector));
    }

    @Override
    public void render () {
        super.render();
    }

    @Override
    public void dispose () {
        super.dispose();
    }
}
