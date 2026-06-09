import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Please enter the first number:");
            int num1 = Integer.parseInt(sc.nextLine());
            System.out.println("You have entered: " + num1);

            System.out.println("Please enter the second number:");
            int num2 = Integer.parseInt(sc.nextLine());
            System.out.println("You have entered: " + num2);

            System.out.println("Please enter the file path:");
            String filePath = sc.nextLine();
            File file = new File(filePath);

            // Try to read the file
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                System.out.println("File contents:");
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                System.out.println("Error reading the file: " + e.getMessage());
            }

            System.out.println("Sum of numbers: " + (num1 + num2));

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please enter valid integers.");
        } finally {
            sc.close();
        }
    }
}
