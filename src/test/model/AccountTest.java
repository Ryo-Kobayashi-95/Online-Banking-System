package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    private Account a1;

    @BeforeEach
    void setUp() {
        a1 = new Account("James", 1234);
    }

    @Test
    void testAccount() {
        assertEquals("James", a1.getName());
        assertEquals(1234, a1.getPassword());
        assertEquals(0, a1.getChequingBalance());
        assertEquals(0, a1.getSavingBalance());
        assertEquals(0, a1.getTransactionHistory().size());
    }

    @Test
    void testDeposit() {
        a1.deposit("c", 100);
        assertEquals(100, a1.getChequingBalance());
        assertEquals(0, a1.getSavingBalance());
        assertEquals(1, a1.getTransactionHistory().size());

        a1.deposit("s", 500);
        assertEquals(100, a1.getChequingBalance());
        assertEquals(500, a1.getSavingBalance());
        assertEquals(2, a1.getTransactionHistory().size());
    }

    @Test
    void testWithdraw() {
        a1.deposit("c", 100);
        a1.deposit("s", 500);

        a1.withdraw("c", 50);
        a1.withdraw("s", 200);
        assertEquals(50, a1.getChequingBalance());
        assertEquals(300, a1.getSavingBalance());
        assertEquals(4, a1.getTransactionHistory().size());

        a1.withdraw("c", 50);
        a1.withdraw("s", 300);
        assertEquals(0, a1.getChequingBalance());
        assertEquals(0, a1.getSavingBalance());
        assertEquals(6, a1.getTransactionHistory().size());
    }
}
