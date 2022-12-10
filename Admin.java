import java.util.HashMap;


/**
 * Represent Admins of the Movie booking System. Each Cineplex has two admins.
 * Each admin may only work under one cineplex
 *
 * @author Chinmay Prasad
 * @version 1.0
 * @since 2022-11-05
 */
public class Admin {

    /**
     * Verifies the username and password against the usernames and passwords
     * against the data in the Admin text file
     *
     * @param username username used for login
     * @param password password corresponding to the username used
     * @return True if the login is successful, else false
     */
    public static boolean login(String username, String password) {
        String fileName = "data/admin.txt";
        HashMap<String, String> data = AdminDB.getAdminData(fileName);
        try {
            if (password.equals(data.get(username))) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Exception > " + e.getMessage());
        }
        return false;
    }
}
