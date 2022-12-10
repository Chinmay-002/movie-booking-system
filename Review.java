/**
 * Represents a reviewer's rating and review stored in the review database (in a reviews.txt file).
 * There can be multiple reviews stored in the review database.
 *
 * @author Karishein Chandran
 * @version 1.0
 * @since 2022-11-05
 */
public class Review {

    /**
     * The name of this reviewer.
     */
    private final String reviewerName;

    /**
     * The title of the movie being reviewed by this reviewer.
     */
    private final String movieTitle;

    /**
     * The rating given for the movie by this reviewer.
     */
    private final double rating;

    /**
     * The review written for the movie by this reviewer.
     */
    private final String reviewDescription;

    /**
     * Creates a new Review with the given reviewer name and movie title.
     *
     * @param reviewerName      This Review's reviewer name.
     * @param movieTitle        This Review's movie title.
     * @param rating            This Review's rating for the movie.
     * @param reviewDescription This Review's review for the movie.
     */
    public Review(String reviewerName, String movieTitle, double rating, String reviewDescription) {
        this.reviewerName = reviewerName;
        this.movieTitle = movieTitle;
        this.rating = rating;
        this.reviewDescription = reviewDescription;
    }

    /**
     * Gets the reviewer name of this Review.
     *
     * @return this Review's reviewer name.
     */
    public String getReviewerName() {
        return reviewerName;
    }

    /**
     * Gets the movie title of this Review.
     *
     * @return this Review's movie title.
     */
    public String getMovieTitle() {
        return movieTitle;
    }

    /**
     * Gets the rating of this Review.
     *
     * @return this Review's rating.
     */
    public double getRating() {
        return rating;
    }

    /**
     * Gets the written review of this Review.
     *
     * @return this Review's written review.
     */
    public String getReviewDescription() {
        return reviewDescription;
    }
}