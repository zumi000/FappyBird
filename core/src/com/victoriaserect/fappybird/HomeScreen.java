package com.victoriaserect.fappybird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class HomeScreen implements Screen {

    private Stage stage;
    private Game game;
    Skin gameSkin, rainbowFontSkin;
    SpriteBatch batch;
    LocalDataHandler localDataHandler;

    public HomeScreen(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());
        gameSkin = new Skin(Gdx.files.internal("skin/metal-ui.json"));
        rainbowFontSkin = new Skin(Gdx.files.internal("rainbow/skin/rainbow-ui.json"));

        batch = new SpriteBatch();
        //localDataHandler = new LocalDataHandler("NEW_USER_AT_EVERY_START_OF_THE_HOME_SCREEN"); //TODO: name input debugger
        localDataHandler = new LocalDataHandler();

        Label title = new Label("Main menu", rainbowFontSkin);
        title.setAlignment(Align.center);
        float titleY = (float)((Gdx.graphics.getHeight() - title.getHeight()) * 0.8);
        title.setY(titleY);
        title.setFontScale(3);
        title.setWidth(Gdx.graphics.getWidth());
        stage.addActor(title);

        TextButton scoresButton = new TextButton("Settings", rainbowFontSkin, "small");
        scoresButton.setWidth(Gdx.graphics.getWidth()/3);
        scoresButton.setHeight(scoresButton.getWidth()/3);
        scoresButton.getLabel().setFontScale(3);
        scoresButton.setPosition(Gdx.graphics.getWidth()/2-scoresButton.getWidth()/2, Gdx.graphics.getHeight()/2 + scoresButton.getHeight()*2);
        scoresButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new SettingsScreen(game));
            }

            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(scoresButton);

        TextButton settingsButton = new TextButton("Profile", rainbowFontSkin, "small");
        settingsButton.setWidth(Gdx.graphics.getWidth()/3);
        settingsButton.setHeight(settingsButton.getWidth()/3);
        settingsButton.getLabel().setFontScale(3);
        settingsButton.setPosition(Gdx.graphics.getWidth()/2-scoresButton.getWidth()/2, Gdx.graphics.getHeight()/2);
        settingsButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new ProfileScreen(game));

            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;

            }
        });
        stage.addActor(settingsButton);

        final Label welcomeBack = new Label("Welcome " + localDataHandler.getUserData().getUserName() + "!", rainbowFontSkin);
        final Label welcomeNew = new Label("Please enter your name!", rainbowFontSkin);
        final TextField usernameTextField = new TextField("", rainbowFontSkin);
        final TextButton playButton = new TextButton("Play!", rainbowFontSkin, "small-toggle");
        final TextButton saveNameButton = new TextButton("Save", rainbowFontSkin, "small-toggle");
        welcomeNew.setVisible(false);
        usernameTextField.setVisible(false);
        saveNameButton.setVisible(false);

        if (localDataHandler.getUserData().getUserName().equals(localDataHandler.DEFAULT_USERNAME)) {
            welcomeBack.setVisible(false);
            playButton.setVisible(false);
            welcomeNew.setVisible(true);
            usernameTextField.setVisible(true);
            saveNameButton.setVisible(true);
        }

        welcomeNew.setAlignment(Align.center);
        welcomeNew.getStyle().font.getData().setScale(5f);
        welcomeNew.setSize(Gdx.graphics.getWidth()/3*2, scoresButton.getHeight());
        welcomeNew.setPosition((Gdx.graphics.getWidth()-welcomeNew.getWidth())/2, Gdx.graphics.getHeight()/2 - scoresButton.getHeight() * 3 / 2);
        stage.addActor(welcomeNew);

        welcomeBack.setAlignment(Align.center);
        welcomeBack.getStyle().font.getData().setScale(5f);
        welcomeBack.setSize(Gdx.graphics.getWidth()/3*2, scoresButton.getHeight());
        welcomeBack.setPosition((Gdx.graphics.getWidth()-welcomeBack.getWidth())/2, Gdx.graphics.getHeight()/2 - scoresButton.getHeight() * 3 / 2);
        stage.addActor(welcomeBack);

        usernameTextField.setAlignment(Align.center);
        usernameTextField.getStyle().font.getData().setScale(3.5f);
        usernameTextField.setSize(Gdx.graphics.getWidth()/3*2, scoresButton.getHeight());
        usernameTextField.setPosition(Gdx.graphics.getWidth()/2-usernameTextField.getWidth()/2, Gdx.graphics.getHeight()/2 - scoresButton.getHeight() * 3);
        stage.addActor(usernameTextField);

        saveNameButton.setWidth(Gdx.graphics.getWidth()/2);
        saveNameButton.setHeight(saveNameButton.getWidth()/2);
        saveNameButton.getLabel().setFontScale(3);
        saveNameButton.setPosition(Gdx.graphics.getWidth()/2-saveNameButton.getWidth()/2,scoresButton.getHeight());
        saveNameButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("|" + usernameTextField.getText().trim() + "|");
                if (usernameTextField.getText().equals("") ||
                        usernameTextField.getText().trim().equals(" ") ||
                        usernameTextField.getText().trim().isEmpty()
                 ) {
                } else {
                    localDataHandler.writeUser(new UserData(usernameTextField.getText().trim(), Gdx.app.getVersion()));
                    welcomeNew.setVisible(false);
                    usernameTextField.setVisible(false);
                    saveNameButton.setVisible(false);
                    welcomeBack.setText("Welcome " + localDataHandler.getUserData().getUserName() + "!");
                    welcomeBack.setVisible(true);
                    playButton.setVisible(true);
                }
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(saveNameButton);

        playButton.setWidth(Gdx.graphics.getWidth()/2);
        playButton.setHeight(playButton.getWidth()/2);
        playButton.getLabel().setFontScale(3);
        playButton.setPosition(Gdx.graphics.getWidth()/2-playButton.getWidth()/2,scoresButton.getHeight());
        playButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
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