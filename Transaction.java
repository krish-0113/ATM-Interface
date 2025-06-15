import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
    private String type;
    private double amount;
    private String description;
    private Date timestamp;
    private double balanceAfter;
    
    public Transaction(String type, double amount, String description, double balanceAfter) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.timestamp = new Date();
        this.balanceAfter = balanceAfter;
    }
    
    public String getType() {
        return type;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public String getDescription() {
        return description;
    }
    
    public Date getTimestamp() {
        return timestamp;
    }
    
    public double getBalanceAfter() {
        return balanceAfter;
    }
    
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return String.format("%-12s | $%8.2f | %-25s | %s | Balance: $%.2f",
                type, amount, description, sdf.format(timestamp), balanceAfter);
    }
}
