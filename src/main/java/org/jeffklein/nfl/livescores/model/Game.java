package org.jeffklein.nfl.livescores.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Game entity for our sample project.
 */
@Entity
@Table(name = "GAME")
public class Game {

    @Column(name = "DAY")
    private String day;

    @Column(name = "TIME")
    private String time;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "TIME_REMAINING")
    private String timeRemaining;

    @Column(name = "AWAY_TEAM")
    private String awayTeam;

    @Column(name = "AWAY_SCORE")
    private Integer awayScore;

    @Column(name = "HOME_TEAM")
    private String homeTeam;

    @Column(name = "HOME_SCORE")
    private Integer homeScore;

    @Column(name = "TEAM_IN_POSSESSION")
    private String teamInPossession;

    @Id
    @Column(name = "GAME_ID")
    private Integer gameId;

    @Column(name = "WEEK")
    private String week;

    @Column(name = "YEAR")
    private Integer year;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(String timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Integer getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(Integer awayScore) {
        this.awayScore = awayScore;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Integer getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(Integer homeScore) {
        this.homeScore = homeScore;
    }

    public String getTeamInPossession() {
        return teamInPossession;
    }

    public void setTeamInPossession(String teamInPossession) {
        this.teamInPossession = teamInPossession;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Game{" +
                "day='" + day + '\'' +
                ", time='" + time + '\'' +
                ", status='" + status + '\'' +
                ", awayTeam='" + awayTeam + '\'' +
                ", awayScore=" + awayScore +
                ", homeTeam='" + homeTeam + '\'' +
                ", homeScore=" + homeScore +
                ", gameId=" + gameId +
                ", week='" + week + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
