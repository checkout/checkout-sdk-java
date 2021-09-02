package com.checkout.tokens;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestCardSource;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
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
    public void shouldRequestCardToken() {

        final Address billingAddress = Address.builder()
                .addressLine1("Checkout.com")
                .addressLine2("90 Tottenham Court Road")
                .city("London")
                .state("London")
                .zip("W1T 4TJ")
                .country(CountryCode.GB)
                .build();

        final Phone phone = Phone.builder().countryCode("44").number("020 222333").build();

        final CardTokenRequest request = CardTokenRequest.builder()
                .number(TestCardSource.VISA.getNumber())
                .expiryMonth(TestCardSource.VISA.getExpiryMonth())
                .expiryYear(TestCardSource.VISA.getExpiryYear())
                .cvv(TestCardSource.VISA.getCvv())
                .billingAddress(billingAddress)
                .phone(phone)
                .build();

        final CardTokenResponse cardTokenResponse = blocking(getApi().tokensClient().requestAsync(request));

        assertNotNull(cardTokenResponse);
        assertNotNull(cardTokenResponse.getToken());
        assertEquals(cardTokenResponse.getType(), "card");
        assertEquals(cardTokenResponse.getExpiryMonth(), request.getExpiryMonth());
        assertEquals(cardTokenResponse.getExpiryYear(), request.getExpiryYear());
        assertNotEquals(cardTokenResponse.getToken().trim(), "");
        assertTrue(cardTokenResponse.getExpiresOn().isAfter(Instant.now()));
        assertNotNull(cardTokenResponse.getBillingAddress());
        assertEquals(cardTokenResponse.getBillingAddress().getAddressLine1(), request.getBillingAddress().getAddressLine1());
        assertEquals(cardTokenResponse.getBillingAddress().getAddressLine2(), request.getBillingAddress().getAddressLine2());
        assertEquals(cardTokenResponse.getBillingAddress().getCity(), request.getBillingAddress().getCity());
        assertEquals(cardTokenResponse.getBillingAddress().getState(), request.getBillingAddress().getState());
        assertEquals(cardTokenResponse.getBillingAddress().getZip(), request.getBillingAddress().getZip());
        assertEquals(cardTokenResponse.getBillingAddress().getCountry(), request.getBillingAddress().getCountry());
        assertNotNull(cardTokenResponse.getPhone());
        assertEquals(cardTokenResponse.getPhone().getCountryCode(), request.getPhone().getCountryCode());
        assertEquals(cardTokenResponse.getPhone().getNumber(), request.getPhone().getNumber());

    }

}