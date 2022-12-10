import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A Class that handles seat booking
 *
 * @author Shikhar Jain 
 * @version 1.0
 * @since 2022-11-05
 */
public class Booking {
    /**
     * Color for red text
     */
    public static final String RED = "\u001B[31m";
    /**
     * Color for green text
     */
    public static final String GREEN = "\u001B[32m";
    /**
     * Color for normal text
     */
    public static final String RESET = "\u001B[0m";

    /**
     * Prints out seats and their details
     *
     * @param timeslot seat data
     */
    public static void availableSeats(TimeSlot timeslot) { //add numbering
        Seat[][] seats = timeslot.getSeats();

        System.out.print("  ");
        int x = 1;
        for (int q = 0; q < 17; q++) {
            if (q == 8) {
                System.out.print("  ");
                continue;
            }

            System.out.printf("%-3d", x);
            x++;
        }
        System.out.println();
        char[] letters = {'*', 'I', 'H', 'G', 'F', 'E', 'D', 'C', 'B', 'A'};

        for (int row = 0; row < 10; row++) {
            System.out.printf("%c", letters[row]);
            System.out.print(" ");
            int column = 0;
            for (int j = 0; j < 17; j++) {
                if (j == 8) {
                    System.out.print("  ");
                    continue;
                }
                if (seats[row][column].isAssigned()) {
                    if (row == 8 || row == 9) {
                        if (column % 2 != 0) {
                            System.out.printf("%s%-3s%s", RED, "X", RESET);
                        } else {
                            System.out.printf("%s%-3s%s", RED, "X--", RESET);
                        }
                    } else {
                        System.out.printf("%s%-3s%s", RED, "X", RESET);
                    }
                } else {
                    if (row == 8 || row == 9) {

                        if (column % 2 != 0) {
                            System.out.printf("%s%-3s%s", GREEN, "O", RESET);

                        } else {
                            System.out.printf("%s%-3s%s", GREEN, "O--", RESET);
                        }
                    } else {
                        System.out.printf("%s%-3s%s", GREEN, "O", RESET);
                    }
                }
                column++;
            }
            System.out.println();
        }
        System.out.println("Legend:");
        System.out.printf("Seats that can be booked: %sO%s\n", GREEN, RESET);
        System.out.printf("Seats that are occupied: %sX%s\n", RED, RESET);
        System.out.printf("%-15s\n", "Elite Seat: *");
        System.out.printf("%-15s\n\n", "Couple Seat: O--O");
    }

