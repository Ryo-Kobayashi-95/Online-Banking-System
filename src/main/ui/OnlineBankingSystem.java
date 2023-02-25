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
        System.out.println("\nWelcome to Bank of UBC!");
        runSystem();
    }

    // MODIFIES: this
    // EFFECTS: run the main menu
    public void runSystem() {
        init();
        processMainRequest();
    }

    // MODIFIES: this
    // EFFECTS: initialize a new account list
    public void init() {
        this.list = new AccountList();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // MODIFIES: this
    // EFFECTS: process the user input in the main menu
    public void processMainRequest() {
        boolean keepGoing = true;
        String request;

        while (keepGoing) {
            renderMainOptions();
            request = input.next();
            request = request.toLowerCase();

            if (request.equals("q")) {
                keepGoing = false;
                System.out.println("\nThank you for using Bank of UBC! See you soon...");
            } else {
                performMainRequest(request);
            }
        }
    }

    // EFFECTS: render the user options in the main menu
    public void renderMainOptions() {
        System.out.println("\nYou are at the main menu");
        System.out.println("\nPlease select and type a key from the following options:");
        System.out.println("- 'a' to create a new account");
        System.out.println("- 'l' to log-in to your account");
        System.out.println("- 'q' to quit");
    }

    // MODIFIES: this
    // EFFECTS: perform the user request in the main menu
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
    // EFFECTS: create a new account. Ask the user to re-enter if the password length is not 4
    public void createAccount() {
        System.out.println("\nSet your username and password to create an account");
        System.out.println("Username:");
        String name = input.next();
        System.out.println("Password (must be in 4 digits):");
        int pass = input.nextInt();
        String passStr = Integer.toString(pass);
        int size = passStr.length();

        while (size != 4) {
            System.out.println("Invalid password. Password must be 4 digits long. Please try again.");
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
    // EFFECTS: login to the existing account
    public void loginToAccount() {
        System.out.println("\nEnter your username and password");
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

    // EFFECTS: handle a situation where given username and/or password not found in the account list.
    //          ask to re-enter or go back to the main menu
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

    // MODIFIES: this
    // EFFECTS: process the user input in the account menu
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

    // EFFECTS: render the user options in the account menu
    public void renderAccountOptions() {
        System.out.println("\nPlease select and type a key from the following options:");
        System.out.println("- 'd'  to deposit money");
        System.out.println("- 'w'  to withdraw money");
        System.out.println("- 'vc' to view the chequing balance");
        System.out.println("- 'vs' to view the saving balance");
        System.out.println("- 'h'  to view your transaction history");
        System.out.println("- 'b'  to go back to the main menu");
    }

    // MODIFIES: this
    // EFFECTS: perform the user request in the account menu
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

        System.out.println("\nChoose the account:");
        System.out.println("- 'c' for chequing");
        System.out.println("- 's' for saving");
        String accountType = input.next();

        if (accountType.equals("c") || accountType.equals("s")) {
            doDeposit(account, accountType);
        } else {
            System.out.println("\nInvalid request, please try again");
            depositMoney(account);
        }
    }

    // MODIFIES: this
    // EFFECTS: put the money in to the chequing or saving account, or ask to re-enter the
    //          amount if it is invalid (amount < 0)
    public void doDeposit(Account account, String accountType) {
        double amount;

        do {
            System.out.println("Enter the amount");
            amount = input.nextInt();
            if (amount < 0) {
                System.out.println("\nInvalid amount. Please try again\n");
            }
        } while (amount < 0);

        account.deposit(accountType, amount);
        System.out.println("\nThe transaction has been done successfully!");
        processAccountRequest(account);
    }

    // MODIFIES: this
    // EFFECTS: perform a withdrawal transaction. Subtract the money from the chequing,
    //          or ask the user to re-enter the amount if amount < 0 or amount > account.getChequingBalance()
    public void withdrawMoney(Account account) {
        double amount;

        System.out.println("\nNote: you can only withdraw money from your chequing account at this time");

        do {
            System.out.println("Enter the amount");
            amount = input.nextInt();
            if (amount > account.getChequingBalance()) {
                System.out.println("\nInsufficient balance in your chequing account. Please try again\n");
            } else if (amount < 0) {
                System.out.println("\nInvalid amount. Please try again\n");
            }
        } while (amount < 0 || amount > account.getChequingBalance());
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
                System.out.println("Username:         " + tr.getUsername());
                System.out.println("Account type:     " + tr.getAccountType());
                System.out.println("Transaction Type: " + tr.getTransactionType());
                System.out.println("Amount:           $" + tr.getTransactionAmount());
            }
        }
        processAccountRequest(account);
    }
}
