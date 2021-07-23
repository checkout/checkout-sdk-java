package com.checkout.payments;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(Parameterized.class)
public class TokenSourceTest {

    @Parameterized.Parameter
    public String invalidToken;

    @Parameterized.Parameters
    public static List<String> invalidTokens() {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, "", " ", null);
        return list;
    }

    @Test(expected = IllegalArgumentException.class)
    public void given_token_invalid_should_throw_exception() {
        new TokenSource(invalidToken);
    }
}