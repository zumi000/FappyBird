package com.victoriaserect.fappybird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class LocalDataHandler {
    Json json = new Json();
    FileHandle scoresFile, userDataFile;
    final String DEFAULT_USERNAME = "new_anonymus_user";

    public LocalDataHandler() {
        if (Gdx.files.local("data/userdata.txt").exists()) {
            userDataFile = Gdx.files.local("data/userdata.txt");
        } else {
            userDataFile = Gdx.files.local("data/userdata.txt");
            writeUser(new UserData(DEFAULT_USERNAME, Gdx.app.getVersion()));
        }
        //            scoresFile = Gdx.files.local("data/scores.txt");
    }

    public LocalDataHandler(String s) {
        userDataFile = Gdx.files.local("data/userdata.txt");
        writeUser(new UserData(DEFAULT_USERNAME, Gdx.app.getVersion()));
    }

    public String randomize() {
        Random rand = new Random();
        return String.valueOf(rand.nextGaussian());
    }

    public void writeUser(UserData user) {
        userDataFile.writeString(json.prettyPrint(user), false);
    }

    public UserData getUserData() {
          return new UserData(new JsonReader().parse(userDataFile.readString()));
    }

    public void addOneGame(long gameStarted, int score) {
        long now = System.currentTimeMillis();
        UserData user = getUserData();
        user.setTotalPlayedGames(user.getTotalPlayedGames() + 1);
        user.setTotalPlayedTime(user.getTotalPlayedTime() + now - gameStarted);
        if (user.getHighestScore() < score) {
            user.setHighestScore((long)score);
        }
        user.setLastUpdated(now);
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy. MMM dd. HH:mm");
        user.setLastUpdatedHumanized(sdf.format(new Date(now)));

        writeUser(user);
    }

}
