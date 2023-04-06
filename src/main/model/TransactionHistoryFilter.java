package model;

import java.util.ArrayList;
import java.util.List;

// Represents the filtered transaction history (chequing account, saving account or both accounts transaction history)
public class TransactionHistoryFilter {

    private List<TransactionRecord> accountRecords = new ArrayList<>();

    // MODIFIES: this
    // EFFECTS: filter the transaction history based on the given account type
    public List<TransactionRecord> historyFilter(List<TransactionRecord> records, String account) {

        if (account.equals("all")) {
            EventLog.getInstance().logEvent(new Event("Transaction history is filtered to display the "
                    + "history of both accounts"));
            return records;
        } else {
            for (TransactionRecord tr : records) {
                if (tr.getAccountType().equals(account)) {
                    accountRecords.add(tr);
                }
            }
            EventLog.getInstance().logEvent(new Event("Transaction history is filtered to display the "
                    + "history of " + account.toLowerCase() + " account only"));
            return accountRecords;
        }
    }
}
