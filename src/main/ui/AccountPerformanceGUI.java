package ui;

import model.Account;
import model.TransactionRecord;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Represents the online banking system's account menu window frame
public class AccountPerformanceGUI extends JFrame implements ActionListener {

    private static JTextField depositField;
    private static JTextField withdrawField;
    private static JLabel userMessageLabel;
    private static JComboBox<String> comboBox;
    private final JFrame previousFrame;
    private final Account account;

    // MODIFIES: this
    // EFFECTS: Constructor sets up all the fields, buttons and instructions for the account menu
    public AccountPerformanceGUI(String name, Account account, JFrame previousFrame) {

        super("Welcome back, " + name + "!");

        this.account = account;
        this.previousFrame = previousFrame;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(700, 400));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());

        createButtonsAndField();
    }

    // MODIFIES: this
    // EFFECTS: create buttons and fields
    private void createButtonsAndField() {
        JButton depositCBtn = setUpButtons("Deposit into chequing account", "depositC");
        JButton depositSBtn = setUpButtons("Deposit into saving account", "depositS");
        JButton withdrawBtn = setUpButtons("Withdraw", "withdraw");
        JButton vcBtn = setUpButtons("Chequing account", "vc");
        JButton vsBtn = setUpButtons("Saving account", "vs");
        JButton vtBtn = setUpButtons("View transaction history", "vt");
        JButton backButton = setUpButtons("Back to the main menu", "bb");

        createDepositLayout(depositCBtn, depositSBtn);
        createWithdrawLayout(withdrawBtn);
        createRecordsLayout(vcBtn, vsBtn, vtBtn);

        add(backButton);

        userMessageLabel = new JLabel("");
        add(userMessageLabel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // EFFECTS: initialise buttons
    private JButton setUpButtons(String buttonName, String key) {
        JButton button = new JButton(buttonName);
        button.setActionCommand(key);
        button.addActionListener(this);
        return button;
    }

    // MODIFIES: this
    // EFFECTS: initialise and render deposit field and button
    private void createDepositLayout(JButton depositCBtn, JButton depositSBtn) {
        depositField = new JTextField(15);
        add(depositField);
        add(depositCBtn);
        add(depositSBtn);
    }

    // MODIFIES: this
    // EFFECTS: initialise and render withdraw field and button
    private void createWithdrawLayout(JButton withdrawBtn) {
        withdrawField = new JTextField(15);
        add(withdrawField);
        add(withdrawBtn);
    }

    // MODIFIES: this
    // EFFECTS: initialise and render account balance and transaction history fields and buttons
    private void createRecordsLayout(JButton vcBtn, JButton vsBtn, JButton vtBtn) {
        JLabel viewBalanceLabel = new JLabel("View account balance");
        add(viewBalanceLabel);
        add(vcBtn);
        add(vsBtn);

        String[] items = {"Select an option", "Show all transactions", "Show chequing transactions",
                "Show saving transactions"};

        comboBox = new JComboBox<>(items);

        add(comboBox);
        add(vtBtn);
    }

    // MODIFIES: this
    // EFFECTS: when the JButton btn is clicked, the related method is performed;
    //          depositC - deposit money into chequing account
    //          depositS - deposit money into saving account
    //          withdraw - withdraw money from chequing account
    //          vc       - render the balance of chequing account
    //          vs       - render the balance of saving account
    //          vt       - render the all the saved transaction history
    //          bb       - go back to the main menu
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("depositC")) {
            depositMoney("c");
        } else if (e.getActionCommand().equals("depositS")) {
            depositMoney("s");
        } else if (e.getActionCommand().equals("withdraw")) {
            withdrawMoney();
        } else if (e.getActionCommand().equals("vc")) {
            displayChequingBalance();
        } else if (e.getActionCommand().equals("vs")) {
            displaySavingBalance();
        } else if (e.getActionCommand().equals("vt")) {
            displayTransactionHistory();
        } else if (e.getActionCommand().equals("bb")) {
            this.dispose();
            previousFrame.setVisible(true);
        }
    }

    // MODIFIES: this
    // EFFECTS: perform a deposit transaction; put the money in to the chequing or saving account,
    //          or ask to re-enter the amount if it is invalid (amount < 0)
    public void depositMoney(String accountType) {

//      TODO: invalid amount needs to be handled

        double depositAmount;
        do {
            depositAmount = Integer.parseInt(depositField.getText());
            if (depositAmount < 0) {
                userMessageLabel.setText("Invalid amount. Please try again");
            }
        } while (depositAmount < 0);

        account.deposit(accountType, depositAmount);
        if (accountType.equals("c")) {
            userMessageLabel.setText("The money has been deposited into your chequing account successfully!");
        } else {
            userMessageLabel.setText("The money has been deposited into your saving account successfully!");
        }
    }

    // MODIFIES: this
    // EFFECTS: perform a withdrawal transaction. Subtract the money from the chequing,
    //          or ask the user to re-enter the amount if amount < 0 or amount > account.getChequingBalance()
    public void withdrawMoney() {
        double withdrawAmount;

//      TODO: Note: you can only withdraw money from your chequing account at this time
//      TODO: invalid amount needs to be handled

        do {
            withdrawAmount = Integer.parseInt(withdrawField.getText());
            if (withdrawAmount > account.getChequingBalance()) {
                userMessageLabel.setText("Insufficient balance in your chequing account. Please try again");
            } else if (withdrawAmount < 0) {
                userMessageLabel.setText("Invalid amount. Please try again");
            }
        } while (withdrawAmount < 0 || withdrawAmount > account.getChequingBalance());

        account.withdraw("c", withdrawAmount);

        double balance = account.getChequingBalance();
        userMessageLabel.setText("Balance in your chequing account: $" + balance);
    }

    // MODIFIES: this
    // EFFECTS: display the balance in the chequing account
    public void displayChequingBalance() {
        double balance = account.getChequingBalance();
        userMessageLabel.setText("Balance in your chequing account: $" + balance);
    }

    // MODIFIES: this
    // EFFECTS: display the balance in the saving account
    public void displaySavingBalance() {
        double balance = account.getSavingBalance();
        userMessageLabel.setText("Balance in your saving account: $" + balance);
    }

    // MODIFIES: this
    // EFFECTS: display the transaction history of this account
    public void displayTransactionHistory() {
        List<TransactionRecord> records = account.getTransactionHistory();
        String selectedOption = (String) comboBox.getSelectedItem();

        if (records.isEmpty()) {
            userMessageLabel.setText("No transaction history on this account");
        } else {
            switch (Objects.requireNonNull(selectedOption)) {
                case "Select an option":
                    userMessageLabel.setText("Please select an option.");
                    break;
                case "Show all transactions":
                    userMessageLabel.setText(" ");
                    displayAllTransactionHistory(records);
                    break;
                case "Show chequing transactions":
                    userMessageLabel.setText(" ");
                    displaySpecificAccountTransactionHistory(records, "Chequing");
                    break;
                case "Show saving transactions":
                    userMessageLabel.setText(" ");
                    displaySpecificAccountTransactionHistory(records, "Saving");
                    break;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: display the transaction history of the given account type; chequing or saving
    private void displaySpecificAccountTransactionHistory(List<TransactionRecord> records, String account) {
        List<TransactionRecord> accountRecords = new ArrayList<>();

        JFrame accountHistoryFrame = new JFrame("Account Page");
        accountHistoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        accountHistoryFrame.setPreferredSize(new Dimension(700, 400));
        ((JPanel) accountHistoryFrame.getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        accountHistoryFrame.setLayout(new FlowLayout());

        for (TransactionRecord tr : records) {
            if (tr.getAccountType().equals(account)) {

                accountRecords.add(tr);
            }
        }
        renderTransactionHistory(accountRecords, accountHistoryFrame);
    }

    // MODIFIES: this
    // EFFECTS: render all the transaction history of the given specific account type; chequing or saving
    private void renderTransactionHistory(List<TransactionRecord> accountRecords, JFrame accountHistoryFrame) {
        for (TransactionRecord tr : accountRecords) {
            JLabel date = new JLabel("Transaction date: " + tr.getDate());
            JLabel username = new JLabel("Username:         " + tr.getUsername());
            JLabel accountType = new JLabel("Account type:     " + tr.getAccountType());
            JLabel transactionType = new JLabel("Transaction Type: " + tr.getTransactionType());
            JLabel amount = new JLabel("Amount:           $" + tr.getTransactionAmount());

            accountHistoryFrame.add(date);
            accountHistoryFrame.add(username);
            accountHistoryFrame.add(accountType);
            accountHistoryFrame.add(transactionType);
            accountHistoryFrame.add(amount);

            accountHistoryFrame.pack();
            accountHistoryFrame.setLocationRelativeTo(null);
            accountHistoryFrame.setVisible(true);
            accountHistoryFrame.setResizable(false);
        }
    }

    // MODIFIES: this
    // EFFECTS: render all the transaction history associated with the account
    private void displayAllTransactionHistory(List<TransactionRecord> records) {
        JFrame allHistoryFrame = new JFrame("Account Page");
        allHistoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        allHistoryFrame.setPreferredSize(new Dimension(700, 400));
        ((JPanel) allHistoryFrame.getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        allHistoryFrame.setLayout(new FlowLayout());

        renderTransactionHistory(records, allHistoryFrame);
    }
}
