import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;


/**
 * A class which reads data from the Admin text file.
 *
 * @author Chinmay Prasad
 * @version 1.0
 * @since 2022-11-05
 */
public class AdminDB {

    /**
     * The character that separates Admin name, username and password entries in the text file.
     */
    public static final String SEPARATOR = "|";

    /**
     * Reads data from a file and returns its contents in an ArrayList
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
     * Reads data from the file and returns a hashmap of admin usernames corresponding to their passwords
     *
     * @param fileName the path of the file(from the content root)
     * @return a HashMap with the usernames of admins as the keys and their passwords as the corresponding values
     */
    public static HashMap<String, String> getAdminData(String fileName) {
        HashMap<String, String> admin_data = new HashMap<>();
        ArrayList<String> string_data = AdminDB.readFile(fileName);
        for (String line : string_data) {
            StringTokenizer st = new StringTokenizer(line, SEPARATOR);
            st.nextToken();
            String username = st.nextToken().trim();
            String password = st.nextToken().trim();
            admin_data.put(username, password);
        }
        return admin_data;
    }
}
