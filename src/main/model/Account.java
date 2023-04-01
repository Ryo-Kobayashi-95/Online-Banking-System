package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Citation:
// Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// Represents an account having a username, password, chequing & saving balance and transaction history.
public class Account implements Writable {

    private String username;
    private String password;
    private double chequingBalance;
    private double savingBalance;
    private final List<TransactionRecord> transactionHistory;
    private Date date;

    // REQUIRES: username must be at least one character long AND
    //           username and password must be unique, meaning no duplicates in the ListOfAccounts
    // EFFECTS: create an account with username, password,
    //          zero initial balance for chequing & saving, and empty transactionHistory
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.chequingBalance = 0;
        this.savingBalance = 0;
        this.transactionHistory = new ArrayList<>();
    }

    public String getName() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
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

    public void setChequingBalance(double amount) {
        this.chequingBalance = amount;
    }

    public void setSavingBalance(double amount) {
        this.savingBalance = amount;
    }

    // MODIFIES: this
    // EFFECTS: add amount to this chequing balance or saving balance
    public void deposit(String accountType, double amount) {
        date = new Date();
        String dateStr = date.toString();
        if (accountType.equals("c")) {
            this.chequingBalance += amount;
            this.transactionHistory.add(new TransactionRecord(this.username, "Chequing",
                    "Deposit", dateStr, amount));
        } else {
            this.savingBalance += amount;
            this.transactionHistory.add(new TransactionRecord(this.username, "Saving",
                    "Deposit", dateStr, amount));
        }
    }

    // MODIFIES: this
    // EFFECTS: subtract amount from this chequing balance or saving balance
    public void withdraw(String accountType, double amount) {
        date = new Date();
        String dateStr = date.toString();
        if (accountType.equals("c")) {
            this.chequingBalance -= amount;
            this.transactionHistory.add(new TransactionRecord(this.username, "Chequing",
                    "Withdraw", dateStr, -amount));
        } else {
            this.savingBalance -= amount;
            this.transactionHistory.add(new TransactionRecord(this.username, "Saving",
                    "Withdraw", dateStr, -amount));
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("username", this.username);
        json.put("password", this.password);
        json.put("chequingBalance", this.chequingBalance);
        json.put("savingBalance", this.savingBalance);

        JSONArray jsonArray = new JSONArray();
        for (TransactionRecord tr : transactionHistory) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", tr.getUsername());
            jsonObject.put("accountType", tr.getAccountType());
            jsonObject.put("transactionType", tr.getTransactionType());
            jsonObject.put("transactionAmount", tr.getTransactionAmount());
            jsonObject.put("date", tr.getDate());
            jsonArray.put(jsonObject);
        }

        json.put("transactionHistory", jsonArray);

        return json;
    }

}
