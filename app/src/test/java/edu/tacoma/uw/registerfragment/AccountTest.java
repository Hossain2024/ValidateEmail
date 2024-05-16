package edu.tacoma.uw.registerfragment;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

public class AccountTest {

    @Test(expected = IllegalArgumentException.class)
    public void testAccountConstructorWithInvalidInputs() {
        new Account("ADDYOUROWN", "ADDYOUROWN");
    }
    @Test
    public void testAccountConstructorBadEmail() {
        try {
            new Account("ADDYOUROWN", "ADDYOUROWN");
            fail("Account created with invalid email");
        } catch(IllegalArgumentException e) {

        }
    }
}
