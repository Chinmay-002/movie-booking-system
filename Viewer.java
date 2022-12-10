import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a viewer who watches and can book tickets for movies through the booking system
 *
 * @author Chinmay Prasad
 * @version 1.0
 * @since 2022-11-05
 */
public class Viewer {

    /**
     * An integer value which is the value of the viewerID of the next user to be created
     */
    private static int idCounter = ViewerDB.getLastViewerID() + 1;

    /**
     * The emailID of the viewer
     */
    private final String email;

    /**
     * The first middle and last name of the viewer
     */
    private final String fullName;

    /**
     * The viewer's mobile number
     */
    private final String mobileNumber;

    /**
     * The viewer's viewerID
     */
    private final String viewerID;

    /**
     * The viewer's booking history
     */
    private ArrayList<String> bookingHistory = new ArrayList<>();


    /**
     * Creates a new viewer
     *
     * @param email        emailID of the viewer
     * @param fullName     Name of the viewer
     * @param mobileNumber Mobile number of the viewer
     */
    public Viewer(String email, String fullName, String mobileNumber) {
        this.email = email;
        this.fullName = fullName;
        this.mobileNumber = mobileNumber;
        this.viewerID = Integer.toString(idCounter);
        idCounter++;
        this.bookingHistory.add("NA");
    }

    /**
     * Creates an instance of an already existing viewer from the text file with a given viewerID
     *
     * @param email        emailID of the viewer
     * @param fullName     Name of the viewer
     * @param mobileNumber Mobile number of the viewer
     * @param viewerID     viewerID of the viewer
     */
    public Viewer(String email, String fullName, String mobileNumber, String viewerID) {
        this.email = email;
        this.fullName = fullName;
        this.mobileNumber = mobileNumber;
        this.viewerID = viewerID;
        this.bookingHistory = getBookingHistoryFromFile();
    }

    /**
     * Adds a new booking to the viewer's booking history
     *
     * @param booking A string of the new booking to be added to the booking history
     */
    public void addNewBookingToHistory(String booking) {
        ArrayList<String> current = getBookingHistory();

        if (current.get(0).equals("NA")) {
            current.remove(0);
        }
        current.add(booking);
        this.bookingHistory = current;
        ViewerDB.writeFile(ViewerDB.updateBookingHistory(this.getEmail(), this.getBookingHistory()));
    }

    /**
     * Find and return an arraylist of the booking history from the viewer text file
     *
     * @return Booking history
     */
    public ArrayList<String> getBookingHistoryFromFile() {
        try {
            HashMap<String, ArrayList<String>> viewers_history = ViewerDB.getViewersHistory();
            return viewers_history.get(this.email);
        } catch (Exception e) {
            System.out.println("IOException > " + e.getMessage());
        }
        return new ArrayList<>();
    }


    /**
     * Creates and stores a new booking
     *
     * @param tID       The transactionID of the booking
     * @param seatID    The seat in the cinema which was booked for the movie
     * @param movieName the movie which was booked
     */
    public void createNewBookingInstance(String tID, String seatID, String movieName) {
        // XXXYYYYMMDDHHMM
        String booking = tID + "-" + seatID + "-";
        String date = "-" + tID.substring(9, 11) + "/" + tID.substring(7, 9) + "/" + tID.substring(3, 7);
        this.addNewBookingToHistory(booking + movieName + date);
    }

    /**
     * Gets the emailID of the student
     *
     * @return This Viewer's emailID
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the viewer's booking History from the value stored in the instance
     *
     * @return This viewer's booking history
     */
    public ArrayList<String> getBookingHistory() {
        return bookingHistory;
    }

    /**
     * Gets the Viewer's full Name
     *
     * @return this viewer's full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * gets this viewer's ViewerID
     *
     * @return This viewer's viewerID
     */
    public String getViewerID() {
        return viewerID;
    }

    /**
     * gets this Viewer mobileNumber
     *
     * @return This viewer's mobile number
     */
    public String getMobileNumber() {
        return mobileNumber;
    }

}