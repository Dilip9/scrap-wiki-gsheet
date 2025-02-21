package com.wikipedia.wikipedia_google_api.model;

public class ChampionshipData {

    private Integer year;
    private String winner;
    private String score;
    private String runnerUp;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getRunnerUp() {
        return runnerUp;
    }

    public void setRunnerUp(String runnerUp) {
        this.runnerUp = runnerUp;
    }

    @Override
    public String toString() {
        return "{year='" + year + "', winner='" + winner + "', score='" + score + "', runnerUp='" + runnerUp + "'}";
    }

    public ChampionshipData() {
    }

    public ChampionshipData(Integer year, String winner, String score, String runnerUp) {
        this.year = year;
        this.winner = winner;
        this.score = score;
        this.runnerUp = runnerUp;
    }
}
