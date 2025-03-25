package PlaneManagement;

public class Person {
    private String name;
    private String surname;
    private String email;

    public Person(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public String get_name() {
        return name;
    }

    public String get_surname() {
        return surname;
    }

    public String get_email() {
        return email;
    }

    public void printInfo() {
        System.out.println("-------------------------------------------------");
        System.out.println("Name: " + name);
        System.out.println("Surname: " + surname);
        System.out.println("Email: " + email);
        System.out.println("-------------------------------------------------");
    }
}
