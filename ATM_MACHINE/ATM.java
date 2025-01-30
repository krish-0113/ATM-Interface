import java.util.*;

public class ATM {
    static Scanner sc = new Scanner(System.in);
    int balance;
    int atmPin;
    long atmNo;
    static int atmCash = 10000;

    ATM() {
        String s = "";

        for (int i = 1; i <= 12; i++) {
            double a = Math.random() * 10d;
            String st = a + "";
            s += st.charAt(0);
        }

        atmNo = Long.parseLong(s);
        balance = 0;
        atmPin = 1234;
        System.out.println("Your ATM pin is " + atmPin);
        System.out.print("Your ATM NO. IS ");
        printATM();
    }

    String checkBalance() {
        return balance + "";
    }

    private void changeAtmPin() {
        int attempts = 3;
        while (attempts > 0) {
            System.out.println("Enter Your new PIN: ");
            int pin = sc.nextInt();
            System.out.println("Confirm Your new PIN: ");
            int confirmPin = sc.nextInt();

            if (pin != confirmPin) {
                System.out.println("Pin does not match! Please try again.");
                attempts--;
            } else {
                this.atmPin = pin;
                System.out.println("PIN changed successfully!");
                break;
            }
        }

        if (attempts == 0) {
            System.out.println("You have reached the maximum limit of attempts!");
        }
    }

    void printATM() {
        String s = this.atmNo + "";
        for (int i = 0; i < 12; i++) {
            if (i % 4 == 0 && i != 0)
                System.out.print(" ");
            System.out.print(s.charAt(i));
        }
        System.out.println();
    }

    void changePin() {
        changeAtmPin();
    }

    private void generatePin() {
        atmPin = 1234;
        System.out.println(
                "Your default pin is " + 1234 + "\nYou can't do any transactions with this pin except changing it.");
    }

    void generatePass() {
        generatePin();
    }

    private void withdrawAmount() {
        System.out.println("Enter the amount to withdraw: ");
        int n = sc.nextInt();
        if (atmCash < n) {
            System.out.println("Sorry, we have insufficient cash.");
            return;
        }
        if (n > this.balance) {
            System.out.println("You don't have sufficient balance.");
        } else {
            this.balance -= n;
            atmCash -= n;
            System.out.println("Collect your cash...");
            System.out.println("Do you want to check your balance? (1 for Yes, any other key for No): ");
            int choice = sc.nextInt();
            if (choice == 1) {
                System.out.println("Your balance is: " + this.balance);
            } else {
                System.out.println("Thank you for visiting the bank. Have a nice day.");
            }
        }
    }

    void withdraw() {
        withdrawAmount();
    }

    private void depositAmount() {
        System.out.print("Enter the amount to deposit: ");
        int amount = sc.nextInt();
        this.balance += amount;
        atmCash += amount;
        System.out.println("Your account is credited with: " + amount);
    }

    void deposit() {
        depositAmount();
    }
}
