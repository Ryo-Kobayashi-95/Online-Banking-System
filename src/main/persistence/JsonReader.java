package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.stream.Stream;

import model.Account;
import model.Bank;
import model.TransactionRecord;
import org.json.*;

// Citation:
// Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// Represents a reader that reads bank from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads bank from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Bank read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBank(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses bank from JSON object and returns it
    private Bank parseBank(JSONObject jsonObject) {
        Bank bank = new Bank();
        addAccounts(bank, jsonObject);
        return bank;
    }

    // MODIFIES: bank
    // EFFECTS: parses accounts from JSON object and adds them to bank
    private void addAccounts(Bank bank, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("accounts");
        for (Object json : jsonArray) {
            JSONObject nextAccount = (JSONObject) json;
            addAccount(bank, nextAccount);
        }
    }

    // MODIFIES: bank
    // EFFECTS: parses account from JSON object and adds it to bank
    private void addAccount(Bank bank, JSONObject jsonObject) {
        String name = jsonObject.getString("username");
        String password = jsonObject.getString("password");

        Account account = new Account(name, password);

        double chequingBalance = jsonObject.getDouble("chequingBalance");
        double savingBalance = jsonObject.getDouble("savingBalance");

        account.setChequingBalance(chequingBalance);
        account.setSavingBalance(savingBalance);

        addTransactionHistory(account, jsonObject);

        bank.addAccount(account);
    }

    // MODIFIES: account
    // EFFECTS: parses accounts from JSON object and adds them to bank
    private void addTransactionHistory(Account account, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("transactionHistory");
        for (Object json : jsonArray) {
            JSONObject nextRecord = (JSONObject) json;
            addRecord(account, nextRecord);
        }
    }

    // MODIFIES: account
    // EFFECTS: parses account from JSON object and adds it to bank
    private void addRecord(Account account, JSONObject jsonObject) {
        String name = jsonObject.getString("username");
        String accountType = jsonObject.getString("accountType");
        String transactionType = jsonObject.getString("transactionType");
        String date = jsonObject.getString("date");
        double transactionAmount = jsonObject.getDouble("transactionAmount");

        account.getTransactionHistory().add(new TransactionRecord(name, accountType,
                transactionType, date, transactionAmount));

    }

}