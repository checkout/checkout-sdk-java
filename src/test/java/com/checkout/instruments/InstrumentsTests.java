package com.checkout.instruments;

import com.checkout.ApiTestFixture;
import com.checkout.TestCardSource;
import com.checkout.common.Address;
import com.checkout.common.Phone;
import com.checkout.tokens.CardTokenRequest;
import com.checkout.tokens.CardTokenResponse;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class InstrumentsTests extends ApiTestFixture {
    @Test
    public void can_create_instrument() throws Exception {
        CardTokenResponse cardToken = getApi().tokensClient().requestAsync(createValidTokenRequest()).get();

        CreateInstrumentRequest request = CreateInstrumentRequest.builder()
                .type("token")
                .token(cardToken.getToken())
                .build();
        CreateInstrumentResponse response = getApi().instrumentsClient().createInstrument(request).get();

        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getId());
        Assert.assertNotNull(response.getType());
        Assert.assertNotNull(response.getBin());
        Assert.assertNotNull(response.getCardCategory());
        Assert.assertNotNull(response.getCardType());
        Assert.assertNotNull(response.getExpiryMonth());
        Assert.assertNotNull(response.getExpiryYear());
        Assert.assertNotNull(response.getIssuer());
        Assert.assertNotNull(response.getIssuerCountry());
        Assert.assertNotNull(response.getProductId());
        Assert.assertNotNull(response.getProductType());
    }

    private CardTokenRequest createValidTokenRequest() {
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