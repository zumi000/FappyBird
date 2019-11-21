package com.mygdx.game;

import com.badlogic.gdx.utils.JsonValue;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserData {
    String userName;
    boolean isPremium;
    long firstGame;
    String firstGameHumanized;
    long lastUpdated;
    String lastUpdatedHumanized;
    long playedGames;
    long totalPlayedTime;
    long highestScore;
    int apiVersion;

    public UserData(String userName, int apiVersion) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy. MMM dd. HH:mm");
        this.userName = userName;
        this.isPremium = false;
        this.firstGame = this.lastUpdated = System.currentTimeMillis();
        this.firstGameHumanized = this.lastUpdatedHumanized = sdf.format(new Date(this.firstGame));
        this.playedGames = this.totalPlayedTime = this.highestScore = 0;
        this.apiVersion = apiVersion;
    }

    public UserData(JsonValue fromJson) {
        this.userName = fromJson.getString("userName");
        this.isPremium = fromJson.getBoolean("isPremium");
        this.firstGame = fromJson.getLong("firstGame");
        this.firstGameHumanized = fromJson.getString("firstGameHumanized");
        this.lastUpdated = fromJson.getLong("lastUpdated");
        this.lastUpdatedHumanized = fromJson.getString("lastUpdatedHumanized");
        this.playedGames = fromJson.getLong("playedGames");
        this.totalPlayedTime = fromJson.getLong("totalPlayedTime");
        this.highestScore = fromJson.getLong("highestScore");
        this.apiVersion = fromJson.getInt("apiVersion");
    }

    @Override
    public String toString() {
        return "UserData{" + '\n' +
                "userName='" + userName + '\'' + '\n' +
                ", isPremium=" + isPremium + '\n' +
                ", firstGame=" + firstGame + '\n' +
                ", firstGameHumanized='" + firstGameHumanized + '\'' + '\n' +
                ", lastUpdated=" + lastUpdated + '\n' +
                ", lastUpdatedHumanized='" + lastUpdatedHumanized + '\'' + '\n' +
                ", playedGames=" + playedGames + '\n' +
                ", totalPlayedTime=" + totalPlayedTime + '\n' +
                ", highestScore=" + highestScore + '\n' +
                ", apiVersion=" + apiVersion + '\n' +
                '}';
    }

/*
    Default user:
    I/System.out:
    {apiVersion:0,firstGame:1574337080718,firstGameHumanized:"nov. 21,2019 12:51",
            highestScore:0,isPremium:false,lastUpdated:1574337080718,lastUpdatedHumanized:"nov. 21,2019 12:51",
            playedGames:0,totalPlayedTime:0,userName:anonymus}*/

}
