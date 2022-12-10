import java.util.ArrayList;
import java.util.Objects;

/**
 * Class to print movies in order
 *
 * @author Shikhar
 * @version 1.0
 * @since 2022-11-05
 */
public class SearchMovie {
    /**
     * Find movie object in moviearray
     *
     * @param movieName Name of movie
     * @param movies    Arraylist of all movies
     * @return movie Object
     */
    public static Movie movieByName(String movieName, ArrayList<Movie> movies) {
        for (Movie movie : movies) {
            //movieListing object has movie object attribute
            if (Objects.equals(movie.getTitle(), movieName)) {
                return movie;
            }
        }
        return null;
    }

    /**
     * Gets movies in a specific cinema
     *
     * @param cinema cinema to check
     * @return movies that are played in the cinema
     */
    public static ArrayList<Movie> movieByCinema(Cinema cinema) {
        ArrayList<MovieListing> all_listings = cinema.getMovieListings();
        ArrayList<Movie> movies = new ArrayList<>();
        for (MovieListing listing : all_listings) {
            Movie movie = listing.getMovie();
//            System.out.printf("Movie %d : %s\n",i+1,movie.getTitle());
            movies.add(movie);
        }
        return movies;
    }

    /**
     * Sorts movies by rating
     *
     * @param pre_sorted_movies Arraylist of all movies
     * @return Arraylist of movies that have been sorted
     */
    public static ArrayList<Movie> movieByRating(ArrayList<Movie> pre_sorted_movies) {
        Movie temp;
        boolean finished = false;
        while (!finished) {
            for (int i = 0; i < pre_sorted_movies.size(); i++) {
                if (pre_sorted_movies.get(i).getOverallReviewerRating().equals("NA")) {
                    pre_sorted_movies.remove(i);
                    break;
                }
                if (i == pre_sorted_movies.size() - 1) {
                    finished = true;
                }
            }
        }
        for (int i = 1; i < pre_sorted_movies.size(); i++) {

            for (int j = i; j > 0; j--) {
                String curr_rating = pre_sorted_movies.get(j).getOverallReviewerRating();
                double currRating = Double.parseDouble(curr_rating);
                String prev_rating = pre_sorted_movies.get(j - 1).getOverallReviewerRating();
                double prevRating = Double.parseDouble(prev_rating);
                if (currRating > prevRating) {
                    temp = pre_sorted_movies.get(j);
                    pre_sorted_movies.set(j, pre_sorted_movies.get(j - 1));
                    pre_sorted_movies.set(j - 1, temp);
                } else {
                    break;
                }
            }

        }
        return pre_sorted_movies;
    }

    /**
     * Sorts movies by tickets
     *
     * @param pre_sorted_movies Arraylist of all movies
     * @return Arraylist of movies that have been sorted
     */
    public static ArrayList<Movie> movieByTickets(ArrayList<Movie> pre_sorted_movies) {
        Movie temp;
        for (int i = 1; i < pre_sorted_movies.size(); i++) {
            for (int j = i; j > 0; j--) {
                int curr_tickets = pre_sorted_movies.get(j).getNoOfTickets();
                int prev_tickets = pre_sorted_movies.get(j - 1).getNoOfTickets();

                if (curr_tickets > prev_tickets) {
                    temp = pre_sorted_movies.get(j);
                    pre_sorted_movies.set(j, pre_sorted_movies.get(j - 1));
                    pre_sorted_movies.set(j - 1, temp);
                } else {
                    break;
                }
            }

        }
        return pre_sorted_movies;
    }

    /**
     * Prints movie details
     *
     * @param movie Movie object
     */
    public static void printMovieDetails(Movie movie) {
        if (movie == null) {
            System.out.println("Sorry, there exists no such Movie");
            return;
        }
        int count = 0;
        System.out.println("Movie Title: " + movie.getTitle());
        System.out.println("Showing Status: " + movie.getStatus());
        System.out.println("Synopsis: " + movie.getSynopsis());
        System.out.println("Director: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overall Reviewer Rating: " + movie.getOverallReviewerRating());
        System.out.println("Past Reviews: ");
        for (String element : movie.getPastReviews()) {
            if (element.equalsIgnoreCase("NA")) {
                System.out.print(element);
                break;
            }
            if (count >= 3) {
                System.out.println();
                count = 0;
            }
            System.out.print(element + "|");
            count++;
        }
        System.out.println();
        System.out.println("Number of tickets sold: " + movie.getNoOfTickets());
        System.out.println();
    }

}




