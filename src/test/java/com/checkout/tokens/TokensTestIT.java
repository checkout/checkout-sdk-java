package com.checkout.tokens;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestCardSource;
import com.checkout.common.Address;
import com.checkout.common.Phone;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TokensTestIT extends SandboxTestFixture {

    public TokensTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    public void can_tokenize_card() throws Exception {
        final CardTokenRequest request = createValidRequest();
        final CardTokenResponse token = getApi().tokensClient().requestAsync(request).get();

        assertNotNull(token);
        assertNotNull(token.getToken());
        assertNotEquals(token.getToken().trim(), "");
        assertTrue(token.getExpiresOn().isAfter(Instant.now()));
        assertNotNull(token.getBillingAddress());
        assertEquals(token.getBillingAddress().getAddressLine1(), request.getBillingAddress().getAddressLine1());
        assertEquals(token.getBillingAddress().getAddressLine2(), request.getBillingAddress().getAddressLine2());
        assertEquals(token.getBillingAddress().getCity(), request.getBillingAddress().getCity());
        assertEquals(token.getBillingAddress().getState(), request.getBillingAddress().getState());
        assertEquals(token.getBillingAddress().getZip(), request.getBillingAddress().getZip());
        assertEquals(token.getBillingAddress().getCountry(), request.getBillingAddress().getCountry());
        assertNotNull(token.getPhone());
        assertEquals(token.getPhone().getCountryCode(), request.getPhone().getCountryCode());
        assertEquals(token.getPhone().getNumber(), request.getPhone().getNumber());
        assertEquals(token.getType(), "card");
        assertEquals(token.getExpiryMonth(), request.getExpiryMonth());
        assertEquals(token.getExpiryYear(), request.getExpiryYear());
    }

    private CardTokenRequest createValidRequest() {
        final Address billingAddress = new Address();
        billingAddress.setAddressLine1("Checkout.com");
        billingAddress.setAddressLine2("90 Tottenham Court Road");
        billingAddress.setCity("London");
        billingAddress.setState("London");
        billingAddress.setZip("W1T 4TJ");
        billingAddress.setCountry("GB");

        final Phone phone = new Phone();
        phone.setCountryCode("44");
        phone.setNumber("020 222333");

        final CardTokenRequest request = new CardTokenRequest(TestCardSource.VISA.getNumber(), TestCardSource.VISA.getExpiryMonth(), TestCardSource.VISA.getExpiryYear());
        request.setCvv(TestCardSource.VISA.getCvv());
        request.setBillingAddress(billingAddress);
        request.setPhone(phone);

        return request;
    }
}