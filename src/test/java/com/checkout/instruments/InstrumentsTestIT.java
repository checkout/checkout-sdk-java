package com.checkout.instruments;

import com.checkout.CheckoutResourceNotFoundException;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestCardSource;
import com.checkout.common.Address;
import com.checkout.common.Phone;
import com.checkout.tokens.CardTokenRequest;
import com.checkout.tokens.CardTokenResponse;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InstrumentsTestIT extends SandboxTestFixture {

    public InstrumentsTestIT() {
        super(PlatformType.CLASSIC);
    }

    @Test
    public void can_create_instrument() throws Exception {
        final CardTokenResponse cardToken = getApi().tokensClient().requestAsync(createValidTokenRequest()).get();

        final CreateInstrumentRequest request = CreateInstrumentRequest.builder()
                .type("token")
                .token(cardToken.getToken())
                .build();
        final CreateInstrumentResponse response = getApi().instrumentsClient().createInstrument(request).get();

        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getType());
        assertNotNull(response.getBin());
        assertNotNull(response.getCardCategory());
        assertNotNull(response.getCardType());
        assertNotNull(response.getExpiryMonth());
        assertNotNull(response.getExpiryYear());
        assertNotNull(response.getIssuer());
        assertNotNull(response.getIssuerCountry());
        assertNotNull(response.getProductId());
        assertNotNull(response.getProductType());
    }

    @Test
    public void can_get_instrument() throws Exception {
        final CardTokenResponse cardToken = getApi().tokensClient().requestAsync(createValidTokenRequest()).get();

        final CreateInstrumentRequest request = CreateInstrumentRequest.builder()
                .type("token")
                .token(cardToken.getToken())
                .build();
        final CreateInstrumentResponse createInstrumentResponse = getApi().instrumentsClient().createInstrument(request).get();

        final InstrumentDetailsResponse instrument = getApi().instrumentsClient().getInstrument(createInstrumentResponse.getId()).get();
        assertNotNull(instrument);
        assertEquals(createInstrumentResponse.getBin(), instrument.getBin());
        assertEquals(createInstrumentResponse.getProductType(), instrument.getProductType());
        assertEquals(createInstrumentResponse.getProductId(), instrument.getProductId());
        assertEquals(createInstrumentResponse.getId(), instrument.getId());
        assertEquals(createInstrumentResponse.getType(), instrument.getType());
        assertEquals(createInstrumentResponse.getExpiryMonth(), instrument.getExpiryMonth());
        assertEquals(createInstrumentResponse.getExpiryYear(), instrument.getExpiryYear());
        assertEquals(createInstrumentResponse.getScheme(), instrument.getScheme());
        assertEquals(createInstrumentResponse.getLast4(), instrument.getLast4());
        assertEquals(createInstrumentResponse.getBin(), instrument.getBin());
        assertEquals(createInstrumentResponse.getCardType(), instrument.getCardType());
        assertEquals(createInstrumentResponse.getCardCategory(), instrument.getCardCategory());
        assertEquals(createInstrumentResponse.getIssuer(), instrument.getIssuer());
        assertEquals(createInstrumentResponse.getIssuerCountry(), instrument.getIssuerCountry());
        assertNotNull(instrument.getFingerprint());
        assertNotNull(instrument.getAccountHolder());
        assertNotNull(instrument.getAccountHolder().getBillingAddress());
        assertNotNull(instrument.getAccountHolder().getBillingAddress().getAddressLine1());
        assertNotNull(instrument.getAccountHolder().getBillingAddress().getAddressLine2());
        assertNotNull(instrument.getAccountHolder().getBillingAddress().getCity());
        assertNotNull(instrument.getAccountHolder().getBillingAddress().getCountry());
        assertNotNull(instrument.getAccountHolder().getBillingAddress().getState());
        assertNotNull(instrument.getAccountHolder().getBillingAddress().getZip());
        assertNotNull(instrument.getAccountHolder().getPhone());
        assertNotNull(instrument.getAccountHolder().getPhone().getCountryCode());
        assertNotNull(instrument.getAccountHolder().getPhone().getNumber());
        assertNotNull(instrument.getCustomer());
        assertNotNull(instrument.getCustomer().getId());
        assertNotNull(instrument.getCustomer().getEmail());
        assertTrue(instrument.getCustomer().isDefault());
    }

    @Test
    public void can_update_instrument() throws Exception {
        final CardTokenResponse cardToken = getApi().tokensClient().requestAsync(createValidTokenRequest()).get();

        final CreateInstrumentRequest request = CreateInstrumentRequest.builder()
                .type("token")
                .token(cardToken.getToken())
                .build();
        final CreateInstrumentResponse response = getApi().instrumentsClient().createInstrument(request).get();
        final UpdateInstrumentResponse updateResponse = getApi().instrumentsClient().updateInstrument(response.getId(), UpdateInstrumentRequest.builder()
                .name("Test")
                .build()).get();

        assertNotNull(updateResponse);
        assertNotNull(updateResponse.getFingerprint());
        assertNotNull(updateResponse.getType());

        final InstrumentDetailsResponse instrument = getApi().instrumentsClient().getInstrument(response.getId()).get();
        assertEquals("Test", instrument.getName());
    }

    @Test
    public void can_delete_instrument() throws ExecutionException, InterruptedException {
        final CardTokenResponse cardToken = getApi().tokensClient().requestAsync(createValidTokenRequest()).get();

        final CreateInstrumentRequest request = CreateInstrumentRequest.builder()
                .type("token")
                .token(cardToken.getToken())
                .build();
        final CreateInstrumentResponse response = getApi().instrumentsClient().createInstrument(request).get();

        try {
            getApi().instrumentsClient().getInstrument(response.getId()).get();
        } catch (final Exception e) {
            assertTrue(e.getCause() instanceof CheckoutResourceNotFoundException);
        }

    }

    private CardTokenRequest createValidTokenRequest() {
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