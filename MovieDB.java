import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Represents a movie database which can be used to write and read from a text file (movies.txt).
 *
 * @author Karishein Chandran
 * @version 1.0
 * @since 2022-11-10
 */
public class MovieDB {

    /**
     * A string variable to represent the separator type between different variables in the text file.
     */
    public static final String SEPARATOR = "|";

    /**
     * Creates an array list of movie objects after reading all the movies from the movies.txt file.
     *
     * @param filename The file path of the movies.txt file which contains all the movies.
     * @return An array list containing multiple movie objects.
     * @see #read(String)
     */
    public static ArrayList<Movie> readMovies(String filename) {
        ArrayList<String> stringArray = read(filename); // read String from text file
        ArrayList<Movie> movRead = new ArrayList<>(); // to store movie objects

        for (String s : stringArray) {
            Movie.ShowingStatus status;
            ArrayList<String> cast = new ArrayList<>();
            ArrayList<String> pastReviews = new ArrayList<>();

            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(s, SEPARATOR);    // pass in the string to the string tokenizer using delimiter ","

            // extracts each field to its respective variable
            String title = star.nextToken().trim();
            status = Movie.ShowingStatus.valueOf(star.nextToken().trim());
            String endOfShowDate = star.nextToken().trim();
            String synopsis = star.nextToken().trim();
            String director = star.nextToken().trim();
            cast.add(star.nextToken().trim());
            String genre = star.nextToken().trim();
            String language = star.nextToken().trim();
            String runtime = star.nextToken().trim();
            String movieRating = star.nextToken().trim();
            String release = star.nextToken().trim();
            String overallReviewerRating = star.nextToken().trim();
            pastReviews.add(star.nextToken().trim());
            int tickets = Integer.parseInt(star.nextToken().trim());

            // create movie object from file data
            Movie movie = new Movie(title, status, synopsis, director, cast, genre, language, runtime,
                    movieRating, release, overallReviewerRating, pastReviews, tickets);

            movie.setEndOfShowingDate(endOfShowDate);
            // add to movie list
            movRead.add(movie);
        }
        return movRead;
    }

    /**
     * Converts a new movie object into a string and stores it into list.
     * This list is written to the existing movies.txt file.
     *
     * @param filename The file path of the movies.txt file which contains all the movies.
     * @param al       A list containing the new movie object.
     * @see #write(String, ArrayList)
     */
    public static void saveMovies(String filename, ArrayList<Movie> al) {
        ArrayList<String> movWrite = new ArrayList<>();// to store movies data

        for (Movie movie : al) {
            StringBuilder st = new StringBuilder();
            st.append(movie.getTitle().trim());
            st.append(SEPARATOR);
            st.append(movie.getStatus());
            st.append(SEPARATOR);
            st.append(movie.getEndOfShowingDate());
            st.append(SEPARATOR);
            st.append(movie.getSynopsis().trim());
            st.append(SEPARATOR);
            st.append(movie.getDirector().trim());
            st.append(SEPARATOR);
            String castDisplay = movie.getCast().toString().replace("[", "").replace("]", "").trim();
            st.append(castDisplay);
            st.append(SEPARATOR);
            st.append(movie.getGenre().trim());
            st.append(SEPARATOR);
            st.append(movie.getLanguage().trim());
            st.append(SEPARATOR);
            st.append(movie.getRunTime().trim());
            st.append(SEPARATOR);
            st.append(movie.getMovieRating().trim());
            st.append(SEPARATOR);
            st.append(movie.getRelease().trim());
            st.append(SEPARATOR);
            st.append(movie.getOverallReviewerRating());
            st.append(SEPARATOR);
            String pastReviewsDisplay = movie.getPastReviews().toString().replace("[", "").replace("]", ""); // to remove square brackets in text file
            st.append(pastReviewsDisplay);
            st.append(SEPARATOR);
            st.append(movie.getNoOfTickets());

            movWrite.add(st.toString());
        }
        write(filename, movWrite);
    }

    /**
     * Writes the movie data obtained from saveMovies() into the movies.txt file.
     *
     * @param fileName The file path of the movies.txt file which contains all the movies.
     * @param data     A list containing the movie data in string format.
     * @see PrintWriter
     * @see FileWriter
     */
    public static void write(String fileName, ArrayList<String> data) {
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName))) {
            for (String line : data) {
                out.println(line);
            }
        } catch (Exception e) {
            System.out.println("Exception >> " + e);
        }
    }

    /**
     * Reads all the movies from the movies.txt file.
     *
     * @param fileName The file path of the movies.txt file which contains all the movies.
     * @return An array list containing the movies in string format.
     * @see Scanner
     * @see FileInputStream
     */
    public static ArrayList<String> read(String fileName) {
        ArrayList<String> data = new ArrayList<>();
        try (Scanner scanner = new Scanner(new FileInputStream(fileName))) {
            while (scanner.hasNextLine()) {
                data.add(scanner.nextLine());
            }
        } catch (Exception e) {
            System.out.println("Exception >> " + e);
        }
        return data;
    }
}
