import java.io.*;
import java.util.*;

public class Practise {

    static final String FILE_NAME = "students.txt";
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- Student Record Management ---");
            System.out.println("1. Add Student");
            System.out.println("2. Read All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> readStudents();
                case 3 -> searchStudent();
                case 4 -> updateStudent();
                case 5 -> {
                    System.out.println("Program closed.");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    // Add a new student
    static void addStudent() {
        System.out.print("Enter ID: ");
        String id = sc.nextLine();

        if (findStudent(id) != null) {
            System.out.println("Student ID already exists!");
            return;
        }

        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Enter age: ");
        int age = Integer.parseInt(sc.nextLine());

        System.out.print("Enter marks: ");
        double marks = Double.parseDouble(sc.nextLine());

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(FILE_NAME, true))) {

            bw.write(id + "," + name + "," + age + "," + marks);
            bw.newLine();

            System.out.println("Student added successfully!");

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Read all student records
    static void readStudents() {
        try (BufferedReader br = new BufferedReader(
                new FileReader(FILE_NAME))) {

            String line;
            boolean found = false;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
                found = true;
            }

            if (!found) {
                System.out.println("No student records found.");
            }

        } catch (IOException e) {
            System.out.println("No records available yet.");
        }
    }

    // Search a student by ID
    static void searchStudent() {
        System.out.print("Enter student ID to search: ");
        String id = sc.nextLine();

        String record = findStudent(id);

        if (record != null) {
            System.out.println("Student found: " + record);
        } else {
            System.out.println("Student not found.");
        }
    }

    // Find a student record by ID
    static String findStudent(String id) {
        try (BufferedReader br = new BufferedReader(
                new FileReader(FILE_NAME))) {

            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);

                if (data.length == 4 && data[0].equals(id)) {
                    return line;
                }
            }

        } catch (IOException e) {
            // File may not exist yet.
        }

        return null;
    }

    // Update an existing student's details
    static void updateStudent() {
        System.out.print("Enter student ID to update: ");
        String id = sc.nextLine();

        List<String> records = new ArrayList<>();
        boolean updated = false;

        try (BufferedReader br = new BufferedReader(
                new FileReader(FILE_NAME))) {

            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);

                if (data.length == 4 && data[0].equals(id)) {
                    System.out.print("Enter new name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter new age: ");
                    int age = Integer.parseInt(sc.nextLine());

                    System.out.print("Enter new marks: ");
                    double marks = Double.parseDouble(sc.nextLine());

                    line = id + "," + name + "," + age + "," + marks;
                    updated = true;
                }

                records.add(line);
            }

        } catch (IOException e) {
            System.out.println("Unable to read student file.");
            return;
        }

        if (!updated) {
            System.out.println("Student not found.");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(FILE_NAME, false))) {

            for (String record : records) {
                bw.write(record);
                bw.newLine();
            }

            System.out.println("Student updated successfully!");

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
