package com.checkout.tokens;

import com.checkout.SandboxTestFixture;
import com.checkout.TestCardSource;
import com.checkout.common.Address;
import com.checkout.common.Phone;
import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;

public class TokensTests extends SandboxTestFixture {
    @Test
    public void can_tokenize_card() throws Exception {
        CardTokenRequest request = createValidRequest();
        CardTokenResponse token = getApi().tokensClient().requestAsync(request).get();

        Assert.assertNotNull(token);
        Assert.assertNotNull(token.getToken());
        Assert.assertNotEquals(token.getToken().trim(), "");
        Assert.assertTrue(token.getExpiresOn().isAfter(Instant.now()));
        Assert.assertNotNull(token.getBillingAddress());
        Assert.assertEquals(token.getBillingAddress().getAddressLine1(), request.getBillingAddress().getAddressLine1());
        Assert.assertEquals(token.getBillingAddress().getAddressLine2(), request.getBillingAddress().getAddressLine2());
        Assert.assertEquals(token.getBillingAddress().getCity(), request.getBillingAddress().getCity());
        Assert.assertEquals(token.getBillingAddress().getState(), request.getBillingAddress().getState());
        Assert.assertEquals(token.getBillingAddress().getZip(), request.getBillingAddress().getZip());
        Assert.assertEquals(token.getBillingAddress().getCountry(), request.getBillingAddress().getCountry());
        Assert.assertNotNull(token.getPhone());
        Assert.assertEquals(token.getPhone().getCountryCode(), request.getPhone().getCountryCode());
        Assert.assertEquals(token.getPhone().getNumber(), request.getPhone().getNumber());
        Assert.assertEquals(token.getType(), "card");
        Assert.assertEquals(token.getExpiryMonth(), request.getExpiryMonth());
        Assert.assertEquals(token.getExpiryYear(), request.getExpiryYear());
    }

    private CardTokenRequest createValidRequest() {
        Address billingAddress = new Address();
        billingAddress.setAddressLine1("Checkout.com");
        billingAddress.setAddressLine2("90 Tottenham Court Road");
        billingAddress.setCity("London");
        billingAddress.setState("London");
        billingAddress.setZip("W1T 4TJ");
        billingAddress.setCountry("GB");

        Phone phone = new Phone();
        phone.setCountryCode("44");
        phone.setNumber("020 222333");

        CardTokenRequest request = new CardTokenRequest(TestCardSource.VISA.getNumber(), TestCardSource.VISA.getExpiryMonth(), TestCardSource.VISA.getExpiryYear());
        request.setCvv(TestCardSource.VISA.getCvv());
        request.setBillingAddress(billingAddress);
        request.setPhone(phone);

        return request;
    }
}