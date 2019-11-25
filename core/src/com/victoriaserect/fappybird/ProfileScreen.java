package com.victoriaserect.fappybird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.concurrent.TimeUnit;


public class ProfileScreen implements Screen {

    private Stage stage;
    private Game game;
    Skin gameSkin, rainbowFontSkin, orange, quantum;

    LocalDataHandler localDataHandler;
    String userDataString = "No user was saved yet";

    public ProfileScreen(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());
        gameSkin = new Skin(Gdx.files.internal("skin/metal-ui.json"));
        rainbowFontSkin = new Skin(Gdx.files.internal("rainbow/skin/rainbow-ui.json"));
        orange = new Skin(Gdx.files.internal("orange/skin/uiskin.json"));
        localDataHandler = new LocalDataHandler();
        UserData user = localDataHandler.getUserData();
        Label title = new Label("Profile", rainbowFontSkin);
        title.setAlignment(Align.center);
        title.setY((float)((Gdx.graphics.getHeight() - title.getHeight()) * 0.8));
        title.setFontScale(3);
        title.setWidth(Gdx.graphics.getWidth());
        stage.addActor(title);

        Table stageTable = new Table();
        stageTable.setFillParent(false);
        stageTable.setWidth(Gdx.graphics.getWidth());
        Label username = new Label("Username ", rainbowFontSkin);
        username.setFontScale(1.2f);
        Label premium = new Label("Premium user ", rainbowFontSkin);
        premium.setFontScale(1.2f);
        Label firstGame = new Label("First game played ", rainbowFontSkin);
        firstGame.setFontScale(1.2f);
        Label totalGame = new Label("Total game played ", rainbowFontSkin);
        totalGame.setFontScale(1.2f);
        Label totalTime = new Label("Total time played ", rainbowFontSkin);
        totalTime.setFontScale(1.2f);
        Label highScore = new Label("Highest score ", rainbowFontSkin);
        highScore.setFontScale(1.2f);

        Table left = new Table(rainbowFontSkin);
        left.setWidth(Gdx.graphics.getWidth()/2);
        left.add(username).right();
        left.row();
        left.add(premium).right();
        left.row();
        left.add(firstGame).right();
        left.row();
        left.add(totalGame).right();
        left.row();
        left.add(totalTime).right();
        left.row();
        left.add(highScore).right();

        Label usernameValue = new Label(user.getUserName(), rainbowFontSkin);
        usernameValue.setFontScale(1.2f);
        Label premiumValue = new Label(String.valueOf(user.isPremium()), rainbowFontSkin);
        premiumValue.setFontScale(1.2f);
        Label firstGameValue = new Label(String.valueOf(user.getFirstGameHumanized()), rainbowFontSkin);
        firstGameValue.setFontScale(1.2f);
        Label totalGameValue = new Label(String.valueOf(user.getTotalPlayedGames()), rainbowFontSkin);
        totalGameValue.setFontScale(1.2f);
        Label totalTimeValue = new Label(String.valueOf(millisToDuration(user.getTotalPlayedTime())), rainbowFontSkin);
        totalTimeValue.setFontScale(1.2f);
        Label highScoreValue = new Label(String.valueOf(user.getHighestScore()), rainbowFontSkin);
        highScoreValue.setFontScale(1.2f);

        Table right = new Table(rainbowFontSkin);
        right.setWidth(Gdx.graphics.getWidth()/2);
        right.add(usernameValue).left();
        right.row();
        right.add(premiumValue).left();
        right.row();
        right.add(firstGameValue).left();
        right.row();
        right.add(totalGameValue).left();
        right.row();
        right.add(totalTimeValue).left();
        right.row();
        right.add(highScoreValue).left();

        SplitPane split = new SplitPane(left, right, false, rainbowFontSkin);
        stageTable.add(split).fill().expand();
        stageTable.setPosition(Gdx.graphics.getWidth() - left.getWidth(), Gdx.graphics.getHeight()/2, Align.center);
        stage.addActor(stageTable);

        TextButton backButton = new TextButton("Back", rainbowFontSkin, "small");
        backButton.setWidth(Gdx.graphics.getWidth()/3);
        backButton.setHeight(backButton.getWidth()/3);
        backButton.getLabel().setFontScale(3);
        backButton.setPosition(Gdx.graphics.getWidth()/2 - backButton.getWidth()/2,backButton.getHeight()*2);
        backButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new HomeScreen(game));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(backButton);


    }

    public String millisToDuration(long millis) {
        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
        millis -= TimeUnit.MILLISECONDS.toSeconds(millis);

        StringBuilder sb = new StringBuilder(64);
        //sb.append(days);
        //sb.append(" Days ");
        if (hours > 0) {
            sb.append(hours);
            sb.append(" Hours ");
        }
        sb.append(minutes);
        sb.append(" Minutes ");
        sb.append(seconds);
        //sb.append('.');
        //sb.append(millis);
        sb.append(" Seconds");
        return(sb.toString());
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

