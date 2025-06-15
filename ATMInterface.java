import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

public class ATMInterface {
    private Bank bank;
    private Scanner scanner;
    private Account currentAccount;
    private DecimalFormat currencyFormat;

    public ATMInterface(Bank bank) {
        this.bank = bank;
        this.scanner = new Scanner(System.in);
        this.currencyFormat = new DecimalFormat("$#,##0.00");
    }

    public void start() {
        System.out.println("=".repeat(50));
        System.out.println("    Welcome to " + bank.getBankName() + " ATM");
        System.out.println("=".repeat(50));

        while (true) {
            if (currentAccount == null) {
                if (!login()) {
                    System.out.println("Thank you for using " + bank.getBankName() + " ATM!");
                    break;
                }
            } else {
                if (!showMainMenu()) {
                    logout();
                }
            }
        }

        scanner.close();
    }

    private boolean login() {
        System.out.println("\n--- LOGIN ---");
        System.out.print(
                "Enter Account Number (or 'exit' to quit   Note : Default A/C No :- 1234567890 and ATM Pin :- 1234): ");
        String accountNumber = scanner.nextLine().trim();

        if (accountNumber.equalsIgnoreCase("exit")) {
            return false;
        }

        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine().trim();

        currentAccount = bank.authenticateUser(accountNumber, pin);

        if (currentAccount != null) {
            System.out.println("\nLogin successful!");
            System.out.println("Welcome, " + currentAccount.getAccountHolderName() + "!");
            return true;
        } else {
            System.out.println("Invalid account number or PIN. Please try again.");
            return true; // Continue to allow retry
        }
    }

    private boolean showMainMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("           MAIN MENU");
        System.out.println("=".repeat(40));
        System.out.println("1. Check Balance");
        System.out.println("2. Withdraw Money");
        System.out.println("3. Deposit Money");
        System.out.println("4. Transfer Money");
        System.out.println("5. Transaction History");
        System.out.println("6. Account Information");
        System.out.println("7. Logout");
        System.out.println("8. Exit");
        System.out.print("\nSelect an option (1-8): ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                checkBalance();
                break;
            case "2":
                withdrawMoney();
                break;
            case "3":
                depositMoney();
                break;
            case "4":
                transferMoney();
                break;
            case "5":
                showTransactionHistory();
                break;
            case "6":
                showAccountInfo();
                break;
            case "7":
                return false; // Logout
            case "8":
                System.out.println("Thank you for using " + bank.getBankName() + " ATM!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }

        return true;
    }

    private void checkBalance() {
        System.out.println("\n--- BALANCE INQUIRY ---");
        System.out.println("Account: " + currentAccount.getAccountNumber());
        System.out.println("Current Balance: " + currencyFormat.format(currentAccount.getBalance()));
        pressEnterToContinue();
    }

    private void withdrawMoney() {
        System.out.println("\n--- WITHDRAWAL ---");
        System.out.println("Current Balance: " + currencyFormat.format(currentAccount.getBalance()));
        System.out.print("Enter withdrawal amount: $");

        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());

            if (amount <= 0) {
                System.out.println("Invalid amount. Please enter a positive number.");
                return;
            }

            if (currentAccount.withdraw(amount)) {
                System.out.println("Withdrawal successful!");
                System.out.println("Amount withdrawn: " + currencyFormat.format(amount));
                System.out.println("New balance: " + currencyFormat.format(currentAccount.getBalance()));
            } else {
                System.out.println("Insufficient funds. Your current balance is " +
                        currencyFormat.format(currentAccount.getBalance()));
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount format. Please enter a valid number.");
        }

        pressEnterToContinue();
    }

    private void depositMoney() {
        System.out.println("\n--- DEPOSIT ---");
        System.out.println("Current Balance: " + currencyFormat.format(currentAccount.getBalance()));
        System.out.print("Enter deposit amount: $");

        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());

            if (amount <= 0) {
                System.out.println("Invalid amount. Please enter a positive number.");
                return;
            }

            currentAccount.deposit(amount);
            System.out.println("Deposit successful!");
            System.out.println("Amount deposited: " + currencyFormat.format(amount));
            System.out.println("New balance: " + currencyFormat.format(currentAccount.getBalance()));
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount format. Please enter a valid number.");
        }

        pressEnterToContinue();
    }

    private void transferMoney() {
        System.out.println("\n--- MONEY TRANSFER ---");
        System.out.println("Current Balance: " + currencyFormat.format(currentAccount.getBalance()));
        System.out.print("Enter target account number: ");

        String targetAccountNumber = scanner.nextLine().trim();

        if (targetAccountNumber.equals(currentAccount.getAccountNumber())) {
            System.out.println("Cannot transfer to the same account.");
            pressEnterToContinue();
            return;
        }

        Account targetAccount = bank.getAccount(targetAccountNumber);
        if (targetAccount == null) {
            System.out.println("Target account not found.");
            pressEnterToContinue();
            return;
        }

        System.out.println("Target Account Holder: " + targetAccount.getAccountHolderName());
        System.out.print("Enter transfer amount: $");

        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());

            if (amount <= 0) {
                System.out.println("Invalid amount. Please enter a positive number.");
                return;
            }

            if (currentAccount.transfer(targetAccount, amount)) {
                System.out.println("Transfer successful!");
                System.out.println("Amount transferred: " + currencyFormat.format(amount));
                System.out.println("To: " + targetAccount.getAccountHolderName() +
                        " (" + targetAccountNumber + ")");
                System.out.println("New balance: " + currencyFormat.format(currentAccount.getBalance()));
            } else {
                System.out.println("Transfer failed. Insufficient funds.");
                System.out.println("Your current balance is " +
                        currencyFormat.format(currentAccount.getBalance()));
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount format. Please enter a valid number.");
        }

        pressEnterToContinue();
    }

    private void showTransactionHistory() {
        System.out.println("\n--- TRANSACTION HISTORY ---");
        List<Transaction> transactions = currentAccount.getRecentTransactions(10);

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("Last " + transactions.size() + " transactions:");
            System.out.println("-".repeat(90));
            System.out.printf("%-12s | %-8s | %-25s | %-19s | %s%n",
                    "TYPE", "AMOUNT", "DESCRIPTION", "DATE", "BALANCE AFTER");
            System.out.println("-".repeat(90));

            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }

        pressEnterToContinue();
    }

    private void showAccountInfo() {
        System.out.println("\n--- ACCOUNT INFORMATION ---");
        System.out.println("Account Holder: " + currentAccount.getAccountHolderName());
        System.out.println("Account Number: " + currentAccount.getAccountNumber());
        System.out.println("Current Balance: " + currencyFormat.format(currentAccount.getBalance()));
        System.out.println("Total Transactions: " + currentAccount.getTransactionHistory().size());
        pressEnterToContinue();
    }

    private void logout() {
        System.out.println("\nLogging out...");
        System.out.println("Thank you, " + currentAccount.getAccountHolderName() + "!");
        currentAccount = null;
    }

    private void pressEnterToContinue() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
}
