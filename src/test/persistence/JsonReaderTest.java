package persistence;

import model.Account;
import model.Bank;
import model.TransactionRecord;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

// Citation:
// Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Bank bank = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyBank() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyBank.json");
        try {
            Bank bank = reader.read();
            assertEquals(0, bank.length());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralBank() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralBank.json");
        try {
            Bank bank = reader.read();
            assertEquals(2, bank.length());

            Account a1 = bank.getAccount("Ryo", 1234);
            Account a2 = bank.getAccount("Justin", 5678);

            a1.deposit("c",1000);
            a2.deposit("s", 222);

            TransactionRecord t1 = a1.getTransactionHistory().get(0);
            TransactionRecord t2 = a2.getTransactionHistory().get(0);

            checkAccount("Ryo", 1234, 1000, 0,
                    t1.getUsername(), t1.getAccountType(), t1.getTransactionType(), t1.getTransactionAmount(), a1, t1);
            checkAccount("Justin", 5678, 0, 222,
                    t2.getUsername(), t2.getAccountType(), t2.getTransactionType(), t2.getTransactionAmount(), a2, t2);

//            checkAccount("Ryo", 1234, 1000, 0,
//                    "Ryo", "Chequing", "Deposit", 1000, a1, t1);
//            checkAccount("Justin", 5678, 0, 222,
//                    "Justin", "Saving", "Deposit", 222, a2, t2);

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
