package com.victoriaserect.fappybird;

import com.badlogic.gdx.utils.JsonValue;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserData {
    private String userName;
    private boolean isPremium;
    private long firstGame;
    private String firstGameHumanized;
    private long lastUpdated;
    private String lastUpdatedHumanized;
    private long totalPlayedGames;
    private long totalPlayedTime;
    private long highestScore;
    private int apiVersion;

    public UserData(String userName, int apiVersion) {
        this.userName = userName;
        this.isPremium = false;
        this.firstGame = this.lastUpdated = System.currentTimeMillis();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy. MMM dd. HH:mm");
        this.firstGameHumanized = this.lastUpdatedHumanized = sdf.format(new Date(this.firstGame));
        this.totalPlayedGames = this.totalPlayedTime = this.highestScore = 0;
        this.apiVersion = apiVersion;
    }

    public UserData(JsonValue fromJson) {
        this.userName = fromJson.getString("userName");
        this.isPremium = fromJson.getBoolean("isPremium");
        this.firstGame = fromJson.getLong("firstGame");
        this.firstGameHumanized = fromJson.getString("firstGameHumanized");
        this.lastUpdated = fromJson.getLong("lastUpdated");
        this.lastUpdatedHumanized = fromJson.getString("lastUpdatedHumanized");
        this.totalPlayedGames = fromJson.getLong("totalPlayedGames");
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
                ", totalPlayedGames=" + totalPlayedGames + '\n' +
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
            totalPlayedGames:0,totalPlayedTime:0,userName:anonymus}*/

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }

    public long getFirstGame() {
        return firstGame;
    }

    public void setFirstGame(long firstGame) {
        this.firstGame = firstGame;
    }

    public String getFirstGameHumanized() {
        return firstGameHumanized;
    }

    public void setFirstGameHumanized(String firstGameHumanized) {
        this.firstGameHumanized = firstGameHumanized;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getLastUpdatedHumanized() {
        return lastUpdatedHumanized;
    }

    public void setLastUpdatedHumanized(String lastUpdatedHumanized) {
        this.lastUpdatedHumanized = lastUpdatedHumanized;
    }

    public long getTotalPlayedGames() {
        return totalPlayedGames;
    }

    public void setTotalPlayedGames(long totalPlayedGames) {
        this.totalPlayedGames = totalPlayedGames;
    }

    public long getTotalPlayedTime() {
        return totalPlayedTime;
    }

    public void setTotalPlayedTime(long totalPlayedTime) {
        this.totalPlayedTime = totalPlayedTime;
    }

    public long getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(long highestScore) {
        this.highestScore = highestScore;
    }

    public int getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(int apiVersion) {
        this.apiVersion = apiVersion;
    }
}
