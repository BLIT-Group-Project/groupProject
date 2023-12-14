package helper;

import java.util.Scanner;

public class TakeInput {
    // this is supposed to be a reusable set of methods for taking numbers from the user with simple validation and consume the nextLine.  These throw no exceptions.
    public int takeInt(Scanner scan) {
        int value;
        while(true) {
            if (scan.hasNextInt()) {
                value = scan.nextInt();
                scan.nextLine();
                return value;
            } else {
                scan.nextLine();
                System.out.println("Please enter a number: ");
                continue;
            }
        }
    }

    public double takeDouble(Scanner scan) {
        double value;
        while(true) {
            if (scan.hasNextDouble()) {
                value = scan.nextDouble();
                scan.nextLine();
                return value;
            } else {
                scan.nextLine();
                System.out.println("Please enter a number: ");
                continue;
            }
        }
    }
}
