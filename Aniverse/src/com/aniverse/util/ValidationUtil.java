package com.aniverse.util;

import java.util.regex.Pattern;

public class ValidationUtil {

    // Regular expression patterns
    private static final Pattern TITLE_PATTERN = Pattern.compile("^[a-zA-Z0-9\\s:.,'!?-]+$");
    private static final Pattern GENRE_PATTERN = Pattern.compile(
            "^(Action|Romance|Comedy|Fantasy|Horror|Sci-Fi|Drama|Adventure|Slice of Life|Mystery|Supernatural|Thriller)(,\\s*(Action|Romance|Comedy|Fantasy|Horror|Sci-Fi|Drama|Adventure|Slice of Life|Mystery|Supernatural|Thriller))*$",
            Pattern.CASE_INSENSITIVE
    );
    private static final Pattern TYPE_PATTERN = Pattern.compile("^(TV|Movie|OVA|ONA|Special)$", Pattern.CASE_INSENSITIVE);
    private static final Pattern STATUS_PATTERN = Pattern.compile("^(Ongoing|Completed|Upcoming)$", Pattern.CASE_INSENSITIVE);
    private static final Pattern RATING_PATTERN = Pattern.compile("^(G|PG|PG-13|R)$", Pattern.CASE_INSENSITIVE);

    /**
     * Validates if a string is null or empty.
     *
     * @param value the string to validate
     * @return true if the string is null or empty, otherwise false
     */
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Validates if the title contains only valid characters.
     *
     * @param title the title to validate
     * @return true if valid, otherwise false
     */
    public static boolean isValidTitle(String title) {
        return !isNullOrEmpty(title) && TITLE_PATTERN.matcher(title).matches();
    }

    /**
     * Validates if the genre is one of the allowed options.
     *
     * @param genre the genre to validate
     * @return true if valid, otherwise false
     */
    public static boolean isValidGenre(String genre) {
        return !isNullOrEmpty(genre) && GENRE_PATTERN.matcher(genre).matches();
    }

    /**
     * Validates if the type is one of the allowed options.
     *
     * @param type the type to validate
     * @return true if valid, otherwise false
     */
    public static boolean isValidType(String type) {
        return !isNullOrEmpty(type) && TYPE_PATTERN.matcher(type).matches();
    }

    /**
     * Validates if the status is one of the allowed options.
     *
     * @param status the status to validate
     * @return true if valid, otherwise false
     */
    public static boolean isValidStatus(String status) {
        return !isNullOrEmpty(status) && STATUS_PATTERN.matcher(status).matches();
    }

    /**
     * Validates if the rating is one of the allowed options.
     *
     * @param rating the rating to validate
     * @return true if valid, otherwise false
     */
    public static boolean isValidRating(String rating) {
        return !isNullOrEmpty(rating) && RATING_PATTERN.matcher(rating).matches();
    }

    /**
     * Validates if the score is between 0 and 10 (inclusive).
     *
     * @param score the score to validate
     * @return true if valid, otherwise false
     */
    public static boolean isValidScore(int score) {
        return score >= 0 && score <= 10;
    }

    /**
     * Utility to parse and validate a score input.
     *
     * @param scoreText the text representing the score
     * @return true if valid, otherwise false
     */
    public static boolean validateScoreInput(String scoreText) {
        try {
            int score = Integer.parseInt(scoreText.trim());
            return isValidScore(score);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validates if the studio name is not null or empty.
     *
     * @param studio the studio name to validate
     * @return true if valid, otherwise false
     */
    public static boolean isValidStudio(String studio) {
        return !isNullOrEmpty(studio);
    }

    /**
     * Validates if the ratings are between 0.0 and 10.0 (inclusive).
     *
     * @param episodes the number of episodes to validate
     * @return true if valid, otherwise false
     */
    public static boolean isValidEpisodes(int episodes) {
        return episodes > 0;
    }

    /**
     * Utility to parse and validate episodes input.
     *
     * @param episodesText the text representing the number of episodes
     * @return true if valid, otherwise false
     */
    public static boolean validateEpisodesInput(String episodesText) {
        try {
            int episodes = Integer.parseInt(episodesText.trim());
            return isValidEpisodes(episodes);
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
