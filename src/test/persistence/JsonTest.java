package persistence;

import model.Account;
import model.TransactionRecord;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Citation:
// Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonTest {
    protected void  checkAccount(String username, int password, double chequingBalance, double savingBalance,
                                 String tUsername, String accountType, String transactionType,
                                 double transactionAmount, Account account, TransactionRecord record) {
        assertEquals(username, account.getName());
        assertEquals(password, account.getPassword());
        assertEquals(chequingBalance, account.getChequingBalance());
        assertEquals(savingBalance, account.getSavingBalance());
        assertEquals(tUsername, record.getUsername());
        assertEquals(accountType, record.getAccountType());
        assertEquals(transactionType, record.getTransactionType());
        assertEquals(transactionAmount, record.getTransactionAmount());
    }
}
