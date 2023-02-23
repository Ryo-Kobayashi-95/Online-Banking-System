package model;

import java.util.List;

// Represents an account having a username, password, chequing & saving balance and transaction history.
public class Account {

    private String username;
    private int password;
    private double chequingBalance;
    private double savingBalance;
    private List<TransactionRecord> transactionHistory;

    // REQUIRES: username must be at least one character long AND
    //           password must be 4 digits AND
    //           username and password must be unique, meaning no duplicates in the ListOfAccounts
    // EFFECTS: create an account with username, password,
    //          zero initial balance for chequing & saving, and empty transactionHistory
    public Account(String username, int password) {

    }

    public String getName() {
        return null;
    }

    public int  getPassword() {
        return 0;
    }

    public void setUsername() {
    }

    public void setPassword() {
    }

    public double getChequingBalance() {
        return 0;
    }

    public double getSavingBalance() {
        return 0;
    }

    public List<TransactionRecord> getTransactionHistory() {
        return null;
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: add amount to this chequing balance or saving balance
    public void deposit(String accountType, double amount) {

    }

    // REQUIRES: amount > 0 and amount <= getBalance()
    // MODIFIES: this
    // EFFECTS: subtract amount from this chequing balance or saving balance
    public void withdraw(String accountType, double amount) {

    }

}
