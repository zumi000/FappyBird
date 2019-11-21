package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;

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
            System.out.println("First start, new_anonymus_user created");
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
}
