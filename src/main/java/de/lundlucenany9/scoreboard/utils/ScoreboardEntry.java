package de.lundlucenany9.scoreboard.utils;

import de.lundlucenany9.scoreboard.Scoreboard;
import org.bukkit.entity.Player;

import java.io.Serializable;

public class ScoreboardEntry implements Serializable {
    private static final long serialVersionUID = 1L;
    public ScoreboardEntry(String rang){
        this.rang = rang;
    }

    public ScoreboardEntry(String rang, float money, int deaths, String job) {

        this.rang = rang;
        this.money = money;
        this.deaths = deaths;
        this.job = job;
    }


    public String getRang() {
        return rang;
    }

    public void setRang(String rang) {
        this.rang = rang;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
    public void changeMoney(float money) { this.money += money; }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
    public String toString() {return "Rang: " + rang + ", Money: " + money + ", Tode: " + deaths + ", Job: " + job;}

    private String rang;
    private float money;
    private int deaths;
    private String job;
}
