package com.victoriaserect.fappybird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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
    private LocalDataHandler localDataHandler;
    private FirebaseConnector firebaseConnector;

    Skin gameSkin, rainbowFontSkin, orange;
    Toast toast;

    public ProfileScreen(Game aGame, FirebaseConnector connector) {
        game = aGame;
        stage = new Stage(new ScreenViewport());

        localDataHandler = new LocalDataHandler();
        UserData user = localDataHandler.getUserData();
        firebaseConnector = connector;

        gameSkin = new Skin(Gdx.files.internal("skin/metal-ui.json"));
        rainbowFontSkin = new Skin(Gdx.files.internal("rainbow/skin/rainbow-ui.json"));
        orange = new Skin(Gdx.files.internal("orange/skin/uiskin.json"));

        Toast.ToastFactory toastFactory = new Toast.ToastFactory.Builder()
                .font(rainbowFontSkin.getFont("font"))
                .backgroundColor(new Color(0.5f, 0.5f, 0.5f, 1f))
                .fadingDuration(1.2f)
                .fontColor(new Color(1f, 1f, 1f, 0.75f))
                .margin(20)
                .maxTextRelativeWidth(1f)
                .positionY(100)
                .build();

        //local data will send to firebase only if user entered this screen
        if (user.getFirebaseId().equals(localDataHandler.DEFAULT_FIREBASE_ID)
                && user.getUserName().equals(localDataHandler.DEFAULT_USERNAME)) {
            //if the default username was not changed, we don't save data into firebase
            //(profile view before name input)
            toast = toastFactory.create("Go back and save your username", Toast.Length.LONG);

        } else if (user.getFirebaseId().equals(localDataHandler.DEFAULT_FIREBASE_ID)
                && !user.getUserName().equals(localDataHandler.DEFAULT_USERNAME)) {
            //name changed, saving the new user into firebase
            user.setFirebaseId(firebaseConnector.createUserId(user));
            firebaseConnector.saveNewUser(user);
            //updating local userdata with the firebase id for the future update process
            localDataHandler.writeUser(user);
            toast = toastFactory.create("New user sent to firebase", Toast.Length.LONG);

        } else if (!user.getFirebaseId().equals(localDataHandler.DEFAULT_FIREBASE_ID)) {
            //updating the data of the existing user using its firebise id
            firebaseConnector.updateUser(user);
            toast = toastFactory.create("All data updated in firebase", Toast.Length.LONG);
        }

        Label title = new Label("Profile", rainbowFontSkin);
        title.setAlignment(Align.center);
        title.setY((float)((Gdx.graphics.getHeight() - title.getHeight()) * 0.8));
        title.setFontScale(3);
        title.setWidth(Gdx.graphics.getWidth());
        stage.addActor(title);

        Table stageTable = new Table();
        stageTable.setFillParent(false);
        stageTable.setWidth(Gdx.graphics.getWidth());
        Label username = new Label("Username ", gameSkin);
        username.setFontScale(2.0f);
        Label premium = new Label("Premium user ", gameSkin);
        premium.setFontScale(2.0f);
        Label firstGame = new Label("First game played ", gameSkin);
        firstGame.setFontScale(2.0f);
        Label totalGame = new Label("Total game played ", gameSkin);
        totalGame.setFontScale(2.0f);
        Label totalTime = new Label("Total time played ", gameSkin);
        totalTime.setFontScale(2.0f);
        Label highScore = new Label("Highest score ", rainbowFontSkin);
        highScore.setFontScale(2.0f);

        Table left = new Table(gameSkin);
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

        Label usernameValue = new Label(user.getUserName(), gameSkin);
        usernameValue.setFontScale(2.0f);
        Label premiumValue = new Label(String.valueOf(user.isPremium()), gameSkin);
        premiumValue.setFontScale(2.0f);
        Label firstGameValue = new Label(String.valueOf(user.getFirstGameHumanized()), gameSkin);
        firstGameValue.setFontScale(2.0f);
        Label totalGameValue = new Label(String.valueOf(user.getTotalPlayedGames()), gameSkin);
        totalGameValue.setFontScale(2.0f);
        Label totalTimeValue = new Label(String.valueOf(millisToDuration(user.getTotalPlayedTime())), gameSkin);
        totalTimeValue.setFontScale(2.0f);
        Label highScoreValue = new Label(String.valueOf(user.getHighestScore()), rainbowFontSkin);
        highScoreValue.setFontScale(2.0f);

        Table right = new Table(gameSkin);
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

        SplitPane split = new SplitPane(left, right, false, gameSkin);
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
                game.setScreen(new HomeScreen(game, firebaseConnector));
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
        millis -= TimeUnit.SECONDS.toMillis(seconds);

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
        sb.append('.');
        sb.append(millis);
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
        toast.render(delta);
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

