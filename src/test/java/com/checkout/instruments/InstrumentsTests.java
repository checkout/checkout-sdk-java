package com.checkout.instruments;

import com.checkout.CheckoutApiException;
import com.checkout.CheckoutResourceNotFoundException;
import com.checkout.SandboxTestFixture;
import com.checkout.TestCardSource;
import com.checkout.common.Address;
import com.checkout.common.Phone;
import com.checkout.tokens.CardTokenRequest;
import com.checkout.tokens.CardTokenResponse;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class InstrumentsTests extends SandboxTestFixture {
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
        // Assert.assertNotNull(response.getIssuer()); TODO uncomment when fixed
        Assert.assertNotNull(response.getIssuerCountry());
        Assert.assertNotNull(response.getProductId());
        Assert.assertNotNull(response.getProductType());
    }

    @Test
    public void can_get_instrument() throws Exception {
        CardTokenResponse cardToken = getApi().tokensClient().requestAsync(createValidTokenRequest()).get();

        CreateInstrumentRequest request = CreateInstrumentRequest.builder()
                .type("token")
                .token(cardToken.getToken())
                .build();
        CreateInstrumentResponse createInstrumentResponse = getApi().instrumentsClient().createInstrument(request).get();

        InstrumentDetailsResponse instrument = getApi().instrumentsClient().getInstrument(createInstrumentResponse.getId()).get();
        Assert.assertNotNull(instrument);
        Assert.assertEquals(createInstrumentResponse.getBin(), instrument.getBin());
        Assert.assertEquals(createInstrumentResponse.getProductType(), instrument.getProductType());
        Assert.assertEquals(createInstrumentResponse.getProductId(), instrument.getProductId());
        Assert.assertEquals(createInstrumentResponse.getId(), instrument.getId());
        Assert.assertEquals(createInstrumentResponse.getType(), instrument.getType());
        Assert.assertEquals(createInstrumentResponse.getExpiryMonth(), instrument.getExpiryMonth());
        Assert.assertEquals(createInstrumentResponse.getExpiryYear(), instrument.getExpiryYear());
        Assert.assertEquals(createInstrumentResponse.getScheme(), instrument.getScheme());
        Assert.assertEquals(createInstrumentResponse.getLast4(), instrument.getLast4());
        Assert.assertEquals(createInstrumentResponse.getBin(), instrument.getBin());
        Assert.assertEquals(createInstrumentResponse.getCardType(), instrument.getCardType());
        Assert.assertEquals(createInstrumentResponse.getCardCategory(), instrument.getCardCategory());
        Assert.assertEquals(createInstrumentResponse.getIssuer(), instrument.getIssuer());
        Assert.assertEquals(createInstrumentResponse.getIssuerCountry(), instrument.getIssuerCountry());
        Assert.assertNotNull(instrument.getFingerprint());
        Assert.assertNotNull(instrument.getAccountHolder());
        Assert.assertNotNull(instrument.getAccountHolder().getBillingAddress());
        Assert.assertNotNull(instrument.getAccountHolder().getBillingAddress().getAddressLine1());
        Assert.assertNotNull(instrument.getAccountHolder().getBillingAddress().getAddressLine2());
        Assert.assertNotNull(instrument.getAccountHolder().getBillingAddress().getCity());
        Assert.assertNotNull(instrument.getAccountHolder().getBillingAddress().getCountry());
        Assert.assertNotNull(instrument.getAccountHolder().getBillingAddress().getState());
        Assert.assertNotNull(instrument.getAccountHolder().getBillingAddress().getZip());
        Assert.assertNotNull(instrument.getAccountHolder().getPhone());
        Assert.assertNotNull(instrument.getAccountHolder().getPhone().getCountryCode());
        Assert.assertNotNull(instrument.getAccountHolder().getPhone().getNumber());
        Assert.assertNotNull(instrument.getCustomer());
        Assert.assertNotNull(instrument.getCustomer().getId());
        Assert.assertNotNull(instrument.getCustomer().getEmail());
        Assert.assertTrue(instrument.getCustomer().isDefault());
    }

    @Test
    public void can_update_instrument() throws Exception {
        CardTokenResponse cardToken = getApi().tokensClient().requestAsync(createValidTokenRequest()).get();

        CreateInstrumentRequest request = CreateInstrumentRequest.builder()
                .type("token")
                .token(cardToken.getToken())
                .build();
        CreateInstrumentResponse response = getApi().instrumentsClient().createInstrument(request).get();
        UpdateInstrumentResponse updateResponse = getApi().instrumentsClient().updateInstrument(response.getId(), UpdateInstrumentRequest.builder()
                .name("Test")
                .build()).get();

        Assert.assertNotNull(updateResponse);
        Assert.assertNotNull(updateResponse.getFingerprint());
        Assert.assertNotNull(updateResponse.getType());

        InstrumentDetailsResponse instrument = getApi().instrumentsClient().getInstrument(response.getId()).get();
        Assert.assertEquals("Test", instrument.getName());
    }

    @Test(expected = CheckoutResourceNotFoundException.class)
    public void can_delete_instrument() throws Throwable {
        CardTokenResponse cardToken = getApi().tokensClient().requestAsync(createValidTokenRequest()).get();

        CreateInstrumentRequest request = CreateInstrumentRequest.builder()
                .type("token")
                .token(cardToken.getToken())
                .build();
        CreateInstrumentResponse response = getApi().instrumentsClient().createInstrument(request).get();

        getApi().instrumentsClient().deleteInstrument(response.getId()).get();

        try {
            getApi().instrumentsClient().getInstrument(response.getId()).get();
        } catch (ExecutionException e) {
            throw e.getCause();
        }
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