package model;

import java.util.ArrayList;
import java.util.List;

// Represents an account having a username, password, chequing & saving balance and transaction history.
public class Account {

    private String username;
    private int password;
    private double chequingBalance;
    private double savingBalance;
    private final List<TransactionRecord> transactionHistory;

    // REQUIRES: username must be at least one character long AND
    //           password must be 4 digits AND
    //           username and password must be unique, meaning no duplicates in the ListOfAccounts
    // EFFECTS: create an account with username, password,
    //          zero initial balance for chequing & saving, and empty transactionHistory
    public Account(String username, int password) {
        this.username = username;
        this.password = password;
        this.chequingBalance = 0;
        this.savingBalance = 0;
        this.transactionHistory = new ArrayList<>();
    }

    public String getName() {
        return this.username;
    }

    public int getPassword() {
        return this.password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public double getChequingBalance() {
        return this.chequingBalance;
    }

    public double getSavingBalance() {
        return this.savingBalance;
    }

    public List<TransactionRecord> getTransactionHistory() {
        return this.transactionHistory;
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: add amount to this chequing balance or saving balance
    public void deposit(String accountType, double amount) {
        if (accountType.equals("cheq")) {
            this.chequingBalance += amount;
            this.transactionHistory.add(new TransactionRecord(this.username, "Chequing",
                    "Deposit", amount));
        } else {
            this.savingBalance += amount;
            this.transactionHistory.add(new TransactionRecord(this.username, "Saving",
                    "Deposit", amount));
        }
    }

    // REQUIRES: amount > 0 and amount <= getBalance()
    // MODIFIES: this
    // EFFECTS: subtract amount from this chequing balance or saving balance
    public void withdraw(String accountType, double amount) {
        if (accountType.equals("cheq")) {
            // handle the insufficient fund
            this.chequingBalance -= amount;
            this.transactionHistory.add(new TransactionRecord(this.username, "Chequing",
                    "Withdraw", -amount));
        } else {
            this.savingBalance -= amount;
            this.transactionHistory.add(new TransactionRecord(this.username, "Saving",
                    "Withdraw", -amount));
        }
    }

}
