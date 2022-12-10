import java.util.ArrayList;

/**
 * Class representing booking status of a movie
 *
 * @author Khoo Yong Quan
 * @version 1.0
 * @since 2022-11-05
 */
public class MovieListing {
    /**
     * Movie object
     */
    private final Movie movieName;
    /**
     * Status of movie
     */
    private final Movie.ShowingStatus movieStatus;
    /**
     * Type of movie
     */
    private final Movie.MovieType movieType;
    /**
     * Arraylist of timeslots objects
     */
    private ArrayList<TimeSlot> timeSlots;

    //private double[] TicketPrice;

    /**
     * Constructor, sets movie, type and status
     *
     * @param movie       Movie
     * @param movieType   Type of movie
     * @param movieStatus Status of movie
     */
    public MovieListing(Movie movie, String movieType, String movieStatus) {
        this.movieName = movie;
        this.timeSlots = new ArrayList<>();
        this.movieType = Movie.MovieType.valueOf(movieType);
        this.movieStatus = Movie.ShowingStatus.valueOf(movieStatus);
    }

    /**
     * Returns movie object
     *
     * @return movie Object
     */
    public Movie getMovie() {
        return movieName;
    }

    /**
     * Return type of movie
     *
     * @return Type of movie
     */
    public int getMovieType() {
        Movie.MovieType typeMovie = this.movieType;
        if (typeMovie == Movie.MovieType.IMAX_3D) {
            return 1;
        } else if (typeMovie == Movie.MovieType.BLOCKBUSTER) {
            return 2;
        } else if ((typeMovie == Movie.MovieType.IMAX_2D)) {
            return 0;
        } else {
            return 0;
        }
    }


    /**
     * Return timeslot arraylist
     *
     * @return Timeslot arraylist
     */
    public ArrayList<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    /**
     * Changes entire timeslots array
     *
     * @param timeSlots New timeslots array
     */
    public void setTimeSlots(ArrayList<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }

    /**
     * returns timeslot which is at a specified time
     *
     * @param time Time of timeslot
     * @return Timeslot
     */
    public TimeSlot getTime(int time) {
        for (TimeSlot timeSlot : timeSlots) {
            if (timeSlot.getTime() == time) {
                return timeSlot;
            }
        }
        return null;
    }

    /**
     * Returns type of movie as a string
     *
     * @return Type of movie
     */
    public String getMovieTypeString() {
        return this.movieType.toString();
    }

    /**
     * Adds a new timeslot
     *
     * @param time Time of timeslot
     * @param date Date of timeslot
     */
    public void addShowtime(int time, String date) {
        TimeSlot t = new TimeSlot(time, date);
        this.timeSlots.add(t);
    }

    /**
     * Returns the Movie's type
     *
     * @return this movie's MovieType
     */
    public Movie.MovieType getMovieTypeEnum() {
        return this.movieType;
    }

}
