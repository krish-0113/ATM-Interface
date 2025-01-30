import java.util.*;


class Main{

    public static void main(String[] args) {
        Map<Long, ATM> accountMap = new HashMap<>();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("1. Generate New ATM Card \n" + "2. Use Your ATM Card \n" + "3. Exit");
            int choice = sc.nextInt();

            if (choice == 1) {
                ATM atm = new ATM();
                accountMap.put(atm.atmNo, atm);

            } else if (choice == 2) {
                int attempts = 3;
                ATM user = null;

                while (attempts-- > 0) {
                    System.out.println("\nEnter Your ATM No.: ");
                    long no = sc.nextLong();

                    if (!accountMap.containsKey(no)) {
                        System.out.println(
                                "Your ATM No. is incorrect. Please try again. You have " + attempts + " attempts left.");
                    } else {
                        System.out.println("Successfully Logged In");
                        user = accountMap.get(no);
                        break;
                    }
                }

                if (attempts <= 0) {
                    System.out.println("You have attempted multiple times. Please try again later.");
                    continue;
                }

                attempts = 3;
                while (attempts-- > 0) {
                    System.out.println("Now enter Your ATM PIN:");
                    int pin = sc.nextInt();

                    if (pin == 1234) {
                        System.out.println("Please generate a new ATM PIN. You cannot access your account with this PIN.");
                        user.generatePass();
                        break;
                    }

                    if (pin != user.atmPin) {
                        System.out.println("You have entered the wrong PIN. You have " + attempts + " attempts left.");
                    } else {
                        while (true) {
                            System.out.println("1. Check Balance \n" + "2. Change ATM Pin \n"
                                    + "3. Withdraw Amount \n" + "4. Deposit Amount \n" + "5. Exit");
                            int s = sc.nextInt();

                            if (s == 1) {
                                System.out.println("Account balance is: " + user.checkBalance());
                            } else if (s == 2) {
                                user.changePin();
                            } else if (s == 3) {
                                user.withdraw();
                            } else if (s == 4) {
                                user.deposit();
                            } else if (s == 5) {
                                System.out.println("Thank you for visiting!");
                                break;
                            }
                        }
                        break;
                    }
                }

                if (attempts <= 0) {
                    System.out.println("You have attempted multiple times. Please try tomorrow. Thank you.");
                }

            } else if (choice == 3) {
                System.out.println("Thank you for visiting!");
                break;
            } else {
                System.out.println("Option is not listed. Please choose a listed option.");
            }
        }

        sc.close();
    }
}