    /**
     * Books a seat
     *
     * @param listing  movie details
     * @param timeslot seat data
     * @param settings class used to calculate ticket price
     * @return arraylist of seats that have been booked
     */
    public static ArrayList<String> makeBooking(MovieListing listing, TimeSlot timeslot, Settings settings) {
        //assign a seat from 2D seat array in movieListing
        ArrayList<String> holidays = settings.getHolidays();
        Seat[][] seats = timeslot.getSeats();
        char[] letters = {'*', 'I', 'H', 'G', 'F', 'E', 'D', 'C', 'B', 'A'};
        Scanner sc = new Scanner(System.in);
        ArrayList<String> selectedSeats = new ArrayList<>();
        ArrayList<Integer> ages = new ArrayList<>();
        boolean firstBooking = true;
        a:
        while (true) {
            if (firstBooking) {
                System.out.println("Would you like to book a seat? (y/n): ");
                firstBooking = false;
            } else {
                System.out.println("Would you like to book another seat? (y/n): ");
            }
            String flag;
            while (!(flag = sc.nextLine()).equals("y")) {
                if (flag.equals("n")) {
                    break a;
                }
                System.out.println("Invalid Entry. Please try again. ");
                System.out.println("Would you like to book another seat? (y/n): ");
            }
            System.out.println("Enter your choice of seat (eg. 1A):");
            String seatNo = sc.nextLine();

            if (seatNo.charAt(seatNo.length() - 1) == 'B' || seatNo.charAt(seatNo.length() - 1) == 'A') {
                for (int numAges = 0; numAges < 2; numAges++) {
                    System.out.printf("Enter age of person %d in couple: \n", numAges + 1);
                    int age = sc.nextInt();
                    sc.nextLine();
                    ages.add(age);
                }
                selectedSeats.add(seatNo);
                int seatNum = Integer.parseInt(seatNo.substring(0, seatNo.length() - 1));
                if (seatNum % 2 == 0) {
                    selectedSeats.add(seatNum - 1 + seatNo.substring(seatNo.length() - 1));
                } else {
                    selectedSeats.add(seatNum + 1 + seatNo.substring(seatNo.length() - 1));
                }
            } else {
                System.out.println("Enter age of the viewer: ");
                int age2 = sc.nextInt();
                sc.nextLine();
                ages.add(age2);
                selectedSeats.add(seatNo);
            }
        }
        double totalPrice = 0.0;
        for (int i = 0; i < selectedSeats.size(); i++) {
            String seatSelect = selectedSeats.get(i);
            int num = Integer.parseInt(seatSelect.substring(0, seatSelect.length() - 1));
            char alpha = seatSelect.charAt(seatSelect.length() - 1);
            int letter;
            for (letter = 0; letter < letters.length; letter++) {
                if (letters[letter] == alpha) {
                    break;
                }
            }
            int priceAdded;
            int actAge;
            if (letter == 0) { //elite
                seats[letter][num - 1].assign();
                int movieType = listing.getMovieType();
                actAge = ages.get(i);
                int myAge = getAgeType(actAge);
                int day = timeslot.getDayType(holidays);

                priceAdded = settings.calculateTicketPrice(movieType, 1, myAge, day);
                String typeMovie;
                String typeDay;
                String typeAge;
                switch (movieType) {
                    case 0 -> typeMovie = "IMAX_2D";
                    case 1 -> typeMovie = "IMAX_3D";
                    default -> typeMovie = "BLOCKBUSTER";
                }
                switch (day) {
                    case 0 -> typeDay = "Weekday";
                    case 1 -> typeDay = "Weekend";
                    default -> typeDay = "Holiday";
                }
                switch (myAge) {
                    case 0 -> typeAge = "Adult";
                    case 1 -> typeAge = "Child (<12 years)";
                    default -> typeAge = "Senior Citizen (>60 years)";
                }
                System.out.printf("\nBase ticket price : %d\n", settings.baseTicketPrice);
                System.out.printf("Additional price for movie type '%s' : %d\n", typeMovie, settings.typeMod[movieType]);
                System.out.printf("Change in price for day type '%s' : %d\n", typeDay, settings.dayMod[day]);
                System.out.printf("Change in price for seat type '%s' : %d\n", "elite", settings.seatMod[1]);
                System.out.printf("Change in price for '%s' : %d\n", typeAge, settings.ageMod[myAge]);
                System.out.printf("Total Price for seat %s : %d\n", seatSelect, priceAdded);
                System.out.println();

            } else {
                seats[letter][num - 1].assign();
                int movieType = listing.getMovieType();
                int myAge = ages.get(i);
                myAge = getAgeType(myAge);
                int day = timeslot.getDayType(holidays);

                priceAdded = settings.calculateTicketPrice(movieType, 0, myAge, day);
                String typeMovie;
                String typeDay;
                String typeAge;
                switch (movieType) {
                    case 0 -> typeMovie = "IMAX_2D";
                    case 1 -> typeMovie = "IMAX_3D";
                    default -> typeMovie = "BLOCKBUSTER";
                }
                switch (day) {
                    case 0 -> typeDay = "Weekday";
                    case 1 -> typeDay = "Weekend";
                    default -> typeDay = "Holiday";
                }
                switch (myAge) {
                    case 0 -> typeAge = "Adult";
                    case 1 -> typeAge = "Child (<12 years)";
                    default -> typeAge = "Senior Citizen (>60 years)";
                }
                System.out.printf("\nBase ticket price : %d\n", settings.baseTicketPrice);
                System.out.printf("Additional price for movie type '%s' : %d\n", typeMovie, settings.typeMod[movieType]);
                System.out.printf("Change in price for day type '%s' : %d\n", typeDay, settings.dayMod[day]);
                System.out.printf("Change in price for '%s' : %d\n", typeAge, settings.ageMod[myAge]);
                System.out.printf("Price for seat %s : %d", seatSelect, priceAdded);
                System.out.println();
            }
            totalPrice += priceAdded;
            timeslot.setSeats(seats);
            //set value of seats

        }
        System.out.printf("The total amount for seats is: %s%.2f SGD%s, Inclusive of GST.\n", RED, totalPrice, RESET);
        return selectedSeats;
    }

    /**
     * Returns cinema ID and time of booking
     *
     * @param cinema cinema that seat booking is created in
     * @return cinema ID and time as string
     */
    public static String completePayment(Cinema cinema) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        LocalDateTime now = LocalDateTime.now();
        String currTime = dtf.format(now);
        String cinemaCode = cinema.getCinemaId();
        return cinemaCode + currTime;

    }

    /**
     * Returns what age group user is in
     *
     * @param myAge are of viewer
     * @return age group
     */
    public static int getAgeType(int myAge) {
        if (myAge < 12) {
            myAge = 1;
        } else if (myAge > 60) {
            myAge = 2;
        } else {
            myAge = 0;
        }
        return myAge;
    }

}
