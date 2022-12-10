import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;


/**
 * A class which writes and reads data to and from the movielisting text file
 *
 * @author Chinmay Prasad
 * @version 1.0
 * @since 2022-11-05
 */
public class MovieListingDB {
    /**
     * The character that separates movilistings in the text file
     */
    public static final String SEPARATOR1 = "|";

    /**
     * The character that separates timeslots in the text file
     */
    public static final String SEPARATOR2 = ";";

    /**
     * The character that separates rows of the seat array in the text file
     */
    public static final String SEPARATOR3 = "-";


    /**
     * Reads data from a file and returns its contents in an ArrayList
     *
     * @return an ArrayList of strings containing lines of the read file
     */
    public static ArrayList<String> readFile() {
        File f = new File("data/movielisting.txt");
        ArrayList<String> file_data = new ArrayList<>();
        try (Scanner scanner = new Scanner(f)) {
            while (scanner.hasNextLine()) {
                file_data.add(scanner.nextLine());
            }
        } catch (Exception e) {
            System.out.println("Exception >> " + e.getMessage());
        }
        return file_data;
    }

    /**
     * reads the movielisting file and finds the required movielisting by cineplex and cinema
     *
     * @param cineplexNumber The number of the cineplex for which the movielisting arraylist is required
     * @param cinemaNumber   The number of the cinema for which the movielisting arraylist is required
     * @param data           Data read from the movielisting file
     * @param allMovies      Movie data
     * @return The cinema's movielistings
     */
    public static ArrayList<MovieListing> getCinemaListing(int cineplexNumber, int cinemaNumber, ArrayList<String> data, ArrayList<Movie> allMovies) {
        ArrayList<MovieListing> movieListings = new ArrayList<>();
        String string_listing = "";

        // deciding which line of the code the
        switch (cineplexNumber) {
            case 1 -> string_listing = data.get(cinemaNumber);
            case 2 -> string_listing = data.get(Integer.parseInt(data.get(0)) + 1 + cinemaNumber);
            case 3 -> {
                int num1 = Integer.parseInt(data.get(0));
                int num2 = Integer.parseInt(data.get(num1 + 1));
                string_listing = data.get(num1 + num2 + 2 + cinemaNumber);
            }
        }
        StringTokenizer st = new StringTokenizer(string_listing, SEPARATOR1);

        // separates movieListings
        while (st.hasMoreTokens()) {
            MovieListing ml;
            ArrayList<TimeSlot> timeSlots = new ArrayList<>();
            String movie = st.nextToken();
            StringTokenizer st2 = new StringTokenizer(movie, SEPARATOR2);
            String name = st2.nextToken().trim();
            String movieType = st2.nextToken().trim();
            Movie m = SearchMovie.movieByName(name, allMovies);
            //noinspection ConstantConditions
            ml = new MovieListing(m, movieType, m.getStatus().toString());

            // separates show-times
            while (st2.hasMoreTokens()) {
                String showtime = st2.nextToken();
                StringTokenizer st3 = new StringTokenizer(showtime, SEPARATOR3);
                String date = st3.nextToken().trim();
                String time = st3.nextToken().trim();
                TimeSlot timeslot = new TimeSlot(Integer.parseInt(time), date);

                // separates seat rows
                int i = 0;
                while (st3.hasMoreTokens()) {
                    String row = st3.nextToken().trim();
                    for (int j = 0; j < 16; j++) {
                        char val = row.charAt(j);
                        timeslot.setSeat(i, j, val == 'x');
                    }
                    i++;
                }
                timeSlots.add(timeslot);
                ml.setTimeSlots(timeSlots);
            }
            movieListings.add(ml);
        }
        return movieListings;
    }

    /**
     * Converts a  cinema's movielistings to a string equivalent
     *
     * @param movieListings A cinema's movielistings
     * @return The string equivalent of the cinema's movielistings
     */
    public static String movieListingToString(ArrayList<MovieListing> movieListings) {
        StringBuilder output = new StringBuilder();
        output.append(" ");
        for (MovieListing ml : movieListings) {
            if (output.charAt(output.length() - 1) == ';') {
                output.deleteCharAt(output.length() - 1);
                output.append("|");
            }
            String movieName = ml.getMovie().getTitle();
            output.append(movieName).append(";");
            output.append(ml.getMovieTypeString()).append(";");
            for (TimeSlot timeSlot : ml.getTimeSlots()) {
                String date = timeSlot.getDate();
                int time = timeSlot.getTime();
                output.append(date).append("-").append(time);
                for (Seat[] row : timeSlot.getSeats()) {
                    StringBuilder string_row = new StringBuilder();
                    output.append("-");
                    for (Seat seat : row) {
                        if (seat.isAssigned()) {
                            string_row.append("x");
                        } else {
                            string_row.append("o");
                        }
                    }
                    output.append(string_row);
                }
                output.append(";");
            }
            output.append("|");
        }
        output.deleteCharAt(output.length() - 1);
        output.deleteCharAt(output.length() - 1);
        output.deleteCharAt(0);

        return output.toString();
    }

    /**
     * writes data from an arraylist into the movielisting file after swapping out the necessary line with the new line
     *
     * @param data           Data to write into the file before the change
     * @param newData        A String which gets swapped with the old String data
     * @param cineplexNumber The number of the cineplex which contains the required cinema
     * @param cinemaNumber   The number of the cinema which contains the required movielisting to edit
     */
    public static void changeLine(ArrayList<String> data, String newData, int cineplexNumber, int cinemaNumber) {
        int index = 0;
        // find index of data in the arraylist to edit
        switch (cineplexNumber) {
            case 1 -> index = cinemaNumber;
            case 2 -> index = Integer.parseInt(data.get(0)) + 1 + cinemaNumber;
            case 3 -> {
                int num1 = Integer.parseInt(data.get(0));
                int num2 = Integer.parseInt(data.get(num1 + 1));
                index = num1 + num2 + 2 + cinemaNumber;
            }
        }
        data.set(index, newData);
        writeFile(data);
    }

    /**
     * Writes an arraylist if String data into the movielisting text file
     *
     * @param data The data to write into the file
     */
    public static void writeFile(ArrayList<String> data) {

        String fileName = "data/movielisting.txt";
        File f = new File(fileName);
        try (FileWriter out = new FileWriter(f, false)) {
            for (String line : data) {
                out.write(line + "\n");
            }
        } catch (Exception e) {
            System.out.println("Exception >> " + e.getMessage());

        }
    }
}
