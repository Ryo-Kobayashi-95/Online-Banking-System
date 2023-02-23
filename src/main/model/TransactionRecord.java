package model;

import java.util.Date;

// Represents a transaction record having username, account type (chequing / saving), date
// and amount deposited and/or withdrawn
public class TransactionRecord {

    private Account account;
    private Date date;
    private double transactionAmount;

    // EFFECTS: create a transaction record given date, username, account type and transaction amount
    public TransactionRecord() {

    }
    
}
