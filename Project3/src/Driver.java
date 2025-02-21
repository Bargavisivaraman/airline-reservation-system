import java.io.*;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        Patrol patrol = new Patrol();
        Travel travel = new Travel();
        Tour tour = new Tour();
        Scanner scanner = new Scanner(System.in);

        // Read files and initialize respective classes
        readFileAndAddEdges("src/patrol.txt", patrol);
        readFileAndAddEdges("src/travel.txt", travel);
        readFileAndAddEdges("src/tour.txt", tour);

        // Menu options
        while (true) {
            // Display menu to the user
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Display Patrol Edges (Minimum Spanning Tree)");
            System.out.println("2. Find Fastest Path Between Two Planets");
            System.out.println("3. Quick Tour from a Starting Point");
            System.out.println("4. Exit");
            System.out.print("Please choose an option (1-4): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline character

            switch (choice) {
                case 1:
                    // Display Patrol Edges
                    System.out.println("\n=== Minimum Spanning Tree ===");
                    patrol.patrolEdges();
                    break;

                case 2:
                    // Find Fastest Path Between Two Planets
                    System.out.print("\nEnter the start planet: ");
                    String startPlanet = scanner.nextLine();
                    System.out.print("Enter the destination planet: ");
                    String endPlanet = scanner.nextLine();
                    travel.quickTravel(startPlanet, endPlanet); // Assuming quickTravel is defined
                    break;

                case 3:
                    // Quick Tour from a Starting Point
                    System.out.print("\nEnter the starting planet for the tour: ");
                    String startTour = scanner.nextLine();
                    tour.quickTour(startTour); // Assuming quickTour is defined
                    break;

                case 4:
                    // Exit the program
                    System.out.println("Exiting program...");
                    scanner.close();
                    return; // Exit the loop and the program

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Utility method to read edges from a file and add them to the respective class (Patrol, Travel, Tour)
    private static void readFileAndAddEdges(String filePath, Object obj) {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().trim().split("\\s+");
                if (parts.length != 3) continue;

                String start = parts[0];
                String end = parts[1];
                int weight = Integer.parseInt(parts[2]);

                if (obj instanceof Patrol) {
                    ((Patrol) obj).addEdge(start, end, weight);
                } else if (obj instanceof Travel) {
                    ((Travel) obj).addEdge(start, end, weight);
                } else if (obj instanceof Tour) {
                    ((Tour) obj).addEdge(start, end, weight);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file " + filePath);
        }
    }
}