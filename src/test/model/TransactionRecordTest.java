package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionRecordTest {

    private TransactionRecord tRec;

    @BeforeEach
    void setUp() {
        tRec = new TransactionRecord("Justin", "Chequing", 100);
    }

    @Test
    void testTransactionRecord() {
        assertEquals("Justin", tRec.getUsername());
        assertEquals("Chequing", tRec.getAccountType());
        assertEquals(100, tRec.getTransactionAmount());
        assertEquals(new Date(), tRec.getDate());
    }
}
