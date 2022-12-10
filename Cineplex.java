import java.util.ArrayList;

/**
 * Class representing a Cineplex
 *
 * @author Khoo Yong Quan
 * @version 1.0
 * @since 2022-11-05
 */
public class Cineplex {
    private final String cineplexName;
    private final Cinema[] cinemas;

    /**
     * Constructor, sets cinemaname and id, initialise array of cinemas
     *
     * @param cineplexName   name of cineplex
     * @param numCinemas     number of cinemas in the cineplex
     * @param cineplexNumber ID of cineplex
     */
    public Cineplex(String cineplexName, int numCinemas, int cineplexNumber) {
        this.cineplexName = cineplexName;
        this.cinemas = new Cinema[numCinemas];
        for (int i = 0; i < numCinemas; i++) {
            this.cinemas[i] = new Cinema(i + 1, cineplexNumber);
        }
    }

    /**
     * Returns array of Cinemas
     *
     * @return array of cinemas
     */
    public Cinema[] getCinemas() {
        return this.cinemas;
    }

    /**
     * Find all cinemas that are showing a specific movie
     *
     * @param movieName name of movie to search for
     * @return Cinemas that are playing the movie
     */
    public ArrayList<Cinema> cinemasByMovie(String movieName) {
        ArrayList<Cinema> movieCinemas = new ArrayList<>();
        for (Cinema cinema : cinemas) {
            ArrayList<MovieListing> cinemaListing = cinema.movieListings;
            for (MovieListing movieListing : cinemaListing) {
                String name = movieListing.getMovie().getTitle();
                if (name.equals(movieName)) {
                    movieCinemas.add(cinema);
                    break;
                }
            }
        }
        return movieCinemas;
    }

    /**
     * Return name of cineplex
     *
     * @return name of cineplex
     */
    public String getCineplexName() {
        return cineplexName;
    }

    /**
     * Sorts movie by cineplex
     *
     * @return sorted arraylist of cineplex
     */
    public ArrayList<Movie> movieByCineplex() {
        ArrayList<Movie> cineplexMovies = new ArrayList<>();
        ArrayList<String> cineplexMovieNames = new ArrayList<>();
        for (Cinema c : this.cinemas) {
            ArrayList<Movie> cinemaMovies = SearchMovie.movieByCinema(c);
            for (Movie movie : cinemaMovies) {
                if (!cineplexMovieNames.contains(movie.getTitle())) {
                    cineplexMovies.add(movie);
                    cineplexMovieNames.add(movie.getTitle());
                }
            }
        }
        return cineplexMovies;
    }

    /**
     * Prints out all movies and their details that are in this cineplex
     */
    public void listMovies() {
        for (Movie movie : this.movieByCineplex()) {
            System.out.println("Movie Title: " + movie.getTitle());
            System.out.println("Showing Status: " + movie.getStatus());
            System.out.println("Synopsis: " + movie.getSynopsis());
            System.out.println();
        }
    }
}
