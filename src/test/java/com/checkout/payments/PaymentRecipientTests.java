package com.checkout.payments;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PaymentRecipientTests {
    @RunWith(Parameterized.class)
    public static class EnclosedAccountNumber {
        @Parameterized.Parameter
        public String invalidAccountNumber;

        @Parameterized.Parameters
        public static List<String> invalidAccountNumbers() {
            List<String> list = new ArrayList<>();
            Collections.addAll(list, "", " ", null, "12345");
            return list;
        }

        @Test(expected = IllegalArgumentException.class)
        public void given_account_number_invalid_should_throw_exception() {
            new PaymentRecipient(LocalDate.now(), invalidAccountNumber, "NW1", null, "Jones");
        }
    }

    @RunWith(Parameterized.class)
    public static class EnclosedZip {
        @Parameterized.Parameter
        public String invalidZip;

        @Parameterized.Parameters
        public static List<String> invalidZips() {
            List<String> list = new ArrayList<>();
            Collections.addAll(list, "", " ", null);
            return list;
        }

        @Test(expected = IllegalArgumentException.class)
        public void given_account_number_invalid_should_throw_exception() {
            new PaymentRecipient(LocalDate.now(), "1234567890", invalidZip, null, "Jones");
        }
    }

    @RunWith(Parameterized.class)
    public static class EnclosedLastName {
        @Parameterized.Parameter
        public String invalidLastName;

        @Parameterized.Parameters
        public static List<String> invalidLastNames() {
            List<String> list = new ArrayList<>();
            Collections.addAll(list, "", " ", null);
            return list;
        }

        @Test(expected = IllegalArgumentException.class)
        public void given_account_number_invalid_should_throw_exception() {
            new PaymentRecipient(LocalDate.now(), "1234567890", "NW1", null, invalidLastName);
        }
    }
}