package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class AccountLIstTest {

    private Bank accounts;

    private Account a1;
    private Account a2;
    private Account a3;

    @BeforeEach
    void setUp() {
        this.accounts = new Bank();
        this.a1 = new Account("James", 1234);
        this.a2 = new Account("Justin", 5678);
        this.a3 = new Account("Julia", 9876);
    }

    @Test
    void testAccountList() {
        assertEquals(0, accounts.length());
    }

    @Test
    void testAddAccount() {
        accounts.addAccount(a1);
        assertEquals(1, accounts.length());

        accounts.addAccount(a2);
        accounts.addAccount(a3);
        assertEquals(3, accounts.length());
    }

    @Test
    void testGetAccount() {
        accounts.addAccount(a1);
        accounts.addAccount(a2);
        accounts.addAccount(a3);

        assertEquals(a1, accounts.getAccount("James", 1234));
        assertEquals(a2, accounts.getAccount("Justin", 5678));
        assertEquals(a3, accounts.getAccount("Julia", 9876));

        assertNull(accounts.getAccount("Julia", 2525));
        assertNull(accounts.getAccount("fkf", 1234));
        assertNull(accounts.getAccount("Justin", 1235));
        assertNull(accounts.getAccount("Justi", 1234));
    }

    @Test
    void testLength() {
        accounts.addAccount(a1);
        assertEquals(1, accounts.length());

        accounts.addAccount(a2);
        accounts.addAccount(a3);
        assertEquals(3, accounts.length());
    }

    @Test
    void testSetUsername() {
        a1.setUsername("Ryo");
        assertEquals("Ryo", a1.getName());
    }

    @Test
    void testSetPassword() {
        a1.setPassword(4321);
        assertEquals(4321, a1.getPassword());
    }
}
