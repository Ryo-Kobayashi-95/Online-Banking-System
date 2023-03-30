package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionRecordTest {

    private TransactionRecord tRec;
    private String date;

    @BeforeEach
    void setUp() {
        date = (new Date()).toString();
        tRec = new TransactionRecord("Mike", "Chequing",
                "Deposit", date, 100);
    }

    @Test
    void testTransactionRecord() {
        assertEquals("Mike", tRec.getUsername());
        assertEquals("Chequing", tRec.getAccountType());
        assertEquals("Deposit", tRec.getTransactionType());
        assertEquals(100, tRec.getTransactionAmount());
    }

    @Test
    void testGetDate() {
        String dateOne = tRec.getDate();
        assertEquals(dateOne, date);
    }
}
