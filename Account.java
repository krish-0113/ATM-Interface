import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {
    private String accountNumber;
    private String pin;
    private double balance;
    private String accountHolderName;
    private List<Transaction> transactionHistory;
    
    public Account(String accountNumber, String pin, String accountHolderName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
        
        // Add initial deposit transaction
        if (initialBalance > 0) {
            addTransaction("DEPOSIT", initialBalance, "Initial deposit");
        }
    }
    
    public boolean validatePin(String inputPin) {
        return this.pin.equals(inputPin);
    }
    
    public double getBalance() {
        return balance;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public String getAccountHolderName() {
        return accountHolderName;
    }
    
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            return false;
        }
        if (balance >= amount) {
            balance -= amount;
            addTransaction("WITHDRAWAL", amount, "ATM withdrawal");
            return true;
        }
        return false;
    }
    
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            addTransaction("DEPOSIT", amount, "ATM deposit");
        }
    }
    
    public boolean transfer(Account targetAccount, double amount) {
        if (amount <= 0 || balance < amount) {
            return false;
        }
        
        balance -= amount;
        targetAccount.balance += amount;
        
        addTransaction("TRANSFER_OUT", amount, "Transfer to " + targetAccount.getAccountNumber());
        targetAccount.addTransaction("TRANSFER_IN", amount, "Transfer from " + this.accountNumber);
        
        return true;
    }
    
    private void addTransaction(String type, double amount, String description) {
        Transaction transaction = new Transaction(type, amount, description, balance);
        transactionHistory.add(transaction);
    }
    
    public List<Transaction> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }
    
    public List<Transaction> getRecentTransactions(int count) {
        List<Transaction> recent = new ArrayList<>();
        int start = Math.max(0, transactionHistory.size() - count);
        for (int i = start; i < transactionHistory.size(); i++) {
            recent.add(transactionHistory.get(i));
        }
        return recent;
    }
}
