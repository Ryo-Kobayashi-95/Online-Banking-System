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
    private static JLabel errorMessageLabel;
    private static JComboBox<String> comboBoxHistory;
    private static JComboBox<String> comboBoxDeposit;
    private final JFrame previousFrame;
    private final Account account;
    private final String name;

    private static final int WINDOW_WIDTH = 1000;

    // MODIFIES: this
    // EFFECTS: Constructor sets up all the fields, buttons and instructions for the account menu
    public AccountPerformanceGUI(String name, Account account, JFrame previousFrame) {

        super("Account Menu");

        this.account = account;
        this.previousFrame = previousFrame;
        this.name = name;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WINDOW_WIDTH, 400));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());

        createButtonsAndField();
    }

    // MODIFIES: this
    // EFFECTS: create buttons and fields
    private void createButtonsAndField() {
        JButton depositBtn = setUpButtons("Deposit", "deposit");
        JButton withdrawBtn = setUpButtons("Withdraw", "withdraw");
        JButton vcBtn = setUpButtons("Chequing account", "vc");
        JButton vsBtn = setUpButtons("Saving account", "vs");
        JButton vtBtn = setUpButtons("View transaction history", "vt");
        JButton backButton = setUpButtons("Back to the main menu", "bb");

        JLabel greeting = new JLabel("Welcome back, " + name + "!" + " How can I help you today?");

        add(greeting);

        createDepositLayout(depositBtn);

        JLabel withdrawConstraintMessage = new JLabel("Note: you can only withdraw from your "
                + "chequing account at this time");
        add(withdrawConstraintMessage);
        createWithdrawLayout(withdrawBtn);
        createRecordsLayout(vcBtn, vsBtn, vtBtn);

        add(backButton);

        createMessage();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // MODIFIES: this
    // EFFECTS: insert user message for successful user command, and error message for erroneous user command
    private void createMessage() {
        userMessageLabel = new JLabel("");
        add(userMessageLabel);

        errorMessageLabel = new JLabel("");
        errorMessageLabel.setForeground(Color.RED);
        add(errorMessageLabel);
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
    private void createDepositLayout(JButton depositBtn) {
        depositField = new JTextField(15);

        String[] items = {"Select the account", "Chequing account", "Saving account"};

        comboBoxDeposit = new JComboBox<>(items);

        add(depositField);
        add(comboBoxDeposit);
        add(depositBtn);
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

        comboBoxHistory = new JComboBox<>(items);

        add(comboBoxHistory);
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

        if (e.getActionCommand().equals("deposit")) {
            accountSelectionForDepositFromDropdownBox();
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
    // EFFECTS: perform deposit based on the selected account from the dropdown box
    public void accountSelectionForDepositFromDropdownBox() {
        String selectedOption = (String) comboBoxDeposit.getSelectedItem();
        switch (Objects.requireNonNull(selectedOption)) {
            case "Select the account":
                userMessageLabel.setText("");
                errorMessageLabel.setText("Please select the account");
                break;
            case "Chequing account":
                userMessageLabel.setText(" ");
                errorMessageLabel.setText(" ");
                depositMoney("c");
                break;
            case "Saving account":
                userMessageLabel.setText(" ");
                errorMessageLabel.setText(" ");
                depositMoney("s");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: perform a deposit transaction; put the money in to the chequing or saving account,
    //          or ask to re-enter the amount if it is invalid (amount < 0)
    public void depositMoney(String accountType) {

//      TODO: invalid amount needs to be handled

        double depositAmount = Integer.parseInt(depositField.getText());
        if (depositAmount < 0) {
            userMessageLabel.setText("");
            errorMessageLabel.setText("Invalid amount. Please try again");
        } else {
            account.deposit(accountType, depositAmount);
            if (accountType.equals("c")) {
                errorMessageLabel.setText("");
                userMessageLabel.setText("The money has been deposited into your chequing account successfully!"
                        + " The balance in your chequing account is $" + account.getChequingBalance());
            } else {
                errorMessageLabel.setText("");
                userMessageLabel.setText("The money has been deposited into your saving account successfully!"
                        + " The balance in your saving account is $" + account.getSavingBalance());
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: perform a withdrawal transaction. Subtract the money from the chequing,
    //          or ask the user to re-enter the amount if amount < 0 or amount > account.getChequingBalance()
    public void withdrawMoney() {

        double withdrawAmount = Integer.parseInt(withdrawField.getText());
        if (withdrawAmount > account.getChequingBalance()) {
            userMessageLabel.setText("");
            errorMessageLabel.setText("Insufficient balance in your chequing account. Please try again");
        } else if (withdrawAmount < 0) {
            userMessageLabel.setText("");
            errorMessageLabel.setText("Invalid amount. Please try again");
        } else {
            account.withdraw("c", withdrawAmount);

            double balance = account.getChequingBalance();
            errorMessageLabel.setText("");
            userMessageLabel.setText("Balance in your chequing account: $" + balance);
        }
    }

    // MODIFIES: this
    // EFFECTS: display the balance in the chequing account
    public void displayChequingBalance() {
        double balance = account.getChequingBalance();
        errorMessageLabel.setText(" ");
        userMessageLabel.setText("Balance in your chequing account: $" + balance);
    }

    // MODIFIES: this
    // EFFECTS: display the balance in the saving account
    public void displaySavingBalance() {
        double balance = account.getSavingBalance();
        errorMessageLabel.setText(" ");
        userMessageLabel.setText("Balance in your saving account: $" + balance);
    }

    // MODIFIES: this
    // EFFECTS: display the transaction history of this account
    public void displayTransactionHistory() {
        List<TransactionRecord> records = account.getTransactionHistory();
        String selectedOption = (String) comboBoxHistory.getSelectedItem();

        if (records.isEmpty()) {
            userMessageLabel.setText("");
            errorMessageLabel.setText("No transaction history on this account");
        } else {
            accountSelectionForHistoryFromDropdownBox(records, selectedOption);
        }
    }

    // MODIFIES: this
    // EFFECTS: display the transaction history based on the selected account from the dropdown box
    private void accountSelectionForHistoryFromDropdownBox(List<TransactionRecord> records, String selectedOption) {
        switch (Objects.requireNonNull(selectedOption)) {
            case "Select an option":
                userMessageLabel.setText("");
                errorMessageLabel.setText("Please select an option.");
                break;
            case "Show all transactions":
                userMessageLabel.setText(" ");
                errorMessageLabel.setText(" ");
                displayAllTransactionHistory(records);
                break;
            case "Show chequing transactions":
                userMessageLabel.setText(" ");
                errorMessageLabel.setText(" ");
                displaySpecificAccountTransactionHistory(records, "Chequing");
                break;
            case "Show saving transactions":
                userMessageLabel.setText(" ");
                errorMessageLabel.setText(" ");
                displaySpecificAccountTransactionHistory(records, "Saving");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: display the transaction history of the given account type; chequing or saving
    private void displaySpecificAccountTransactionHistory(List<TransactionRecord> records, String account) {
        List<TransactionRecord> accountRecords = new ArrayList<>();

        JFrame accountHistoryFrame = new JFrame("Transaction history of your "
                + account.toLowerCase() + " account");
        accountHistoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        accountHistoryFrame.setPreferredSize(new Dimension(WINDOW_WIDTH, 400));
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
        JFrame allHistoryFrame = new JFrame("Transaction history of your chequing and saving account");
        allHistoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        allHistoryFrame.setPreferredSize(new Dimension(WINDOW_WIDTH, 400));
        ((JPanel) allHistoryFrame.getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        allHistoryFrame.setLayout(new FlowLayout());

        renderTransactionHistory(records, allHistoryFrame);
    }
}
