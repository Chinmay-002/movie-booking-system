import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * A class which reads data from the Admin text file
 *
 * @author Chinmay Prasad
 * @version 1.0
 * @since 2022-11-05
 */
public class ViewerDB {

    /**
     * The character that separates entries in the text file
     */
    public static final String SEPARATOR = "|";

    /**
     * Reads data from a file and returns its contents in an ArrayList
     *
     * @param fileName the path of the file(from the content root)
     * @return an ArrayList of strings containing lines of the read file
     */
    public static ArrayList<String> readFile(String fileName) {
        File f = new File(fileName);
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
     * Writes data from an Arraylist into a file
     *
     * @param data An arraylist of String data
     */
    public static void writeFile(ArrayList<String> data) {

        String fileName = "data/viewer.txt";
        File f = new File(fileName);
        try (FileWriter out = new FileWriter(f, false)) {
            for (String line : data) {
                out.write(line + "\n");
            }
        } catch (Exception e) {
            System.out.println("Exception >> " + e.getMessage());

        }
    }

    /**
     * Reads the Viewer text file and returns a hashmap of email id's corresponding to the viewer's booking history
     *
     * @return A Hashmap of email ids corresponding to an Arraylist of the viewer's booking history
     */
    public static HashMap<String, ArrayList<String>> getViewersHistory() {
        String fileName = "data/viewer.txt";
        ArrayList<String> string_data = readFile(fileName);
        HashMap<String, ArrayList<String>> viewers_history = new HashMap<>();
        for (String line : string_data) {
            ArrayList<String> history = new ArrayList<>();
            StringTokenizer star = new StringTokenizer(line, SEPARATOR);
            star.nextToken();
            star.nextToken();
            String email = star.nextToken().trim();
            star.nextToken();
            while (star.hasMoreTokens()) {
                if (line.equals("NA")) {
                    history.add("NA");
                    break;
                }
                history.add(star.nextToken().trim());
            }
            viewers_history.put(email, history);
        }
        return viewers_history;
    }

    /**
     * Find the ID value of the last viewer to be created
     *
     * @return The id if found, else -1
     */
    public static int getLastViewerID() {
        try {
            String fileName = "data/viewer.txt";
            File f = new File(fileName);
            String id;
            String line = "1021|";
            try (Scanner scanner = new Scanner(f)) {
                while (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                }
                StringTokenizer st = new StringTokenizer(line, SEPARATOR);
                id = st.nextToken().trim();
            }
            return Integer.parseInt(id);
        } catch (Exception e) {
            System.out.println("Exception >> " + e.getMessage());
        }
        return -1;
    }

    /**
     * Updates the booking history of a viewer and returns the data in a format
     * that can be used to write into the viewer text file
     *
     * @param email   Email id of the viewer
     * @param booking The updated booking history of a viewer
     * @return booking Data that can be used to write into the text file
     */
    public static ArrayList<String> updateBookingHistory(String email, ArrayList<String> booking) {
        ArrayList<String> data = readFile("data/viewer.txt");
        int update_index = 0;
        String id = "";
        String name = "";
        String number = "";
        String data_email = "";

        for (int i = 0; i < data.size(); i++) {
            String line = data.get(i);
            StringTokenizer star = new StringTokenizer(line, SEPARATOR);
            id = star.nextToken();
            name = star.nextToken();
            data_email = star.nextToken().trim();
            number = star.nextToken().trim();
            star.nextToken();
            if (email.equals(data_email)) {
                update_index = i;
                break;
            }
        }
        StringBuilder new_viewer_data = new StringBuilder(id + SEPARATOR + name + SEPARATOR + data_email + SEPARATOR + number);
        for (String line : booking) {
            new_viewer_data.append(SEPARATOR).append(line);
        }

        data.set(update_index, new_viewer_data.toString());
        return data;
    }

    /**
     * Return all email and password of all users
     *
     * @param fileName Location of file
     * @return Arraylist of all user's email and password
     */
    public static HashMap<String, String> getViewerData(String fileName) {
        HashMap<String, String> viewer_data = new HashMap<>();
        ArrayList<String> string_data = ViewerDB.readFile(fileName);
        for (String line : string_data) {
            StringTokenizer st = new StringTokenizer(line, SEPARATOR);
            st.nextToken();
            st.nextToken();
            String email = st.nextToken().trim();
            String password = st.nextToken().trim();
            viewer_data.put(email, password);
        }
        return viewer_data;
    }

    /**
     * Return email, name, id, number of user
     *
     * @param email Email of user
     * @return Viewer object with email, name, id, number of user
     */
    public static Viewer getViewer(String email) {
        ArrayList<String> data = readFile("data/viewer.txt");

        String number = "";
        String id = "";
        String name = "";
        for (String line : data) {
            StringTokenizer st = new StringTokenizer(line, SEPARATOR);
            id = st.nextToken();
            name = st.nextToken();
            String emailid = st.nextToken().trim();
            number = st.nextToken().trim();
            if (email.equals(emailid)) {
                break;
            }
        }
        return new Viewer(email, name, id, number);
    }

    /**
     * Store a new viewer object in viewer file
     *
     * @param v Viewer object to store
     */
    public static void createViewerInFile(Viewer v) {
        StringBuilder s = new StringBuilder(v.getViewerID() + "|" + v.getFullName() + "|" + v.getEmail() + "|" + v.getMobileNumber());
        String fileName = "data/viewer.txt";
        for (String booking : v.getBookingHistory()) {
            s.append("|").append(booking);
        }
        File f = new File(fileName);
        try (FileWriter out = new FileWriter(f, true)) {
            out.write(s + "\n");
        } catch (Exception e) {
            System.out.println("Exception >> " + e);
        }
    }
}
