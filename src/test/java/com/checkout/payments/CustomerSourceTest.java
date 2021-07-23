package com.checkout.payments;

import com.checkout.payments.CustomerSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomerSourceTest {

    @RunWith(Parameterized.class)
    public static class Enclosed {
        @Parameterized.Parameter
        public String email;

        @Parameterized.Parameters
        public static List<String> emails() {
            List<String> emails = new ArrayList<>();
            Collections.addAll(emails, "test@@", "test@", "test@@test", "test");
            return emails;
        }

        @Test(expected = IllegalArgumentException.class)
        public void given_invalid_email_should_throw_exception() {
            new CustomerSource(null, email);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void missing_id_and_email_should_throw_exception() {
        new CustomerSource(null, null);
    }
}