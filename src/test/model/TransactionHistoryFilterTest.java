package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionHistoryFilterTest {

    TransactionRecord tr1;
    TransactionRecord tr2;
    TransactionRecord tr3;
    List<TransactionRecord> accountRecords;
    TransactionHistoryFilter filter;

    @BeforeEach
    public void setUp() {

        Date date = new Date();
        String dateStr = date.toString();

        filter = new TransactionHistoryFilter();
        accountRecords = new ArrayList<>();

        tr1 = new TransactionRecord("A", "Chequing", "Deposit",
                dateStr, 1000);
        tr2 = new TransactionRecord("B", "Chequing", "Withdraw",
                dateStr, 500);
        tr3 = new TransactionRecord("C", "Saving", "Deposit",
                dateStr, 1500);

        accountRecords.add(tr1);
        accountRecords.add(tr2);
        accountRecords.add(tr3);
    }

    @Test
    public void testHistoryFilterForAll() {
        List<TransactionRecord> all = filter.historyFilter(accountRecords, "all");

        assertEquals(accountRecords.size(), all.size());
        assertEquals(accountRecords, all);
        assertTrue(all.contains(tr1));
        assertTrue(all.contains(tr2));
        assertTrue(all.contains(tr3));
    }

    @Test
    public void testHistoryFilterForChequing() {

        List<TransactionRecord> chequing = filter.historyFilter(accountRecords, "Chequing");

        for (TransactionRecord tr : chequing) {
            if (!tr.getAccountType().equals("Chequing")) {
                fail("Not filtered correctly");
            }
        }

        assertEquals(2, chequing.size());
        assertTrue(chequing.contains(tr1));
        assertTrue(chequing.contains(tr2));
        assertFalse(chequing.contains(tr3));
    }

    @Test
    public void testHistoryFilterForSaving() {

        List<TransactionRecord> saving = filter.historyFilter(accountRecords, "Saving");

        for (TransactionRecord tr : saving) {
            if (!tr.getAccountType().equals("Saving")) {
                fail("Not filtered correctly");
            }
        }

        assertEquals(1, saving.size());
        assertFalse(saving.contains(tr1));
        assertFalse(saving.contains(tr2));
        assertTrue(saving.contains(tr3));
    }




}
