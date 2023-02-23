package model;

import java.util.Date;

// Represents a transaction record having username, account type (chequing / saving), date
// and amount deposited and/or withdrawn
public class TransactionRecord {

    private String username;
    private String accountType;
    private Date date;
    private double transactionAmount;

    // EFFECTS: create a transaction record given date, username, account type and transaction amount
    public TransactionRecord(String username, String accountType, double transactionAmount) {

    }

    public String getUsername() {return null;}

    public String getAccountType() {return null;}

    public double getTransactionAmount() {return 0;}

    public Date getDate() {return null;}

}
