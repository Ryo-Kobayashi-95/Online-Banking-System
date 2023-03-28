package ui;

import model.Account;
import model.TransactionRecord;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AccountPerformanceGUI extends JFrame implements ActionListener {

    private static JLabel viewBalanceLabel;
    private static JTextField depositField;
    private static JTextField withdrawField;
    private static JLabel userMessageLabel;
    private Account account;

    public AccountPerformanceGUI(String name, Account account) {

        super("Welcome back, " + name + "!" + " This is your account page");

        this.account = account;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(700, 400));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13) );
        setLayout(new FlowLayout());

        JButton depositCBtn = new JButton("Deposit into chequing account");
        depositCBtn.setActionCommand("depositC");
        depositCBtn.addActionListener(this);
        JButton depositSBtn = new JButton("Deposit into saving account");
        depositSBtn.setActionCommand("depositS");
        depositSBtn.addActionListener(this);

        JButton withdrawBtn = new JButton("Withdraw");
        withdrawBtn.setActionCommand("withdraw");
        withdrawBtn.addActionListener(this);

        JButton vcBtn = new JButton("Chequing account");
        vcBtn.setActionCommand("vc");
        vcBtn.addActionListener(this);

        JButton vsBtn = new JButton("Saving account");
        vcBtn.setActionCommand("vs");
        vcBtn.addActionListener(this);

        JButton backButton = new JButton("Back to the main menu");
        backButton.setActionCommand("bb");
        backButton.addActionListener(this);

        depositField = new JTextField(15);
        add(depositField);
        add(depositCBtn);
        add(depositSBtn);

        withdrawField = new JTextField(15);
        add(withdrawField);
        add(withdrawBtn);

        viewBalanceLabel = new JLabel("View account balance");
        add(viewBalanceLabel);
        add(vcBtn);
        add(vsBtn);

        userMessageLabel = new JLabel("");
        add(userMessageLabel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("depositC")) {
            depositMoney("c");
        } else if (e.getActionCommand().equals("depositS")) {
            depositMoney("s");
        }else if (e.getActionCommand().equals("withdraw")) {
            withdrawMoney();
        } else if (e.getActionCommand().equals("vc")) {
            displayChequingBalance();
        } else if (e.getActionCommand().equals("vs")) {
            displaySavingBalance();
        } else if (e.getActionCommand().equals("vt")) {
            displayTransactionHistory();
        } else if (e.getActionCommand().equals("bb")) {
            dispose();
        }
    }

    // MODIFIES: this
    // EFFECTS: perform a deposit transaction; put the money in to the chequing or saving account,
    //          or ask to re-enter the amount if it is invalid (amount < 0)
    public void depositMoney(String accountType) {

        int depositAmount;
        do {
            depositAmount = Integer.parseInt(depositField.getText());
            if (depositAmount < 0) {
                userMessageLabel.setText("Invalid amount. Please try again");
            }
        } while (depositAmount < 0);

        account.deposit(accountType, depositAmount);
        userMessageLabel.setText("The transaction has been done successfully!");
    }

    // MODIFIES: this
    // EFFECTS: perform a withdrawal transaction. Subtract the money from the chequing,
    //          or ask the user to re-enter the amount if amount < 0 or amount > account.getChequingBalance()
    public void withdrawMoney() {
        int withdrawAmount;

//        System.out.println("\nNote: you can only withdraw money from your chequing account at this time");

        do {
            withdrawAmount = Integer.parseInt(withdrawField.getText());
            if (withdrawAmount > account.getChequingBalance()) {
                userMessageLabel.setText("Insufficient balance in your chequing account. Please try again");
            } else if (withdrawAmount < 0) {
                userMessageLabel.setText("Invalid amount. Please try again");
            }
        } while (withdrawAmount < 0 || withdrawAmount > account.getChequingBalance());

        account.withdraw("c", withdrawAmount);

        userMessageLabel.setText("The transaction has been done successfully!");
    }

    // EFFECTS: display the balance in the chequing account
    public void displayChequingBalance() {
        double balance = account.getChequingBalance();
        userMessageLabel.setText("Balance in your chequing account: $" + balance);
    }

    // EFFECTS: display the balance in the saving account
    public void displaySavingBalance() {
        double balance = account.getSavingBalance();
        userMessageLabel.setText("Balance in your saving account: $" + balance);
    }

    // EFFECTS: display the transaction history of this account
    public void displayTransactionHistory() {
        List<TransactionRecord> records = account.getTransactionHistory();

        if (records.isEmpty()) {
            userMessageLabel.setText("No transaction history on this account");
        } else {
            for (TransactionRecord tr : records) {
                JLabel date = new JLabel("Transaction date: " + tr.getDate());
                JLabel username = new JLabel("Username:         " + tr.getUsername());
                JLabel accountType = new JLabel("Account type:     " + tr.getAccountType());
                JLabel transactionType = new JLabel("Transaction Type: " + tr.getTransactionType());
                JLabel amount = new JLabel("Amount:           $" + tr.getTransactionAmount());

                add(date);
                add(username);
                add(accountType);
                add(transactionType);
                add(amount);
            }
        }
    }
}