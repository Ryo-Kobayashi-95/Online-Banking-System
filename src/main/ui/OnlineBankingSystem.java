package ui;

import model.Account;
import model.AccountList;
import model.TransactionRecord;

import java.util.List;
import java.util.Scanner;

// Online banking system
public class OnlineBankingSystem {

    private Scanner input;
    private AccountList list;

    // EFFECTS: run the online banking system
    public OnlineBankingSystem() {
        System.out.println("\nWelcome!");
        runSystem();
    }

    // MODIFIES: this
    // EFFECTS: process
    public void runSystem() {
        init();
        processMainRequest();
    }

    // MODIFIES: this
    // EFFECTS: initialize a new account
    public void init() {
        this.list = new AccountList();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    public void processMainRequest() {
        boolean keepGoing = true;
        String request;

        while (keepGoing) {
            renderMainOptions();
            request = input.next();
            request = request.toLowerCase();

            if (request.equals("q")) {
                keepGoing = false;
                System.out.println("\nThank you for using our bank! See you soon...");
            } else {
                performMainRequest(request);
            }
        }
    }

    // EFFECTS: render the user options
    public void renderMainOptions() {
        System.out.println("\nYou are at the main menu");
        System.out.println("\nPlease see the following options:");
        System.out.println("- Please type 'a' to create a new account");
        System.out.println("- Please type 'l' to log-in to your account");
        System.out.println("- Please type 'q' to quit");
    }

    public void performMainRequest(String request) {
        if (request.equals("a")) {
            createAccount();
        } else if (request.equals("l")) {
            loginToAccount();
        } else {
            System.out.println("\nInvalid request, please try again");
        }
    }

    // MODIFIES: this
    // EFFECTS: create a new account
    public void createAccount() {
        System.out.println("\nPlease set your username and password to create an account");
        System.out.println("Username:");
        String name = input.next();
        System.out.println("Password (must be in 4 digits):");
        int pass = input.nextInt();
        String passStr = Integer.toString(pass);
        int size = passStr.length();

        while (size != 4) {
            System.out.println("Invalid password. Password must be 4 digits long");
            pass = input.nextInt();
            passStr = Integer.toString(pass);
            size = passStr.length();
        }

        Account account = new Account(name, pass);
        list.addAccount(account);

        System.out.println("\nYou are all set!");

        processAccountRequest(account);
    }

    // MODIFIES: this
    // EFFECTS: login the existing account
    public void loginToAccount() {
        System.out.println("\nPlease enter your username and password to log in to your account");
        System.out.println("Username:");
        String name = input.next();
        System.out.println("Password (must be in 4 digits):");
        int pass = input.nextInt();

        Account account = list.getAccount(name, pass);
        if (account == null) {
            noAccountHandler();
        } else {
            processAccountRequest(account);
        }
    }

    public void noAccountHandler() {
        System.out.println("There is no account with the given username and/or password." + "\n"
                + "Please type 't' to try again, or 'b' to go back to the main menu");
        String request = input.next();

        boolean keepGoing = true;
        while (keepGoing) {
            if (request.equals("t")) {
                keepGoing = false;
                loginToAccount();
            } else if (request.equals("b")) {
                keepGoing = false;
            } else {
                System.out.println("\nInvalid request, please try again");
            }
        }
    }

    public void processAccountRequest(Account account) {
        boolean keepGoing = true;
        String request;

        while (keepGoing) {
            renderAccountOptions();
            request = input.next();
            request = request.toLowerCase();

            if (request.equals("b")) {
                keepGoing = false;
            } else if (request.equals("d") || request.equals("w") || request.equals("vc")
                    || request.equals("vs") || request.equals("h")) {
                keepGoing = false;
                performAccountRequest(request, account);
            } else {
                System.out.println("\nInvalid request, please try again");
            }
        }
    }

    public void renderAccountOptions() {
        System.out.println("\nPlease see the following options:");
        System.out.println("- Please enter 'd' to deposit money into your account");
        System.out.println("- Please enter 'w' to withdraw money from your account");
        System.out.println("- Please enter 'vc' to view the balance in your chequing account");
        System.out.println("- Please enter 'vs' to view the balance in your saving account");
        System.out.println("- Please enter 'h' to view your transaction history");
        System.out.println("- Please enter 'b' to go back to the main");
    }

    public void performAccountRequest(String request, Account account) {
        boolean keepGoing = true;

        while (keepGoing) {
            if (request.equals("d")) {
                keepGoing = false;
                depositMoney(account);
            } else if (request.equals("w")) {
                keepGoing = false;
                withdrawMoney(account);
            } else if (request.equals("vc")) {
                keepGoing = false;
                displayChequingBalance(account);
            } else if (request.equals("vs")) {
                keepGoing = false;
                displaySavingBalance(account);
            } else if (request.equals("h")) {
                keepGoing = false;
                displayTransactionHistory(account);
            } else if (request.equals("b")) {
                keepGoing = false;
            } else {
                System.out.println("\nInvalid request, please try again");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: perform a deposit transaction
    public void depositMoney(Account account) {
        System.out.println("\nPlease choose the account:");
        System.out.println("- Please enter 'c' for chequing account");
        System.out.println("- Please enter 's' for saving account");

        String accountType = input.next();
        if (accountType.equals("c")) {
            System.out.println("Please enter the amount");
            double amount = input.nextInt();
            account.deposit("cheq", amount);
            System.out.println("\nThe transaction has been done successfully!");
            processAccountRequest(account);
        } else if (accountType.equals("s")) {
            System.out.println("Please enter the amount");
            double amount = input.nextInt();
            account.deposit("sav", amount);
            System.out.println("\nThe transaction has been done successfully!");
            processAccountRequest(account);
        } else {
            System.out.println("\nInvalid request, please try again");
            depositMoney(account);
        }
    }

    // MODIFIES: this
    // EFFECTS: perform a withdrawal transaction
    public void withdrawMoney(Account account) {
        System.out.println("\nNote: you can only withdraw money from your chequing account at this time");
        System.out.println("Please enter the amount");

        double amount = input.nextInt();
        account.withdraw("cheq", amount);

        System.out.println("\nThe transaction has been done successfully!");

        processAccountRequest(account);
    }

    // EFFECTS: display the balance in the chequing account
    public void displayChequingBalance(Account account) {
        double balance = account.getChequingBalance();
        System.out.println("\nBalance in your chequing account: $" + balance);

        processAccountRequest(account);
    }

    // EFFECTS: display the balance in the saving account
    public void displaySavingBalance(Account account) {
        double balance = account.getSavingBalance();
        System.out.println("\nBalance in your saving account: $" + balance);

        processAccountRequest(account);
    }

    // EFFECTS: display the transaction history of this account
    public void displayTransactionHistory(Account account) {
        List<TransactionRecord> records = account.getTransactionHistory();

        if (records.isEmpty()) {
            System.out.println("\nNo transaction history on this account");
        } else {
            for (TransactionRecord tr : records) {
                System.out.println("\n" + "Transaction date: " + tr.getDate());
                System.out.println("Username: " + tr.getUsername());
                System.out.println("Account type: " + tr.getAccountType());
                System.out.println("Transaction Type: " + tr.getTransactionType());
                System.out.println("Amount: $" + tr.getTransactionAmount());
            }
        }
        processAccountRequest(account);
    }
}
