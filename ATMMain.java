public class ATMMain {
    public static void main(String[] args) {
        // Create a bank instance
        Bank bank = new Bank("SecureBank");
        
        // Create and start the ATM interface
        ATMInterface atm = new ATMInterface(bank);
        atm.start();
    }
}
