package model;

import java.util.ArrayList;
import java.util.List;


// Represents a list of account in the system
public class AccountList {

    private List<Account> accounts;

    // EFFECTS: create a list of account
    public AccountList() {
        this.accounts = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: add account to the list
    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    // EFFECTS: return the account with the given username and password
    public Account getAccount(String username, int password) {

        for(Account a: accounts) {
            if(a.getName().equals(username) && a.getPassword() == password) {
                return a;
            }
        }
        return null;
    }


    public int length() {
        return this.accounts.size();
    }

}
