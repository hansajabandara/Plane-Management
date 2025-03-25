package PlaneManagement;

public class Ticket {
    private char row;
    private int seat;
    private double price;
    private Person person;

    public Ticket(char row, int seat, double price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    public char get_row() {
        return row;
    }

    public int get_seat() {
        return seat;
    }

    public double get_price() {
        return price;
    }

    public Person get_person() {
        return person;
    }

    public void printInfo() {
        System.out.println("Row: " + row);
        System.out.println("Seat: " + seat);
        System.out.println("Price: £" + price);
        System.out.println("             Person Information:");
        person.printInfo();
    }
}
