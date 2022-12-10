import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
//import java.time.Month;

/**
 * Class that stores settings
 *
 * @author Samuel Ng
 * @version 1.0
 * @since 2022-11-05
 */
public class Settings {
    /**
     * Formats the date into specified string
     */
    public static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    /**
     * How much each type of seat modifies the cost
     */
    final int[] typeMod = new int[3];
    /**
     * How much each type of seat modifies the cost
     */
    final int[] seatMod = new int[2];
    /**
     * How much each type of age group modifies the cost
     */
    final int[] ageMod = new int[3];
    /**
     * How much each type of day modifies the cost
     */
    final int[] dayMod = new int[3];
    /**
     * Stores the dates that are holidays
     */
    ArrayList<String> holidays = new ArrayList<>();
    /**
     * How much each type of movie modifies the cost
     */
    public int baseTicketPrice;

    /**
     * Boolean variable that denotes if the viewers can display movies sorted by review
     */
    public static boolean byReview = true;

    /**
     * Boolean variable that denotes if the viewers can display movies sorted by tickets
     */
    public static boolean byTicket = true;

    /**
     * Constructor, load settings from file
     */
    public Settings() {
        this.loadSettings();
    }

    /**
     * Stores settings to file
     */
    public void storeSettings() {
        ArrayList<String> data = new ArrayList<>();
        data.add(Integer.toString(baseTicketPrice));
        data.add("\n");
        data.add(Integer.toString(typeMod[0]));
        data.add("\n");
        data.add(Integer.toString(typeMod[1]));
        data.add("\n");
        data.add(Integer.toString(typeMod[2]));
        data.add("\n");
        data.add(Integer.toString(seatMod[0]));
        data.add("\n");
        data.add(Integer.toString(seatMod[1]));
        data.add("\n");
        data.add(Integer.toString(ageMod[0]));
        data.add("\n");
        data.add(Integer.toString(ageMod[1]));
        data.add("\n");
        data.add(Integer.toString(ageMod[2]));
        data.add("\n");
        data.add(Integer.toString(dayMod[0]));
        data.add("\n");
        data.add(Integer.toString(dayMod[1]));
        data.add("\n");
        data.add(Integer.toString(dayMod[2]));
        data.add("\n");
        if(byReview){
            data.add(Integer.toString(1));
        }else{
            data.add(Integer.toString(0));
        }
        data.add("\n");
        if(byTicket){
            data.add(Integer.toString(1));
        }else{
            data.add(Integer.toString(0));
        }
        data.add("\n");


        try {
            SettingsDB.writeFile("data/settings.txt", data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<String> data2 = new ArrayList<>();

        for (String holiday : holidays) {
            data2.add(holiday);
            data2.add("\n");
        }

        try {
            SettingsDB.writeFile("data/holidays.txt", data2);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Load settings from file
     */
    public void loadSettings() {
        ArrayList<String> data;
        try {
            data = SettingsDB.readFile("data/settings.txt");
            baseTicketPrice = Integer.parseInt(data.get(0));
            typeMod[0] = Integer.parseInt(data.get(1));
            typeMod[1] = Integer.parseInt(data.get(2));
            typeMod[2] = Integer.parseInt(data.get(3));
            seatMod[0] = Integer.parseInt(data.get(4));
            seatMod[1] = Integer.parseInt(data.get(5));
            ageMod[0] = Integer.parseInt(data.get(6));
            ageMod[1] = Integer.parseInt(data.get(7));
            ageMod[2] = Integer.parseInt(data.get(8));
            dayMod[0] = Integer.parseInt(data.get(9));
            dayMod[1] = Integer.parseInt(data.get(10));
            dayMod[2] = Integer.parseInt(data.get(11));
            byReview= data.get(12).equals("1");
            byTicket= data.get(13).equals("1");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<String> data2;
        try {
            holidays = new ArrayList<>();
            data2 = SettingsDB.readFile("data/holidays.txt");
            for (String line : data2) {
                if (!(line.equals("\n"))) {
                    holidays.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints out settings
     */
    public void printSettings() {
        System.out.println("Base Ticket Price: $" + baseTicketPrice);
        System.out.println("IMAX 2D movie price modifier: " + typeMod[0]);
        System.out.println("IMAX 3D movie price modifier: " + typeMod[1]);
        System.out.println("Blockbuster movie price modifier: " + typeMod[2]);
        System.out.println("Normal seat price modifier: " + seatMod[0]);
        System.out.println("Elite seat price modifier: " + seatMod[1]);
        System.out.println("Adult price modifier: " + ageMod[0]);
        System.out.println("Child price modifier: " + ageMod[1]);
        System.out.println("Senior Citizen price modifier: " + ageMod[2]);
        System.out.println("Weekday price modifier: " + dayMod[0]);
        System.out.println("Weekend price modifier: " + dayMod[1]);
        System.out.println("Holiday price modifier: " + dayMod[2]);
        System.out.print("Holidays: ");
        for (String holiday : holidays) {
            System.out.printf("%s\n", holiday);
        }
        System.out.println();
        System.out.println();
    }

    /**
     * Adds date as holiday
     *
     * @param date  Day
     * @param month Month
     * @param year  Year
     */
    public void addHoliday(int date, int month, int year) {
        LocalDateTime time = LocalDateTime.of(year, month, date, 0, 0, 0);
        String holidate = dtf.format(time);
        holidays.add(holidate);

    }

    /**
     * removes date from holiday
     *
     * @param date  Day
     * @param month Month
     * @param year  Year
     */
    public void removeHoliday(int date, int month, int year) {
        LocalDateTime time = LocalDateTime.of(year, month, date, 0, 0, 0);
        String holidate = dtf.format(time);
        for (int j = 0; j < holidays.size(); j++) {
            if (holidays.get(j).equals(holidate)) {
                holidays.remove(j);
                break;
            }
        }
    }

    /**
     * Change the base price of a ticket
     *
     * @param newPrice New price
     */
    public void changeBasePrice(int newPrice) {
        baseTicketPrice = newPrice;
    }

    /**
     * Changes a setting
     *
     * @param setting Which setting to change
     * @param option  Which option in setting to change
     * @param value   New value
     */
    public void changeTicketPrice(int setting, int option, int value) {
        if (setting == 1) {
            typeMod[option - 1] = value;
        }
        if (setting == 2) {
            seatMod[option - 1] = value;
        }
        if (setting == 3) {
            ageMod[option - 1] = value;
        }
        if (setting == 4) {
            dayMod[option - 1] = value;
        }
    }

    /**
     * calculates price of ticket
     *
     * @param cinemaType  Type of movie
     * @param cinemaClass Class of cinema
     * @param age         Age of viewer
     * @param day         Day that movie is booked
     * @return Price of ticket
     */
    public int calculateTicketPrice(int cinemaType, int cinemaClass, int age, int day) {
        return baseTicketPrice + typeMod[cinemaType] + seatMod[cinemaClass] + ageMod[age] + dayMod[day];
    }

    /**
     * Returns arraylist of holidays
     *
     * @return Arraylist of holidays
     */
    public ArrayList<String> getHolidays() {
        return this.holidays;
    }

}
