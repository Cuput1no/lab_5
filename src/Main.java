import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Database_cls db;

        try {
            db = new Database_cls();
            db.connect();

            boolean continueLoop = true;

            while (continueLoop) {

                System.out.println("Choose an operation:");
                System.out.println("1. View all rows");
                System.out.println("2. Insert new row");
                System.out.println("3. Update location");
                System.out.println("4. Delete row");
                System.out.println("5. Clear table");
                System.out.println("6. View locations by coordinates");
                System.out.println("7. Exit");

                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:

                        db.selectAll();
                        break;
                    case 2:

                        System.out.print("Enter latitude: ");
                        double lat = scanner.nextDouble();
                        System.out.print("Enter longitude: ");
                        double lon = scanner.nextDouble();
                        System.out.print("Enter element: ");
                        double element = scanner.nextDouble();
                        db.insertRow(lat, lon, element);
                        break;
                    case 3:

                        System.out.print("Enter ID of the location to update: ");
                        int updateId = scanner.nextInt();
                        System.out.print("Enter new latitude: ");
                        double updateLat = scanner.nextDouble();
                        System.out.print("Enter new longitude: ");
                        double updateLon = scanner.nextDouble();
                        System.out.print("Enter new element: ");
                        double updateElement = scanner.nextDouble();
                        db.updateLocation(updateId, updateLat, updateLon, updateElement);
                        break;
                    case 4:
                        
                        System.out.print("Enter ID of the row to delete: ");
                        int deleteId = scanner.nextInt();
                        db.deleteRow(deleteId);
                        break;
                    case 5:
                        
                        db.clearTable();
                        break;
                    case 6:
                        
                        db.getLocation();
                        break;
                    case 7:
                        
                        continueLoop = false;
                        System.out.println("Exiting the program...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        } finally {
            scanner.close(); 
        }
    }
}
