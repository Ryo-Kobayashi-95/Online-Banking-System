package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Citation:
// Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// Represents bank with a list of all accounts in the system
public class Bank implements Writable {

    private List<Account> accounts;

    // EFFECTS: create a list of account
    public Bank() {
        this.accounts = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: add account to the list
    public void addAccount(Account account) {
        this.accounts.add(account);
        EventLog.getInstance().logEvent(new Event("Account added to the list"));
    }

    // EFFECTS: return the account with the given username and password
    public Account getAccount(String username, String password) {

        for (Account a: accounts) {
            if (a.getName().equals(username) && a.getPassword().equals(password)) {
                return a;
            }
        }
        return null;
    }

    // EFFECTS: return the length of the account list
    public int length() {
        return this.accounts.size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json  = new JSONObject();
        json.put("accounts", accountsToJson());
        return json;
    }

    // EFFECTS: returns account list in this bank as a JSON array
    private JSONArray accountsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Account a : accounts) {
            jsonArray.put(a.toJson());
        }

        return jsonArray;
    }

}
