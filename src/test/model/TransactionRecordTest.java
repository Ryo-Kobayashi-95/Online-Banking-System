package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionRecordTest {

    private TransactionRecord tRec;

    @BeforeEach
    void setUp() {
        tRec = new TransactionRecord("Justin", "Chequing",
                "Deposit", 100);
    }

    @Test
    void testTransactionRecord() {
        assertEquals("Justin", tRec.getUsername());
        assertEquals("Chequing", tRec.getAccountType());
        assertEquals("Deposit", tRec.getTransactionType());
        assertEquals(100, tRec.getTransactionAmount());
    }

    @Test
    void testGetDate() {
        Date dateOne = tRec.getDate();
        Date dateTwo = new Date();
        assertTrue((dateOne.getTime() - dateTwo.getTime()) < 1000);
    }
}
