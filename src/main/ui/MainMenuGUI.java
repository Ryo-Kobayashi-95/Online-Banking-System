package ui;

import model.Account;
import model.Bank;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// Represents the online banking system's main menu window frame
public class MainMenuGUI extends JFrame implements ActionListener {

    private static JTextField nameField;
    private static JTextField passwordField;
    private static JLabel userMessageLabel;

    private static final String JSON_STORE = "./data/bank.json";
    private Bank list;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;

    // MODIFIES: this
    // EFFECTS: Constructor runs the online baking system and sets up all the fields,
    //          buttons and instructions for the main menu
    public MainMenuGUI() {

        super("Main menu");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        runSystem();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(700, 400));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());

        JButton createBtn = createButtons("Create", "create");
        JButton loginBtn = createButtons("Login", "login");
        JButton saveBtn = createButtons("Save", "save");
        JButton loadBtn = createButtons("Load", "load");

        createFields();

        add(createBtn);
        add(loginBtn);
        add(saveBtn);
        add(loadBtn);

        userMessageLabel = new JLabel("");
        add(userMessageLabel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // REQUIRES: both buttonName and key must at least one character long
    // MODIFIES: this
    // EFFECTS: create buttons with the given name and key in the main menu
    private JButton createButtons(String buttonName, String key) {
        JButton button = new JButton(buttonName);
        button.setActionCommand(key);
        button.addActionListener(this);
        return button;
    }

    // MODIFIES: this
    // EFFECTS: create and render fields in the main menu
    private void createFields() {
        JLabel userNameLabel = new JLabel("Username");
        nameField = new JTextField(15);
        add(userNameLabel);
        add(nameField);

        JLabel passwordLabel = new JLabel("Password");
        passwordField = new JTextField(15);
        add(passwordLabel);
        add(passwordField);
    }

    // EFFECTS: when the JButton btn is clicked, the related method is performed;
    //          create - create a new account
    //          login - login to an existing account if there is one
    //          save - save the state of the application
    //          load - load the state of the application
    @Override
    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText();

        String pass = passwordField.getText();
        int password = 0;
        if (!pass.isEmpty()) {
            password = Integer.parseInt(passwordField.getText());
        }

        if (e.getActionCommand().equals("create")) {
            createAccount(name, password);
        } else if (e.getActionCommand().equals("login")) {
            loginToAccount(name, password);
        } else if (e.getActionCommand().equals("save")) {
            saveBank();
        } else if (e.getActionCommand().equals("load")) {
            loadBank();
        }
    }

    // MODIFIES: this
    // EFFECTS: run the main menu
    public void runSystem() {
        init();
    }

    // MODIFIES: this
    // EFFECTS: initialize a new account list
    public void init() {
        this.list = new Bank();
    }

    // MODIFIES: this
    // EFFECTS: create a new account. Ask the user to re-enter if the password length is not 4
    public void createAccount(String name, int pass) {
        String passStr = Integer.toString(pass);
        int size = passStr.length();

//      TODO: show an error message for invalid password in red color

        while (size != 4) {
            userMessageLabel.setText("Invalid password. Password must be 4 digits long. Please try again.");
            pass = Integer.parseInt(passwordField.getText());
            passStr = Integer.toString(pass);
            size = passStr.length();
        }

        Account account = new Account(name, pass);
        list.addAccount(account);

        userMessageLabel.setText("You are all set! "
                + "\nPlease login to your account if you wish to perform transaction!");
    }

    // MODIFIES: this
    // EFFECTS: login to the existing account
    public void loginToAccount(String name, int pass) {

//      TODO: accept the lower case too

        Account account = list.getAccount(name, pass);
        if (account == null) {
            noAccountHandler();
        } else {
            this.dispose();
            AccountPerformanceGUI apGUI = new AccountPerformanceGUI(name, account, this);
            apGUI.setVisible(true);
        }
    }

    // MODIFIES: this
    // EFFECTS: handle a situation where given username and/or password not found in the account list.
    //          ask to re-enter or go back to the main menu
    public void noAccountHandler() {
//      TODO: show an error message in red color
        userMessageLabel.setText("There is no account with the given username and/or password." + "\n"
                + "Please try again");
    }

    // MODIFIES: this
    // EFFECTS: saves the workroom to file
    private void saveBank() {
        try {
            jsonWriter.open();
            jsonWriter.write(list);
            jsonWriter.close();
            userMessageLabel.setText("Saved account to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            userMessageLabel.setText("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadBank() {
        try {
            list = jsonReader.read();
            userMessageLabel.setText("Loaded account from " + JSON_STORE);
        } catch (IOException e) {
            userMessageLabel.setText("Unable to read from file: " + JSON_STORE);
        }
    }
}