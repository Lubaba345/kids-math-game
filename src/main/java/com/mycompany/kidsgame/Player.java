package com.mycompany.kidsgame;

/**
 * Represents one player in the graphical math game.
 */
public class Player {

    private final String name;
    private int score;
    private String summary;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.summary = "";
    }

    public String getName() {
        return name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }
}
