package com.aniverse.model;

/**
 *
 * @author Pranav
 */
public class AnimeModel {

    private String title;
    private String genre;
    private String type;
    private int episodes;
    private String status;
    private String studio;
    private String ratings;
    private int score;

    public AnimeModel() {
    }

    public AnimeModel(String title, String genre, String type, int episodes, String status, String studio, String ratings, int score) {
        this.title = title;
        this.genre = genre;
        this.type = type;
        this.episodes = episodes;
        this.status = status;
        this.studio = studio;
        this.ratings = ratings;
        this.score = score;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getEpisodes() {
        return episodes;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getRatings() { // Getter for ratings (String)
        return ratings;
    }

    public void setRatings(String ratings) { // Setter for ratings
        this.ratings = ratings;
    }

    public int getScore() { // Getter for score
        return score;
    }

    public void setScore(int score) { // Setter for score
        this.score = score;
    }
}
