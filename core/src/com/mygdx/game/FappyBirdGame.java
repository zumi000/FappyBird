package com.mygdx.game;

import com.badlogic.gdx.Game;

public class FappyBirdGame extends Game {

    @Override
    public void create () {
        new LocalDataHandler().getUserData();
        this.setScreen(new HomeScreen(this));
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
