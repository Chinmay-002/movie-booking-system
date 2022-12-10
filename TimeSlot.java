import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Class that stores information on which seats in a cinema are booked at a specific date and time
 *
 * @author Khoo Yong Quan
 * @version 1.0
 * @since 2022-11-05
 */
public class TimeSlot {
//    private int timing;
    /**
     * Date and time
     */
    public LocalDateTime dateTime;
    /**
     * 2d array of seats
     */
    private Seat[][] seating = new Seat[10][16];

//    private final String date;

    /**
     * Constructor, saves date and time and initializes seat array
     *
     * @param timing time of timeslot
     * @param date   date of timeslot
     */
    public TimeSlot(int timing, String date) {
        String time = Integer.toString(timing);
        int hour = Integer.parseInt(time.substring(0, time.length() - 2));
        int minute = Integer.parseInt(time.substring(time.length() - 2));
        int year = Integer.parseInt(date.substring(6));
        int month = Integer.parseInt(date.substring(3, 5));
        int day = Integer.parseInt(date.substring(0, 2));
        this.dateTime = LocalDateTime.of(year, month, day, hour, minute);
        char[] letters = {'*', 'I', 'H', 'G', 'F', 'E', 'D', 'C', 'B', 'A'};
        for (int i = 0; i < 10; i++) {
            char alpha = letters[i];
            for (int j = 0; j < 16; j++) {
                String num = Integer.toString(j + 1);
                String code = num + alpha;
                this.seating[i][j] = new Seat(code);
            }
        }
    }

    /**
     * Change date and time of timeslot
     *
     * @param dateTime Date and time
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Returns array of seats
     *
     * @return Array of seats
     */
    public Seat[][] getSeats() {
        return this.seating;

    }

    /**
     * Change array of seats
     *
     * @param seating Array of seats
     */
    public void setSeats(Seat[][] seating) {
        this.seating = seating;

    }

    /**
     * Returns time of timeslot
     *
     * @return Time
     */
    public int getTime() {
        return this.dateTime.getHour() * 100 + this.dateTime.getMinute();
    }

    /**
     * Change time of timeslot
     *
     * @param time Time
     */
    public void setTime(int time) {
        String s_time = Integer.toString(time);
        int hour = Integer.parseInt(s_time.substring(0, s_time.length() - 2));
        int minute = Integer.parseInt(s_time.substring(s_time.length() - 2, s_time.length() - 1));
        int year = this.dateTime.getYear();
        int month = this.dateTime.getMonthValue();
        int day = this.dateTime.getDayOfMonth();
        this.dateTime = LocalDateTime.of(year, month, day, hour, minute);
    }

    /**
     * Returns date of timeslot
     *
     * @return Date
     */
    public String getDate() {
        return this.dateTime.getDayOfMonth() + "/" + this.dateTime.getMonthValue() + "/" + this.dateTime.getYear();
    }

    /**
     * Change seat booking status
     *
     * @param i   x coordinate of seat
     * @param j   y coordinate of seat
     * @param val Value to change the status to
     */
    public void setSeat(int i, int j, boolean val) {
        if (val) {
            this.seating[i][j].assign();
        } else {
            this.seating[i][j].unAssign();
        }
    }

    /**
     * Check if date is holiday, weekend or weekday
     *
     * @param holidays Arraylist of dates that are holidays
     * @return 0:weekday 1:weekend 2: holiday
     */
    public int getDayType(ArrayList<String> holidays) {

        LocalDateTime date = this.dateTime;
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateType = f.format(date);
        for (String holiday : holidays) {
            if (holiday.equals(dateType)) {
                return 2;
            }
        }
        String dayType = this.dateTime.getDayOfWeek().name();
        if (dayType.equals("SATURDAY") || dayType.equals("SUNDAY")) {
            return 1;
        }
        return 0;

    }

}
