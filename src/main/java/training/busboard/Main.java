package training.busboard;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a postcode: ");
        String postcode = scanner.nextLine();
        new TflApiHelper().printAllStopDisplays(postcode);
    }
}