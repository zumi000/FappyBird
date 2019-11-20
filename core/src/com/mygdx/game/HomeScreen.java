package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class HomeScreen implements Screen {

    private Stage stage;
    private Game game;
    Skin gameSkin;
    SpriteBatch batch;

    public HomeScreen(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());
        gameSkin = new Skin(Gdx.files.internal("skin/metal-ui.json"));
        batch = new SpriteBatch();

        Label title = new Label("Main menu", gameSkin);
        title.setAlignment(Align.center);
        float titleY = (float)((Gdx.graphics.getHeight() - title.getHeight()) * 0.8);
        title.setY(titleY);
        title.setFontScale(8,8);
        title.setWidth(Gdx.graphics.getWidth());
        stage.addActor(title);

        TextButton scoresButton = new TextButton("High scores", gameSkin);
        scoresButton.setWidth(Gdx.graphics.getWidth()/3);
        scoresButton.setHeight(scoresButton.getWidth()/3);
        scoresButton.getLabel().setFontScale(3);
        scoresButton.setPosition(Gdx.graphics.getWidth()/2-scoresButton.getWidth()/2, Gdx.graphics.getHeight()/2 + scoresButton.getHeight()*2);
        scoresButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("touchup", "Play");
            }

            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("touchdown", "Play");
                return true;
            }
        });
        stage.addActor(scoresButton);

        TextButton settingsButton = new TextButton("Settings", gameSkin);
        settingsButton.setWidth(Gdx.graphics.getWidth()/3);
        settingsButton.setHeight(settingsButton.getWidth()/3);
        settingsButton.getLabel().setFontScale(3);
        settingsButton.setPosition(Gdx.graphics.getWidth()/2-scoresButton.getWidth()/2, Gdx.graphics.getHeight()/2);
        settingsButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("touchup", "Options");
                Gdx.app.log("Gdx.graphics.getDensity()", String.valueOf(Gdx.graphics.getDensity()));
                game.setScreen(new SettingsScreen(game));

            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("touchdown", "Options");
                return true;

            }
        });
        stage.addActor(settingsButton);

        TextButton playButton = new TextButton("Play!", gameSkin);
        playButton.setWidth(Gdx.graphics.getWidth()/2);
        playButton.setHeight(playButton.getWidth()/2);
        playButton.getLabel().setFontScale(4);
        playButton.setPosition(Gdx.graphics.getWidth()/2-playButton.getWidth()/2,scoresButton.getHeight());
        playButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("touchup", "Play");
                game.setScreen(new GameScreen(game));

            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("touchdown", "Play");
                return true;
            }
        });
        stage.addActor(playButton);






    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Texture backgound;
        backgound = new Texture("background.png");
        batch.begin();
        batch.draw(backgound, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
//        Gdx.gl.glClearColor(13.0f/255.0f, 59.0f/255.0f, 95.0f/255.0f, 1.0f);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}