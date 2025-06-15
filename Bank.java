import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<String, Account> accounts;
    private String bankName;
    
    public Bank(String bankName) {
        this.bankName = bankName;
        this.accounts = new HashMap<>();
        initializeSampleAccounts();
    }
    
    private void initializeSampleAccounts() {
        // Create some sample accounts for testing
        accounts.put("1234567890", new Account("1234567890", "1234", "John Doe", 1500.00));
        accounts.put("0987654321", new Account("0987654321", "5678", "Jane Smith", 2500.00));
        accounts.put("1111222233", new Account("1111222233", "9999", "Bob Johnson", 750.00));
    }
    
    public Account authenticateUser(String accountNumber, String pin) {
        Account account = accounts.get(accountNumber);
        if (account != null && account.validatePin(pin)) {
            return account;
        }
        return null;
    }
    
    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }
    
    public boolean accountExists(String accountNumber) {
        return accounts.containsKey(accountNumber);
    }
    
    public String getBankName() {
        return bankName;
    }
    
    public void addAccount(Account account) {
        accounts.put(account.getAccountNumber(), account);
    }
}
