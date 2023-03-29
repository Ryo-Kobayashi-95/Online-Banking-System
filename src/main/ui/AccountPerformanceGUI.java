package ui;

import model.Account;
import model.TransactionRecord;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

// Represents the online banking system's account menu window frame
public class AccountPerformanceGUI extends JFrame implements ActionListener {

    private static JLabel viewBalanceLabel;
    private static JTextField depositField;
    private static JTextField withdrawField;
    private static JLabel userMessageLabel;
    private static JComboBox<String> comboBox;
    private JFrame previousFrame;
    private Account account;

    // EFFECTS: Constructor sets up all the fields, buttons and instructions for the account menu
    public AccountPerformanceGUI(String name, Account account, JFrame previousFrame) {

        super("Welcome back, " + name + "!");

        this.account = account;
        this.previousFrame = previousFrame;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(700, 400));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
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
        vsBtn.setActionCommand("vs");
        vsBtn.addActionListener(this);

        JButton vtBtn = new JButton("View transaction history");
        vtBtn.setActionCommand("vt");
        vtBtn.addActionListener(this);

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


        String[] items = {"Select an option", "Show all transactions", "Show chequing transactions", "Show saving transactions"};
//        ComboBoxModel<String> model = new DefaultComboBoxModel<>(items) {
//            boolean selectionAllowed = false;
//            @Override
//            public void setSelectedItem(Object item) {
//                if (!selectionAllowed) {
//                    if (item != null && getIndexOf(item) == 0) {
//                        super.setSelectedItem(null);
//                        return;
//                    }
//                }
//                super.setSelectedItem(item);
//            }
//        };

        comboBox = new JComboBox<>(items);

        add(comboBox);
        add(vtBtn);
        add(backButton);

        userMessageLabel = new JLabel("");
        add(userMessageLabel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

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
        String selectedOption = (String) comboBox.getSelectedItem();

        if (records.isEmpty()) {
            userMessageLabel.setText("No transaction history on this account");
        } else {
            if (selectedOption.equals("Select an option")) {
                userMessageLabel.setText("Please select an option.");
            } else if (selectedOption.equals("Show all transactions")) {
                userMessageLabel.setText(" ");

                JFrame allHistoryFrame = new JFrame("Account Page");
                allHistoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                allHistoryFrame.setPreferredSize(new Dimension(700, 400));
                ((JPanel) allHistoryFrame.getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
                allHistoryFrame.setLayout(new FlowLayout());

                for (TransactionRecord tr : records) {
                    JLabel date = new JLabel("Transaction date: " + tr.getDate());
                    JLabel username = new JLabel("Username:         " + tr.getUsername());
                    JLabel accountType = new JLabel("Account type:     " + tr.getAccountType());
                    JLabel transactionType = new JLabel("Transaction Type: " + tr.getTransactionType());
                    JLabel amount = new JLabel("Amount:           $" + tr.getTransactionAmount());

                    allHistoryFrame.add(date);
                    allHistoryFrame.add(username);
                    allHistoryFrame.add(accountType);
                    allHistoryFrame.add(transactionType);
                    allHistoryFrame.add(amount);

                    allHistoryFrame.pack();
                    allHistoryFrame.setLocationRelativeTo(null);
                    allHistoryFrame.setVisible(true);
                    allHistoryFrame.setResizable(false);
                }
            } else if (selectedOption.equals("Show chequing transactions")) {
                userMessageLabel.setText(" ");
                List<TransactionRecord> depositRecords = new ArrayList<>();

                JFrame depositHistoryFrame = new JFrame("Account Page");
                depositHistoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                depositHistoryFrame.setPreferredSize(new Dimension(700, 400));
                ((JPanel) depositHistoryFrame.getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
                depositHistoryFrame.setLayout(new FlowLayout());

                for (TransactionRecord tr : records) {
                    if (tr.getAccountType().equals("Chequing")) {
                        depositRecords.add(tr);
                    }
                }
                for (TransactionRecord tr : depositRecords) {
                    JLabel date = new JLabel("Transaction date: " + tr.getDate());
                    JLabel username = new JLabel("Username:         " + tr.getUsername());
                    JLabel accountType = new JLabel("Account type:     " + tr.getAccountType());
                    JLabel transactionType = new JLabel("Transaction Type: " + tr.getTransactionType());
                    JLabel amount = new JLabel("Amount:           $" + tr.getTransactionAmount());

                    depositHistoryFrame.add(date);
                    depositHistoryFrame.add(username);
                    depositHistoryFrame.add(accountType);
                    depositHistoryFrame.add(transactionType);
                    depositHistoryFrame.add(amount);

                    depositHistoryFrame.pack();
                    depositHistoryFrame.setLocationRelativeTo(null);
                    depositHistoryFrame.setVisible(true);
                    depositHistoryFrame.setResizable(false);
                }
            } else if (selectedOption.equals("Show saving transactions")) {
                userMessageLabel.setText(" ");
                List<TransactionRecord> savingRecords = new ArrayList<>();

                JFrame savingHistoryFrame = new JFrame("Account Page");
                savingHistoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                savingHistoryFrame.setPreferredSize(new Dimension(700, 400));
                ((JPanel) savingHistoryFrame.getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
                savingHistoryFrame.setLayout(new FlowLayout());

                for (TransactionRecord tr : records) {
                    if (tr.getAccountType().equals("Saving")) {
                        savingRecords.add(tr);
                    }
                }
                for (TransactionRecord tr : savingRecords) {
                    JLabel date = new JLabel("Transaction date: " + tr.getDate());
                    JLabel username = new JLabel("Username:         " + tr.getUsername());
                    JLabel accountType = new JLabel("Account type:     " + tr.getAccountType());
                    JLabel transactionType = new JLabel("Transaction Type: " + tr.getTransactionType());
                    JLabel amount = new JLabel("Amount:           $" + tr.getTransactionAmount());

                    savingHistoryFrame.add(date);
                    savingHistoryFrame.add(username);
                    savingHistoryFrame.add(accountType);
                    savingHistoryFrame.add(transactionType);
                    savingHistoryFrame.add(amount);

                    savingHistoryFrame.pack();
                    savingHistoryFrame.setLocationRelativeTo(null);
                    savingHistoryFrame.setVisible(true);
                    savingHistoryFrame.setResizable(false);
                }
            }
        }
    }
}
