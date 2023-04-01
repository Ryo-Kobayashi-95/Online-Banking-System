package persistence;

import model.Account;
import model.Bank;
import model.TransactionRecord;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Citation:
// Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Bank bank = new Bank();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyBank() {
        try {
            Bank bank = new Bank();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyBank.json");
            writer.open();
            writer.write(bank);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyBank.json");
            bank = reader.read();
            assertEquals(0, bank.length());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralBank() {
        try {
            Bank bank = new Bank();
            bank.addAccount(new Account("Ryo", "1234"));
            bank.addAccount(new Account("Justin", "5678"));

            Account a1 = bank.getAccount("Ryo", "1234");
            Account a2 = bank.getAccount("Justin", "5678");

            a1.deposit("c",1000);
            a2.deposit("s", 222);

            TransactionRecord t1 = a1.getTransactionHistory().get(0);
            TransactionRecord t2 = a2.getTransactionHistory().get(0);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBank.json");
            writer.open();
            writer.write(bank);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralBank.json");
            bank = reader.read();
            assertEquals(2, bank.length());

            checkAccount("Ryo", "1234", 1000, 0,
                    "Ryo", "Chequing", "Deposit", 1000, a1, t1);
            checkAccount("Justin", "5678", 0, 222,
                    "Justin", "Saving", "Deposit", 222, a2, t2);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
