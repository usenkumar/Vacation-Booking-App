package DemoProject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class HotelManagement {

    private static void register(String directoryPath,Scanner scanner) {

        User user = new User();

        int randomNumber = (int) (Math.random() * 10000); // Adjust the range as needed
        String folderName = "acc_" + randomNumber;

        File folder = new File(directoryPath, folderName);
        if (!folder.exists()) {
            boolean folderCreated = folder.mkdirs();
            if (folderCreated) {
                System.out.println("Folder created successfully: " + folder.getAbsolutePath());
            } else {
                System.out.println("Failed to create the folder!");
                return;
            }
        } else {
            System.out.println("Folder already exists!");
        }

        System.out.print("Enter your name: ");
        user.setName(scanner.nextLine());

        System.out.print("Enter your age: ");
        user.setAge(scanner.nextInt());
        scanner.nextLine(); // Consume newline

        System.out.print("Enter your gender: ");
        user.setGender(scanner.nextLine());

        System.out.print("Enter your location: ");
        user.setLocation(scanner.nextLine());

        System.out.print("Enter your phone number: ");
        user.setPhone_number(scanner.nextInt());

        JSONArray bookingDetails = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", user.getName());
        jsonObject.put("age", user.getAge());
        jsonObject.put("gender", user.getGender());
        jsonObject.put("location", user.getLocation());
        jsonObject.put("phonenumber", user.getPhone_number());
        jsonObject.put("bookings", bookingDetails);

        String jsonFilePath = directoryPath + File.separator + folderName + File.separator + "acc_" + randomNumber + ".json";

        try {
            JSONArray jsonArray;
            File dataFile = new File(jsonFilePath);
            if (dataFile.exists() && dataFile.length() > 0) {
                String content = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
                jsonArray = new JSONArray(content);
            } else {
                jsonArray = new JSONArray();
            }
            jsonArray.put(jsonObject);

            try (FileWriter fileWriter = new FileWriter(jsonFilePath)) {
                fileWriter.write(jsonArray.toString());
                System.out.println("User registration details have been saved successfully.");
                System.out.println("Your registration id is"+randomNumber);
            }
        } catch (IOException | JSONException e) {
            System.out.println("An error occurred while writing the JSON file: " + e.getMessage());
        }

    }


    private static void bookVacation(String directoryPath,Scanner scanner) {
        System.out.println("Book Vacation option selected.");


        System.out.print("Enter your registration number: ");
        String registrationNumber = scanner.nextLine();

        String folderName = "acc_" + registrationNumber;
        File folder = new File(directoryPath, folderName);
        if (!folder.exists()) {
            System.out.println("This is not a valid registration number, please register first.");
            return;
        }

//        System.out.print("Enter your name: ");
//        String name = scanner.nextLine();
//
//        System.out.print("Enter your age: ");
//        String age = scanner.nextLine();
//
//        System.out.print("Enter the gender: ");
//        String gender = scanner.nextLine();
//
//        System.out.print("Enter the location: ");
//        String location = scanner.nextLine();
//
//        System.out.print("Enter the contact number: ");
//        String contactNumber = scanner.nextLine();

        System.out.print("Enter the place to visit: ");
        String placeToVisit = scanner.nextLine();

        System.out.print("Enter the booking type (hotel/apartment/villa): ");
        String bookingType = scanner.nextLine();

        System.out.print("Enter the number of people: ");
        int numberOfPeople = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter the number of days stay: ");
        int numberOfDays = scanner.nextInt();

        int bookingNumber = (int) (Math.random() * 10000);
        String bookingFileName = "vcc_" + bookingNumber + ".json";
        String bookingFilePath = folder.getAbsolutePath() + File.separator + bookingFileName;

        JSONObject bookingDetails = new JSONObject();
//        bookingDetails.put("name", name);
//        bookingDetails.put("age", age);
//        bookingDetails.put("gender", gender);
//        bookingDetails.put("location", location);
//        bookingDetails.put("contactnumber", contactNumber);
        bookingDetails.put("placetovisit", placeToVisit);
        bookingDetails.put("bookingtype", bookingType);
        bookingDetails.put("numberofpeople", numberOfPeople);
        bookingDetails.put("numberofdays", numberOfDays);

        try (FileWriter fileWriter = new FileWriter(bookingFilePath)) {
            fileWriter.write(bookingDetails.toString());
            System.out.println("Booking details have been saved successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing the booking details: " + e.getMessage());
        }

        // Update the user's registration file with the new booking
        String registrationFilePath = directoryPath + File.separator + folderName + File.separator + "acc_" + registrationNumber + ".json";

        try {
            String content = new String(Files.readAllBytes(Paths.get(registrationFilePath)));
            JSONArray userArray = new JSONArray(content);
            JSONObject userObject = userArray.getJSONObject(0);
            JSONArray bookingsArray = userObject.getJSONArray("bookings");
            bookingsArray.put(bookingFileName);

            try (FileWriter fileWriter = new FileWriter(registrationFilePath)) {
                fileWriter.write(userArray.toString());
                System.out.println("User registration details have been updated with the new booking.");
            }
        } catch (IOException | JSONException e) {
            System.out.println("An error occurred while updating the registration file: " + e.getMessage());
        }
    }

    private static void myTotalVacations() {
        System.out.println("My Total Vacations option selected.");
        // Implementation for my total vacations
    }

    private static void payBill() {
        System.out.println("Pay Bill option selected.");
        // Implementation for paying bill
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String directoryPath = "C:\\Users\\usenk\\OneDrive\\Desktop\\pro";

        while (true) {
            System.out.println("Options:");
            System.out.println("1. Register");
            System.out.println("2. Book Vacation");
            System.out.println("3. My Total Vacations");
            System.out.println("4. Pay Bill");
            System.out.println("5. Close");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    register(directoryPath, scanner);
                    break;
                case 2:
                    bookVacation(directoryPath, scanner);
                    break;
                case 3:
                    myTotalVacations();
                    break;
                case 4:
                    payBill();
                    break;
                case 5:
                    System.out.println("Closing the program...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}
