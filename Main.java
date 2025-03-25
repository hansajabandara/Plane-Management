package PlaneManagement;

import java.io.FileWriter; //importing filewriter in java for textfile 
import java.io.IOException;
import java.util.Scanner; // for inputs inn java

public class Main { //creating the class PlaneManagement

    private static final int total_rows = 4;
    private static final int[] seats_per_row = {14, 12, 12, 14}; //creating array of seats_per_row
    private static final char[] row_letters = {'A', 'B', 'C', 'D'}; //creating array of row_letters
    private static int[][] seats = new int[total_rows][];
    private static Ticket[] tickets = new Ticket[total_rows * 14]; // Assuming each row has 14 seats

    public static void main(String[] args) {
        initialize_seats();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("Welcome To The Plane Management Application");
            System.out.println("*************************************************\n*                 MENU OPTIONS                  *\n*************************************************");
            System.out.println(" 1) Buy a seat");
            System.out.println(" 2) Cancel a seat");
            System.out.println(" 3) Find first available seat");
            System.out.println(" 4) Show seating plan");
            System.out.println(" 5) Print tickets information and total sales");
            System.out.println(" 6) Search ticket");
            System.out.println(" 0) Exit");
            System.out.println("*************************************************");
            System.out.print("Enter your choice: ");
            int choice;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice < 0 || choice > 6) {
                    System.out.println("Invalid choice. Please try again.");
                    continue;
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                continue;
            }
            switch (choice) {
                case 1:
                    buy_seat(scanner);
                    break;
                case 2:
                    cancel_seat(scanner);
                    break;
                case 3:
                    find_first_available();
                    break;
                case 4:
                    show_seating_plan();
                    System.out.println("-----------------------------------------------------");
                    break;
                case 5:
                    print_tickets_info();
                    break;
                case 6:
                    search_ticket(scanner);
                    break;
                case 0:
                    System.out.println("-----------------------------------------------------");
                    System.out.println("Exiting program...Thank you come again..");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }

