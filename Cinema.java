import java.util.ArrayList;

/**
 * A class that handles data for each cinema
 *
 * @author Samuel Ng
 * @version 1.0
 * @since 2022-11-05
 */
public class Cinema {
    /**
     * ID of cinema
     */
    private final String cinemaId;
    /**
     * Arraylist of movielisting, details of movies that are scheduled to play in this cinema
     */
    ArrayList<MovieListing> movieListings;

    /**
     * Constructor, stores given id and cineplexNumber and populates movielisting array with movies from movie file
     *
     * @param id             id for cinema
     * @param cineplexNumber id for cineplex that cinema is in
     */
    public Cinema(int id, int cineplexNumber) {
        this.cinemaId = "00" + id;

        this.movieListings = MovieListingDB.getCinemaListing(cineplexNumber, id, MovieListingDB.readFile(), MovieDB.readMovies("data/movies.txt"));
    }

    /**
     * Returns CinemaId
     *
     * @return ID of cinema
     */
    public String getCinemaId() {
        return this.cinemaId;
    }

    /**
     * Returns movielisting, that corresponds to moviename
     *
     * @param movieName name of movie to find movielisting for
     * @return movielisting
     */
    public MovieListing getListing(String movieName) {
        for (MovieListing movielisting : this.getMovieListings()) {
            String name = movielisting.getMovie().getTitle();
            if (name.equals(movieName)) {
                return movielisting;
            }
        }
        return null;
    }

    /**
     * Returns arraylist of movielistings that belong to this cinema
     *
     * @return arraylist of movielistings
     */
    public ArrayList<MovieListing> getMovieListings() {
        return movieListings;
    }

    /**
     * Sets this Cinema's movielisting array
     *
     * @param movieListings The movielisting array to be set
     */
    public void setMovieListings(ArrayList<MovieListing> movieListings) {
        this.movieListings = movieListings;
    }


}


