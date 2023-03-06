package model;

import java.util.Date;

// Represents a transaction record having username, account type (chequing / saving), date
// and amount deposited and/or withdrawn
public class TransactionRecord {

    private String username;
    private String accountType;
    private String transactionType;
    private Date date;
    private double transactionAmount;

    // EFFECTS: create a transaction record given date, username, account type and transaction amount
    public TransactionRecord(String username, String accountType, String transactionType, double transactionAmount) {
        this.username = username;
        this.accountType = accountType;
        this.transactionType = transactionType;
        this.date = new Date();
        this.transactionAmount = transactionAmount;
    }

    public String getUsername() {
        return this.username;
    }

    public String getAccountType() {
        return this.accountType;
    }

    public String getTransactionType() {
        return this.transactionType;
    }

    public double getTransactionAmount() {
        return this.transactionAmount;
    }

    public Date getDate() {
        return this.date;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setTransactionAmount(double amount) {

        this.transactionAmount = amount;
    }

    public Date setDate() {
        return this.date;
    }



}
