package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class SettingsScreen implements Screen {

    private Stage stage;
    private Game game;
    Skin gameSkin;

    LocalDataHandler localDataHandler;
    String userDataString = "No user was saved yet";

    public SettingsScreen(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());
        gameSkin = new Skin(Gdx.files.internal("skin/metal-ui.json"));
        localDataHandler = new LocalDataHandler();

        Label title = new Label("Settings", gameSkin);
        title.setAlignment(Align.center);
        title.setY((float)((Gdx.graphics.getHeight() - title.getHeight()) * 0.8));
        title.setFontScale(8,8);
        title.setWidth(Gdx.graphics.getWidth());
        stage.addActor(title);


        Label score = new Label(localDataHandler.getUserData().toString(), gameSkin);
        score.setAlignment(Align.center);
        score.setY((float)((Gdx.graphics.getHeight() - score.getHeight()) * 0.8) - title.getHeight() *2);
        score.setFontScale(2);
        score.setWidth(Gdx.graphics.getWidth());
        stage.addActor(score);

        TextButton saveButton = new TextButton("Save", gameSkin);
        saveButton.setWidth(Gdx.graphics.getWidth()/3);
        saveButton.setHeight(saveButton.getWidth()/3);
        saveButton.getLabel().setFontScale(4);
        saveButton.setPosition((Gdx.graphics.getWidth()/2 + (Gdx.graphics.getWidth()/2-saveButton.getWidth())/2),
                Gdx.graphics.getHeight()/4-saveButton.getHeight()/2);
        saveButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("touchup", "Save");
                stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("touchdown", "Save");
                return true;
            }
        });
        stage.addActor(saveButton);

        TextButton cancelButton = new TextButton("Cancel", gameSkin);
        cancelButton.setWidth(Gdx.graphics.getWidth()/3);
        cancelButton.setHeight(cancelButton.getWidth()/3);
        cancelButton.getLabel().setFontScale(4);
        cancelButton.setPosition((Gdx.graphics.getWidth()/2-cancelButton.getWidth())/2,Gdx.graphics.getHeight()/4-cancelButton.getHeight()/2);
        cancelButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("touchup", "Cancel");
                game.setScreen(new HomeScreen(game));

            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("touchdown", "Cancel");
                game.setScreen(new HomeScreen(game));
                return true;

            }
        });
        stage.addActor(cancelButton);




    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(13.0f/255.0f, 59.0f/255.0f, 95.0f/255.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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