        scanner.close();
    }

    private static void initialize_seats() {
        for (int i = 0; i < total_rows; i++) {
            seats[i] = new int[seats_per_row[i]];
            for (int j = 0; j < seats_per_row[i]; j++) {
                seats[i][j] = 0;
            }
        }
    }

    private static void buy_seat(Scanner scanner) {
        System.out.print("Enter row letter (A-D): ");
        char rowLetter = scanner.next().toUpperCase().charAt(0);
        if (rowLetter < 'A' || rowLetter > 'D') {
            System.out.println("Invalid row letter. Please enter a letter from A to D.");
            return;
        }
        System.out.print("Enter seat number (1-" + seats_per_row[rowLetter - 'A'] + "): ");
        int seatNumber;
        if (scanner.hasNextInt()) {
            seatNumber = scanner.nextInt();
        } else {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // Clear the invalid input
            return; // Return to the menu
        }
    
        int row = rowLetter - 'A';
        int seatIndex = seatNumber - 1;
    
        if (row < 0 || row >= total_rows || seatIndex < 0 || seatIndex >= seats_per_row[row]) {
            System.out.println("Invalid row or seat number.");
            return;
        }
    
        if (seats[row][seatIndex] == 1) {
            System.out.println("Seat already sold. Please select another seat.");
            return;
        }
    
        System.out.print("Enter person's first name: ");
        String name = scanner.next();
        System.out.print("Enter person's surname: ");
        String surname = scanner.next();
        System.out.print("Enter person's email: ");
        String email = scanner.next();
    
        Person person = new Person(name, surname, email);
        double price = calculate_price(row, seatNumber);
        Ticket ticket = new Ticket(rowLetter, seatNumber, price, person);
        tickets[row * 14 + (seatNumber - 1)] = ticket;
        seats[row][seatIndex] = 1;
        save(ticket);
        System.out.print(rowLetter + "-");
        System.out.print(seatNumber + " ");
        System.out.println("Seat bought successfully.");
    }
    

    private static double calculate_price(int row, int seatNumber) {
        char rowLetter = row_letters[row];
        if ((rowLetter == 'A' && seatNumber <= 5) ||
                (rowLetter == 'B' && seatNumber <= 9) ||
                (rowLetter == 'C' && seatNumber <= 9) ||
                (rowLetter == 'D' && seatNumber <= 5)) {
            return 200.0;
        } else if ((rowLetter == 'A' && seatNumber >= 6 && seatNumber <= 9) ||
                (rowLetter == 'B' && seatNumber >= 6 && seatNumber <= 9) ||
                (rowLetter == 'C' && seatNumber >= 6 && seatNumber <= 9) ||
                (rowLetter == 'D' && seatNumber >= 6 && seatNumber <= 9)) {
            return 150.0;
        } else {
            return 180.0;
        }
    }


    private static void cancel_seat(Scanner scanner) {
        System.out.print("Enter row letter (A-D): ");
        char rowLetter;
        if (scanner.hasNext("[A-Da-d]")) {
            rowLetter = scanner.next().toUpperCase().charAt(0);
        } else {
            System.out.println("Invalid row letter. Please enter a letter from A to D.");
            scanner.next(); // Clear the invalid input
            return; // Return to the menu
        }
    
        if (rowLetter < 'A' || rowLetter > 'D') {
            System.out.println("Invalid row letter. Please enter a letter from A to D.");
            return;
        }
    
        System.out.print("Enter seat number (1-" + seats_per_row[rowLetter - 'A'] + "): ");
        int seatNumber;
        if (scanner.hasNextInt()) {
            seatNumber = scanner.nextInt();
        } else {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // Clear the invalid input
            return; // Return to the menu
        }
    
        int row = rowLetter - 'A';
        int seatIndex = seatNumber - 1;
    
        if (row < 0 || row >= total_rows || seatIndex < 0 || seatIndex >= seats_per_row[row]) {
            System.out.println("Invalid row or seat number.");
            return;
        }
    
        if (seats[row][seatIndex] == 0) {
            System.out.print(rowLetter + "-");
            System.out.print(seatNumber + " ");
            System.out.println("Seat is already available.");
            return;
        }
    
        seats[row][seatIndex] = 0;
        Ticket removedTicket = tickets[row * 14 + (seatNumber - 1)];
        if (removedTicket != null) {
            tickets[row * 14 + (seatNumber - 1)] = null;
            System.out.print(rowLetter + "-");
            System.out.print(seatNumber + " ");
            System.out.println("Seat canceled successfully.");
        }
    }
    

    private static void find_first_available() { //creating the find_first_available seat method
        for (int i = 0; i < total_rows; i++) {
            for (int j = 0; j < seats_per_row[i]; j++) {
                if (seats[i][j] == 0) {
                    System.out.println("First available seat: Row " + row_letters[i] + ", Seat " + (j + 1));
                    return;
                }
            }
        }
        System.out.println("No available seats.");
    }

    private static void show_seating_plan() { //creating the show_seating_plan method
        System.out.println("-------------------------------------------------");
        System.out.println("Seating Plan:");
        System.out.println("-------------------------------------------------");
        System.out.println("Available seats --> O   " +"|"+ "    Sold seats --> X");
        System.out.println("-------------------------------------------------");
        System.out.print("   ");
        
        for (int i = 1; i <= 14; i++) {
            System.out.print((i < 10 ? " " : "") + i + " ");
        }
        System.out.println();
        for (int i = 0; i < total_rows; i++) {
            System.out.print(row_letters[i] + "  ");
            for (int j = 0; j < seats_per_row[i]; j++) {
                System.out.print(" " + (seats[i][j] == 0 ? "O" : "X") + " ");
            }
            System.out.println();
        }
    }

    private static void print_tickets_info() { //creating a method called print_ticket_info for print the ticket information and the total sales
        double totalSales = 0;
        System.out.println("-------------------------------------------------");
        System.out.println("             Tickets Information:");
        for (Ticket ticket : tickets) {
            if (ticket != null) {
                ticket.printInfo();
                totalSales += ticket.get_price();
            }
        }
        System.out.println("Total Sales: £" + totalSales);
        System.out.println("-------------------------------------------------");
    }

    private static void search_ticket(Scanner scanner) {
        System.out.print("Enter row letter (A-D): ");
        char rowLetter;
        if (scanner.hasNext("[A-Da-d]")) {
            rowLetter = scanner.next().toUpperCase().charAt(0);
        } else {
            System.out.println("Invalid row letter. Please enter a letter from A to D.");
            scanner.next(); // Clear the invalid input
            return; // Return to the menu
        }
    
        if (rowLetter < 'A' || rowLetter > 'D') {
            System.out.println("Invalid row letter. Please enter a letter from A to D.");
            return;
        }
    
        System.out.print("Enter seat number (1-" + seats_per_row[rowLetter - 'A'] + "): ");
        int seatNumber;
        if (scanner.hasNextInt()) {
            seatNumber = scanner.nextInt();
        } else {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // Clear the invalid input
            return; // Return to the menu
        }
    
        int row = rowLetter - 'A';
        int seatIndex = seatNumber - 1;
    
        if (row < 0 || row >= total_rows || seatIndex < 0 || seatIndex >= seats_per_row[row]) {
            System.out.println("Invalid row or seat number.");
            return;
        }
    
        Ticket foundTicket = tickets[row * 14 + (seatNumber - 1)];
        if (foundTicket != null) {
            System.out.println("-------------------------------------------------");
            System.out.println("Ticket Found:");
            foundTicket.printInfo();
        } else {
            System.out.println("This seat is available.");
        }
    }
    

    private static void save(Ticket ticket) { //using filewriter option printing the text file
        String fileName = ticket.get_row() + String.valueOf(ticket.get_seat()) + ".txt";
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write("----------------------------------------------------\n");
            fileWriter.write("                 Ticket Information        \n");
            fileWriter.write("Row: " + ticket.get_row() + "\n");
            fileWriter.write("Seat: " + ticket.get_seat() + "\n");
            fileWriter.write("Price: £" + ticket.get_price() + "\n");
            fileWriter.write("----------------------------------------------------\n");
            fileWriter.write("                 Person Information        \n");
            fileWriter.write("First name: " + ticket.get_person().get_name() + "\n");
            fileWriter.write("Surname: " + ticket.get_person().get_surname() + "\n");
            fileWriter.write("Email: " + ticket.get_person().get_email() + "\n");
            fileWriter.write("----------------------------------------------------");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the ticket to a file.");
            e.printStackTrace();
        }
    }
}
