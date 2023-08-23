package ui;

import model.Account;
import model.Bank;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// Represents the online banking system's main menu window frame
public class MainMenuGUI extends JFrame implements ActionListener {

    private static JTextField nameField;
    private static JPasswordField passwordField;
    private static final JLabel messageLabel1 = new JLabel("Let's see what we can do...");
    private static JPanel responsePanel1;
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
        setPreferredSize(new Dimension(WINDOW_WIDTH, 450));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(0, 13, 13, 13));
        setLayout(new FlowLayout());

        JButton createBtn = createButtons("Create", "create");
        JButton loginBtn = createButtons("Login", "login");

        JPanel fixedMessagePanel1 = createFixedMessage("Please enter the username and password", 20);

        createTopBord();

        add(fixedMessagePanel1);
        createFields();
        createMainMenuButtons(createBtn, loginBtn);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        saveData();

        printLogEvent();

    }

    // EFFECTS: save the transaction data
    private void saveData() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveBank();
            }
        });
    }

    // EFFECTS: add window listener and print logged events
    private void printLogEvent() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {

                printLog(EventLog.getInstance());

                System.exit(0);

            }
        });
    }

    // EFFECTS: print logged events to console
    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println("\n" + next);
        }

        repaint();
    }

    // MODIFIES: this
    // EFFECTS: render the image on the top of the main menu frame
    private void createTopBord() {
        JPanel panel = new TopBordPanelGUI("main");
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panel.setPreferredSize(new Dimension(WINDOW_WIDTH, 200));
        panel.setBounds(10, 10, WINDOW_WIDTH, 300);
        add(panel);
    }

    // MODIFIES: this
    // EFFECTS: initialise and render constant message panel in this frame
    private JPanel createFixedMessage(String text, int height) {
        JPanel fixedMessagePanel = initJPanel(height);
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
    // EFFECTS: create a panel for a response message
    private JPanel createResponseMessagePanel(JLabel message, int height) {
        JPanel messagePanel = initJPanel(height);
        messagePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));
        messagePanel.setPreferredSize(new Dimension(WINDOW_WIDTH, 80));
        messagePanel.add(message);
        return messagePanel;
    }

    // MODIFIES: this
    // EFFECTS: add buttons to the main menu
    private void createMainMenuButtons(JButton createBtn, JButton loginBtn) {

        JPanel buttons1Panel = initJPanel(50);
        JPanel buttons2Panel = initJPanel(40);

        buttons1Panel.add(createBtn);
        buttons1Panel.add(loginBtn);

        responsePanel1 = createResponseMessagePanel(messageLabel1, 100);
        add(buttons1Panel);
        add(responsePanel1);
        add(buttons2Panel);
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
        String password = passwordField.getText();

        if (e.getActionCommand().equals("create")) {
            createAccount(name, password);
        } else if (e.getActionCommand().equals("login")) {
            loginToAccount(name, password);
        } else if (e.getActionCommand().equals("done")) {
            saveBank();
            this.dispose();
        }
    }

    // MODIFIES: this
    // EFFECTS: run the main menu
    public void runSystem() {
        this.list = new Bank();
        init();
    }

    // MODIFIES: this
    // EFFECTS: initialize a new account list
    public void init() {
        try {
            this.list = jsonReader.read();
        } catch (IOException e) {

        }
    }

    // MODIFIES: this
    // EFFECTS: create a new account. Ask the user to re-enter if the password length is not 4
    public void createAccount(String name, String pass) {
        int size = pass.length();

        try {
            if (name.equals("") || pass.equals("")) {
                responseMessageCreator(messageLabel1, "Please enter your username and/or password",
                        Color.RED, responsePanel1);
            } else if (size != 4) {
                responseMessageCreator(messageLabel1, "Invalid password. Password must be 4 digits long. "
                        + "Please try again.", Color.RED, responsePanel1);
            } else {
                Integer.parseInt(pass);

                Account account = new Account(name, pass);
                list.addAccount(account);

                responseMessageCreator(messageLabel1, "You are all set! Click login to perform transaction!",
                        Color.BLACK, responsePanel1);
            }
        } catch (NumberFormatException e) {
            responseMessageCreator(messageLabel1, "Invalid password. Password must be 4 digits long. "
                    + "Please try again.", Color.RED, responsePanel1);
        }
    }

    // MODIFIES: this
    // EFFECTS: insert response message for successful user command, and error message for erroneous user command
    private void responseMessageCreator(JLabel message, String text, Color color, JPanel panel) {
        message.setText(text);
        message.setForeground(color);
        panel.add(message);
    }

    // MODIFIES: this
    // EFFECTS: login to the existing account
    public void loginToAccount(String name, String pass) {
        responseMessageCreator(messageLabel1, "Let's see what we can do...", Color.BLACK, responsePanel1);

        Account account = list.getAccount(name, pass);

        if (name.equals("") || pass.equals("")) {
            responseMessageCreator(messageLabel1, "Please enter your username and/or password",
                    Color.RED, responsePanel1);
        } else if (account == null) {
            noAccountHandler();
        } else {
            this.dispose();
            AccountMenuGUI apGUI = new AccountMenuGUI(name, account, this);
            apGUI.setVisible(true);
            nameField.setText("");
            passwordField.setText("");
        }
    }

    // MODIFIES: this
    // EFFECTS: handle a situation where given username and/or password not found in the account list.
    //          ask to re-enter or go back to the main menu
    public void noAccountHandler() {
        responseMessageCreator(messageLabel1, "There is no account with the given username and/or password.",
                Color.RED, responsePanel1);
    }

    // MODIFIES: this
    // EFFECTS: saves the workroom to file
    private void saveBank() {
        try {
            jsonWriter.open();
            jsonWriter.write(list);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable read data");
        }
    }

}