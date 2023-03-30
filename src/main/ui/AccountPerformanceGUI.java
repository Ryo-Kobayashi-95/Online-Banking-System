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
    private static final JLabel depositMessageLabel = new JLabel("");
    private static final JLabel withdrawMessageLabel = new JLabel("");
    private static final JLabel balanceMessageLabel = new JLabel("");
    private static final JLabel transactionHistoryLabel = new JLabel("");
    private static JComboBox<String> comboBoxHistory;
    private static JComboBox<String> comboBoxDeposit;
    private final JFrame previousFrame;
    private final Account account;
    private final String name;

    private static JPanel depositMessage;
    private static JPanel withdrawMessage;
    private static JPanel balanceMessage;
    private static JPanel transactionHistoryMessage;

    private static final int WINDOW_WIDTH = 550;
    private static final int PANEL_HEIGHT = 30;

    // MODIFIES: this
    // EFFECTS: Constructor sets up all the fields, buttons and instructions for the account menu
    public AccountPerformanceGUI(String name, Account account, JFrame previousFrame) {

        super("Account Menu");

        this.account = account;
        this.previousFrame = previousFrame;
        this.name = name;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WINDOW_WIDTH, 700));
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

        createPanels(depositBtn, withdrawBtn, vcBtn, vsBtn, vtBtn, backButton);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // MODIFIES: this
    // EFFECTS: create all the panels
    private void createPanels(JButton depositBtn, JButton withdrawBtn, JButton vcBtn, JButton vsBtn, JButton vtBtn,
                              JButton backButton) {
        JPanel greeting = createGreetingMessage("Welcome back, " + name + "!" + " How can I help you today?");
        add(greeting);

        addPanels(depositBtn, withdrawBtn, vcBtn, vsBtn, vtBtn, backButton);
    }

    // MODIFIES: this
    // EFFECTS: add all the panels to the account menu
    private void addPanels(JButton depositBtn, JButton withdrawBtn, JButton vcBtn, JButton vsBtn,
                           JButton vtBtn, JButton backButton) {

        JPanel fixedMessagePanel1 = createFixedMessage("Deposit:");
        add(fixedMessagePanel1);
        createDepositLayout(depositBtn);
        depositMessage = addMessagePanels(depositMessageLabel);

        JPanel fixedMessagePanel2 = createFixedMessage("Withdraw:");
        add(fixedMessagePanel2);
        createWithdrawLayout(withdrawBtn);

        JPanel withdrawNote = createWithdrawMessage();
        add(withdrawNote);
        withdrawMessage = addMessagePanels(withdrawMessageLabel);

        JPanel fixedMessagePanel3 = createFixedMessage("View account balance");
        add(fixedMessagePanel3);
        createRecordsLayout(vcBtn, vsBtn);
        balanceMessage = addMessagePanels(balanceMessageLabel);

        JPanel fixedMessagePanel4 = createFixedMessage("View transaction history");
        add(fixedMessagePanel4);
        createTransactionHistoryLayout(vtBtn);
        transactionHistoryMessage = addMessagePanels(transactionHistoryLabel);

        JPanel spaceLabel = initJPanel(PANEL_HEIGHT);
        add(spaceLabel.add(new JLabel("")));
        JPanel backButtonPanel = initJPanel(PANEL_HEIGHT);
        backButtonPanel.add(backButton);
        add(backButtonPanel);
    }

    // MODIFIES: this
    // EFFECTS: add all the message panels to the account menu
    private JPanel addMessagePanels(JLabel messageLabel) {
        JPanel message = createMessagePanel(messageLabel);
        add(message);
        return message;
    }

    // MODIFIES: this
    // EFFECTS: initialise and render greeting message panel in this frame
    private JPanel createGreetingMessage(String text) {
        JPanel fixedMessagePanel = initJPanel(60);
        JLabel message = new JLabel(text);
        message.setBounds(10, 10, WINDOW_WIDTH, PANEL_HEIGHT);
        message.setFont(new Font("", Font.BOLD, 15));
        fixedMessagePanel.add(message);
        return fixedMessagePanel;
    }

    // MODIFIES: this
    // EFFECTS: initialise and render withdraw message panel in this frame
    private JPanel createWithdrawMessage() {
        JPanel fixedMessagePanel = initJPanel(25);
        JLabel message = new JLabel("Note: you can only withdraw from your "
                + "chequing account at this time");
        message.setBounds(10, 10, WINDOW_WIDTH, PANEL_HEIGHT);
        message.setFont(new Font("", Font.BOLD, 10));
        message.setForeground(Color.RED);
        fixedMessagePanel.add(message);
        return fixedMessagePanel;
    }

    // MODIFIES: this
    // EFFECTS: initialise and render constant message panel in this frame
    private JPanel createFixedMessage(String text) {
        JPanel fixedMessagePanel = initJPanel(20);
        JLabel message = new JLabel(text);
        message.setBounds(10, 10, WINDOW_WIDTH, PANEL_HEIGHT);
        message.setFont(new Font("", Font.BOLD, 13));
        fixedMessagePanel.add(message);
        return fixedMessagePanel;
    }

    // MODIFIES: this
    // EFFECTS: initialise JPanel in this frame
    private JPanel initJPanel(int height) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));
        panel.setPreferredSize(new Dimension(WINDOW_WIDTH, height));
        panel.setBounds(10, 10, WINDOW_WIDTH, PANEL_HEIGHT);
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: insert user message for successful user command, and error message for erroneous user command
    private JPanel createMessagePanel(JLabel message) {
        JPanel messagePanel = initJPanel(PANEL_HEIGHT);
        messagePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));
        messagePanel.setPreferredSize(new Dimension(WINDOW_WIDTH, 40));
        messagePanel.add(message);
        return messagePanel;
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
        JPanel depositPanel = initJPanel(50);
        depositField = new JTextField(15);
        depositField.setMaximumSize(depositField.getPreferredSize());

        String[] items = {"Select the account", "Chequing account", "Saving account"};
        comboBoxDeposit = new JComboBox<>(items);

        depositPanel.add(depositField);
        depositPanel.add(comboBoxDeposit);
        depositPanel.add(depositBtn);
        add(depositPanel);
    }

    // MODIFIES: this
    // EFFECTS: initialise and render withdraw field and button
    private void createWithdrawLayout(JButton withdrawBtn) {
        JPanel withdrawPanel = initJPanel(25);
        withdrawField = new JTextField(15);
        withdrawField.setMaximumSize(withdrawField.getPreferredSize());
        withdrawPanel.add(withdrawField);
        withdrawPanel.add(withdrawBtn);
        add(withdrawPanel);
    }

    // MODIFIES: this
    // EFFECTS: initialise and render account balance buttons
    private void createRecordsLayout(JButton vcBtn, JButton vsBtn) {
        JPanel recordPanel = initJPanel(50);
        recordPanel.add(vcBtn);
        recordPanel.add(vsBtn);
        add(recordPanel);
    }

    // MODIFIES: this
    // EFFECTS: initialise and render transaction history dropdown box and buttons
    private void createTransactionHistoryLayout(JButton button) {
        JPanel transactionHistoryPanel = initJPanel(50);
        String[] items = {"Select an option", "Show all transactions", "Show chequing transactions",
                "Show saving transactions"};
        comboBoxHistory = new JComboBox<>(items);
        transactionHistoryPanel.add(comboBoxHistory);
        transactionHistoryPanel.add(button);
        add(transactionHistoryPanel);
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
                depositMessageLabel.setText("Please select the account");
                depositMessageLabel.setForeground(Color.RED);
                depositMessage.add(depositMessageLabel);
                break;
            case "Chequing account":
                depositMoney("c");
                break;
            case "Saving account":
                depositMoney("s");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: perform a deposit transaction; put the money in to the chequing or saving account,
    //          or ask to re-enter the amount if it is invalid (amount < 0)
    public void depositMoney(String accountType) {
        double depositAmount = Integer.parseInt(depositField.getText());
        if (depositAmount < 0) {
            depositMessageLabel.setText("Invalid amount. Please try again");
            depositMessageLabel.setForeground(Color.RED);
            depositMessage.add(depositMessageLabel);
        } else {
            account.deposit(accountType, depositAmount);
            if (accountType.equals("c")) {
                depositMessageLabel.setText("$" + depositAmount
                        + " deposited into your chequing account successfully!");
            } else {
                depositMessageLabel.setText("$" + depositAmount
                        + " deposited into your saving account successfully!");
            }
            depositMessageLabel.setForeground(Color.BLACK);
            depositMessage.add(depositMessageLabel);
        }
    }

    // MODIFIES: this
    // EFFECTS: perform a withdrawal transaction. Subtract the money from the chequing,
    //          or ask the user to re-enter the amount if amount < 0 or amount > account.getChequingBalance()
    public void withdrawMoney() {
        double withdrawAmount = Integer.parseInt(withdrawField.getText());
        if (withdrawAmount > account.getChequingBalance()) {
            withdrawMessageLabel.setText("Insufficient balance in your chequing account. Please try again");
            withdrawMessageLabel.setForeground(Color.RED);
            withdrawMessage.add(withdrawMessageLabel);
        } else if (withdrawAmount < 0) {
            withdrawMessageLabel.setText("Invalid amount. Please try again");
            withdrawMessageLabel.setForeground(Color.RED);
            withdrawMessage.add(withdrawMessageLabel);
        } else {
            account.withdraw("c", withdrawAmount);
            double balance = account.getChequingBalance();
            withdrawMessageLabel.setText("The transaction was successful! Balance in your chequing account: $"
                    + balance);
            withdrawMessageLabel.setForeground(Color.BLACK);
            withdrawMessage.add(withdrawMessageLabel);
        }
    }

    // MODIFIES: this
    // EFFECTS: display the balance in the chequing account
    public void displayChequingBalance() {
        double balance = account.getChequingBalance();
        balanceMessageLabel.setText("Balance in your chequing account: $" + balance);
        balanceMessageLabel.setForeground(Color.BLACK);
        balanceMessage.add(balanceMessageLabel);
    }

    // MODIFIES: this
    // EFFECTS: display the balance in the saving account
    public void displaySavingBalance() {
        double balance = account.getSavingBalance();
        balanceMessageLabel.setText("Balance in your saving account: $" + balance);
        balanceMessageLabel.setForeground(Color.BLACK);
        balanceMessage.add(balanceMessageLabel);
    }

    // MODIFIES: this
    // EFFECTS: display the transaction history of this account
    public void displayTransactionHistory() {
        List<TransactionRecord> records = account.getTransactionHistory();
        String selectedOption = (String) comboBoxHistory.getSelectedItem();

        if (records.isEmpty()) {
            transactionHistoryLabel.setText("No transaction history on this account");
            transactionHistoryLabel.setForeground(Color.RED);
            transactionHistoryMessage.add(transactionHistoryLabel);
        } else {
            transactionHistoryLabel.setText("");
            transactionHistoryMessage.add(transactionHistoryLabel);
            accountSelectionForHistoryFromDropdownBox(records, selectedOption);
        }
    }

    // MODIFIES: this
    // EFFECTS: display the transaction history based on the selected account from the dropdown box
    private void accountSelectionForHistoryFromDropdownBox(List<TransactionRecord> records, String selectedOption) {
        switch (Objects.requireNonNull(selectedOption)) {
            case "Select an option":
                transactionHistoryLabel.setText("Please select an option");
                transactionHistoryLabel.setForeground(Color.RED);
                transactionHistoryMessage.add(transactionHistoryLabel);
                break;
            case "Show all transactions":
                displayAllTransactionHistory(records);
                break;
            case "Show chequing transactions":
                displaySpecificAccountTransactionHistory(records, "Chequing");
                break;
            case "Show saving transactions":
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
        accountHistoryFrame.setPreferredSize(new Dimension(400, 1000));
        ((JPanel) accountHistoryFrame.getContentPane()).setBorder(new EmptyBorder(
                13, 13, 13, 13));
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
            JLabel username = new JLabel("Username:            " + tr.getUsername());
            JLabel accountType = new JLabel("Account type:       " + tr.getAccountType());
            JLabel transactionType = new JLabel("Transaction type: " + tr.getTransactionType());
            JLabel amount = new JLabel("Amount:               $" + tr.getTransactionAmount());

            createPanelsForTransactionHistory(accountHistoryFrame, date, username,
                    accountType, transactionType, amount);

            accountHistoryFrame.pack();
            accountHistoryFrame.setLocationRelativeTo(null);
            accountHistoryFrame.setVisible(true);
            accountHistoryFrame.setResizable(true);
        }
    }

    // MODIFIES: this
    // EFFECTS: create transaction history panels to the transaction history page
    private void createPanelsForTransactionHistory(JFrame accountHistoryFrame, JLabel date, JLabel username,
                                                   JLabel accountType, JLabel transactionType, JLabel amount) {
        JPanel datePanel = initJPanel(20);

        addTransactionHistoryPanels(accountHistoryFrame, date, datePanel);

        JPanel usernamePanel = initJPanel(20);
        addTransactionHistoryPanels(accountHistoryFrame, username, usernamePanel);

        JPanel accountTypePanel = initJPanel(20);
        addTransactionHistoryPanels(accountHistoryFrame, accountType, accountTypePanel);

        JPanel transactionTypePanel = initJPanel(20);
        addTransactionHistoryPanels(accountHistoryFrame, transactionType, transactionTypePanel);

        JPanel amountPanel = initJPanel(20);
        addTransactionHistoryPanels(accountHistoryFrame, amount, amountPanel);

        JPanel spacePanel = initJPanel(20);
        addTransactionHistoryPanels(accountHistoryFrame, new JLabel(""), spacePanel);
    }

    // MODIFIES: this
    // EFFECTS: add transaction history panels to the transaction history page
    private void addTransactionHistoryPanels(JFrame accountHistoryFrame, JLabel label, JPanel panel) {
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 110, 0));
        panel.add(label);
        accountHistoryFrame.add(panel);
    }

    // MODIFIES: this
    // EFFECTS: render all the transaction history associated with the account
    private void displayAllTransactionHistory(List<TransactionRecord> records) {
        JFrame allHistoryFrame = new JFrame("Transaction history of your account");
        allHistoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        allHistoryFrame.setPreferredSize(new Dimension(400, 1000));
        ((JPanel) allHistoryFrame.getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        allHistoryFrame.setLayout(new FlowLayout());

        renderTransactionHistory(records, allHistoryFrame);
    }

}
