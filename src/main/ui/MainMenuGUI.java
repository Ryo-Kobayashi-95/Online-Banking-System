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
    private static JPasswordField passwordField;
    private static final JLabel messageLabel1 = new JLabel("Let's see what we can do...");
    private static final JLabel messageLabel2 = new JLabel("Make sure to click 'Save' and/or 'Load'!");
    private static JPanel responsePanel1;
    private static JPanel responsePanel2;

    private static final String JSON_STORE = "./data/bank.json";
    private Bank list;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;

    private static final int WINDOW_WIDTH = 460;
    private static final int PANEL_HEIGHT = 30;

    // MODIFIES: this
    // EFFECTS: Constructor runs the online baking system and sets up all the fields,
    //          buttons and instructions for the main menu
    public MainMenuGUI() {

        super("Main menu");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        runSystem();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WINDOW_WIDTH, 380));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());

        JButton createBtn = createButtons("Create", "create");
        JButton loginBtn = createButtons("Login", "login");
        JButton saveBtn = createButtons("Save", "save");
        JButton loadBtn = createButtons("Load", "load");

        JPanel fixedMessagePanel1 = createFixedMessage("Please enter the username and password");

        add(fixedMessagePanel1);
        createFields();
        createMainMenuButtons(createBtn, loginBtn, saveBtn, loadBtn);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
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
        panel.setBounds(10, 10, WINDOW_WIDTH, height);
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: insert user message for successful user command, and error message for erroneous user command
    private JPanel createResponseMessage(JLabel message) {
        JPanel messagePanel = initJPanel(PANEL_HEIGHT);
        messagePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));
        messagePanel.setPreferredSize(new Dimension(WINDOW_WIDTH, 40));
        messagePanel.add(message);
        return messagePanel;
    }

    // MODIFIES: this
    // EFFECTS: add buttons to the main menu
    private void createMainMenuButtons(JButton createBtn, JButton loginBtn, JButton saveBtn, JButton loadBtn) {

        JPanel fixedMessagePanel2 = createFixedMessage("Please click 'Save' to save the transaction, ");
        JPanel fixedMessagePanel3 = createFixedMessage("or click 'Load' to retrieve previous transaction");

        JPanel buttons1Panel = initJPanel(50);
        JPanel buttons2Panel = initJPanel(50);

        buttons1Panel.add(createBtn);
        buttons1Panel.add(loginBtn);

        buttons2Panel.add(saveBtn);
        buttons2Panel.add(loadBtn);

        responsePanel1 = createResponseMessage(messageLabel1);
        responsePanel2 = createResponseMessage(messageLabel2);
        add(buttons1Panel);
        add(responsePanel1);
        add(fixedMessagePanel2);
        add(fixedMessagePanel3);
        add(buttons2Panel);
        add(responsePanel2);
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

        JPanel namePanel = initJPanel(PANEL_HEIGHT);
        JLabel userNameLabel = new JLabel("Username:");
        userNameLabel.setBounds(10, 10, WINDOW_WIDTH, PANEL_HEIGHT);
        nameField = new JTextField(15);
        nameField.setMaximumSize(nameField.getPreferredSize());

        namePanel.add(userNameLabel);
        namePanel.add(nameField);
        add(namePanel);

        JPanel panelPassword = initJPanel(PANEL_HEIGHT);
        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setBounds(10, 10, WINDOW_WIDTH, PANEL_HEIGHT);
        passwordField = new JPasswordField(15);
        passwordField.setMaximumSize(passwordField.getPreferredSize());

        panelPassword.add(passwordLabel);
        panelPassword.add(passwordField);
        add(panelPassword);
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

        if (size != 4) {
            messageLabel1.setText("Invalid password. Password must be 4 digits long. Please try again.");
            messageLabel1.setForeground(Color.RED);
            responsePanel1.add(messageLabel1);
        } else {
            Account account = new Account(name, pass);
            list.addAccount(account);

            messageLabel1.setText("You are all set! Click login to perform transaction!");
            messageLabel1.setForeground(Color.BLACK);
            responsePanel1.add(messageLabel1);
        }
    }

    // MODIFIES: this
    // EFFECTS: login to the existing account
    public void loginToAccount(String name, int pass) {
        messageLabel1.setText("Let's see what we can do...");
        messageLabel1.setForeground(Color.BLACK);
        responsePanel1.add(messageLabel1);

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
        messageLabel1.setText("There is no account with the given username and/or password.");
        messageLabel1.setForeground(Color.RED);
        responsePanel1.add(messageLabel1);
    }

    // MODIFIES: this
    // EFFECTS: saves the workroom to file
    private void saveBank() {
        try {
            jsonWriter.open();
            jsonWriter.write(list);
            jsonWriter.close();
            messageLabel2.setText("Saved account to " + JSON_STORE);
            messageLabel2.setForeground(Color.BLACK);
            responsePanel2.add(messageLabel2);
        } catch (FileNotFoundException e) {
            messageLabel2.setText("Unable to write to file: " + JSON_STORE);
            messageLabel2.setForeground(Color.BLACK);
            responsePanel2.add(messageLabel2);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadBank() {
        try {
            list = jsonReader.read();
            messageLabel2.setText("Loaded account from " + JSON_STORE);
            messageLabel2.setForeground(Color.BLACK);
            responsePanel2.add(messageLabel2);
        } catch (IOException e) {
            messageLabel2.setText("Unable to read from file: " + JSON_STORE);
            messageLabel2.setForeground(Color.RED);
            responsePanel2.add(messageLabel2);
        }
    }
}