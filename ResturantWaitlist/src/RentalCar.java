import java.util.Scanner;

public class RentalCar {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("What is your name?: ");
        String name = scanner.nextLine();
        System.out.print("What is your age?: ");
        int age = Integer.parseInt(scanner.nextLine());

        if (age > 25) {
            System.out.println(name + " can rent a car!");
        } else {
            System.out.println(name + " is not old enough to rent a car!");
        }
        scanner.close();
    }
}
