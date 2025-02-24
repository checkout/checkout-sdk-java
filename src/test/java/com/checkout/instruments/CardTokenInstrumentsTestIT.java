package com.checkout.instruments;

import com.checkout.CheckoutAuthorizationException;
import com.checkout.common.AccountHolder;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.Phone;
import com.checkout.common.UpdateCustomerRequest;
import com.checkout.customers.CustomerRequest;
import com.checkout.instruments.create.CreateCustomerInstrumentRequest;
import com.checkout.instruments.create.CreateInstrumentTokenRequest;
import com.checkout.instruments.create.CreateInstrumentTokenResponse;
import com.checkout.instruments.get.BankAccountFieldQuery;
import com.checkout.instruments.get.GetCardInstrumentResponse;
import com.checkout.instruments.get.GetInstrumentResponse;
import com.checkout.instruments.update.UpdateInstrumentCardRequest;
import com.checkout.instruments.update.UpdateInstrumentCardResponse;
import com.checkout.instruments.update.UpdateInstrumentTokenRequest;
import com.checkout.payments.AbstractPaymentsTestIT;
import org.junit.jupiter.api.Test;

import static com.checkout.TestHelper.generateRandomEmail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardTokenInstrumentsTestIT extends AbstractPaymentsTestIT {

    @Test
    void shouldCreateAndGetTokenInstrument() {

        final CreateInstrumentTokenResponse tokenInstrument = createTokenInstrument();

        final GetInstrumentResponse getResponse = blocking(() -> checkoutApi.instrumentsClient().get(tokenInstrument.getId()));

        final GetCardInstrumentResponse cardResponse = (GetCardInstrumentResponse) getResponse;

        assertNotNull(cardResponse);
        assertNotNull(cardResponse.getId());
        assertNotNull(cardResponse.getFingerprint());
        assertNotNull(cardResponse.getExpiryMonth());
        assertNotNull(cardResponse.getExpiryYear());
        assertNotNull(cardResponse.getScheme());
        assertNotNull(cardResponse.getLast4());
        assertNotNull(cardResponse.getBin());
        //assertNotNull(cardResponse.getIssuer());
        assertNotNull(cardResponse.getIssuerCountry());
        assertNotNull(cardResponse.getProductId());
        assertNotNull(cardResponse.getProductType());
        assertNotNull(cardResponse.getCustomer());
        assertNotNull(cardResponse.getAccountHolder());
        assertNotNull(cardResponse.getAccountHolder().getBillingAddress());
        assertNotNull(cardResponse.getAccountHolder().getPhone());
        assertNotNull(cardResponse.getCardType());
        assertNotNull(cardResponse.getCardCategory());


    }

    @Test
    void shouldUpdateTokenInstrument() {

        final CreateInstrumentTokenResponse tokenInstrument = createTokenInstrument();

        final String token = requestToken().getToken();
        assertNotNull(token);

        final UpdateInstrumentTokenRequest updateRequest = UpdateInstrumentTokenRequest.builder()
                .token(token)
                .build();

        final UpdateInstrumentCardResponse updateResponse = blocking(() -> checkoutApi.instrumentsClient().update(tokenInstrument.getId(), updateRequest));
        assertNotNull(updateResponse);
        assertNotNull(updateResponse.getFingerprint());

    }

    @Test
    void shouldUpdateCardInstrument() {

        final CreateInstrumentTokenResponse tokenInstrument = createTokenInstrument();

        final String token = requestToken().getToken();
        assertNotNull(token);

        final UpdateInstrumentCardRequest updateRequest = UpdateInstrumentCardRequest.builder()
                .expiryMonth(12)
                .expiryYear(2030)
                .name("John Doe")
                .customer(UpdateCustomerRequest.builder().id(tokenInstrument.getCustomer().getId()).defaultCustomer(true).build())
                .accountHolder(AccountHolder.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .phone(Phone.builder()
                                .countryCode("+1")
                                .number("415 555 2671")
                                .build())
                        .billingAddress(Address.builder()
                                .addressLine1("CheckoutSdk.com")
                                .addressLine2("90 Tottenham Court Road")
                                .city("London")
                                .state("London")
                                .zip("W1T 4TJ")
                                .country(CountryCode.GB)
                                .build())
                        .build())
                .build();

        final UpdateInstrumentCardResponse updateResponse = blocking(() -> checkoutApi.instrumentsClient().update(tokenInstrument.getId(), updateRequest));
        assertNotNull(updateResponse);
        assertNotNull(updateResponse.getFingerprint());

        final GetInstrumentResponse getResponse = blocking(() -> checkoutApi.instrumentsClient().get(tokenInstrument.getId()));
        final GetCardInstrumentResponse cardResponse = (GetCardInstrumentResponse) getResponse;

        assertNotNull(cardResponse);
        assertNotNull(cardResponse.getId());
        assertNotNull(cardResponse.getFingerprint());
        assertEquals(12, cardResponse.getExpiryMonth());
        assertEquals(2030, cardResponse.getExpiryYear());
        assertEquals("John", cardResponse.getAccountHolder().getFirstName());
        assertEquals("Doe", cardResponse.getAccountHolder().getLastName());
        assertTrue(cardResponse.getCustomer().isDefault());
        assertNotNull(cardResponse.getCardType());
        assertNotNull(cardResponse.getCardCategory());

    }

    @Test
    void shouldDeleteCardInstrument() {

        final CreateInstrumentTokenResponse response = createTokenInstrument();

        blocking(() -> checkoutApi.instrumentsClient().delete(response.getId()));

        assertNotFound(checkoutApi.instrumentsClient().get(response.getId()));

    }

    private CreateInstrumentTokenResponse createTokenInstrument() {

        final CustomerRequest customerRequest = CustomerRequest.builder()
                .email(generateRandomEmail())
                .name("Testing")
                .phone(Phone.builder()
                        .countryCode("1")
                        .number("4155552671")
                        .build())
                .build();

        final String customerId = blocking(() -> checkoutApi.customersClient().create(customerRequest)).getId();
        assertNotNull(customerId);

        return createTokenInstrument(customerId);
    }

    protected CreateInstrumentTokenResponse createTokenInstrument(final String customerId) {

        final String token = requestToken().getToken();
        assertNotNull(token);

        final CreateInstrumentTokenRequest request = CreateInstrumentTokenRequest.builder()
                .token(token)
                .accountHolder(AccountHolder.builder()
                        .firstName("John")
                        .lastName("Smith")
                        .phone(Phone.builder()
                                .countryCode("+1")
                                .number("415 555 2671")
                                .build())
                        .billingAddress(Address.builder()
                                .addressLine1("CheckoutSdk.com")
                                .addressLine2("90 Tottenham Court Road")
                                .city("London")
                                .state("London")
                                .zip("W1T 4TJ")
                                .country(CountryCode.GB)
                                .build())
                        .build())
                .customer(CreateCustomerInstrumentRequest.builder()
                        .id(customerId)
                        .build())
                .build();

        final CreateInstrumentTokenResponse response = blocking(() -> checkoutApi.instrumentsClient().create(request));
        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getFingerprint());
        assertNotNull(response.getExpiryMonth());
        assertNotNull(response.getExpiryYear());
        assertNotNull(response.getScheme());
        assertNotNull(response.getLast4());
        assertNotNull(response.getBin());
        //assertNotNull(response.getIssuer());
        assertNotNull(response.getIssuerCountry());
        assertNotNull(response.getProductId());
        assertNotNull(response.getProductType());
        assertNotNull(response.getCustomer());
        assertNotNull(response.getCardType());
        assertNotNull(response.getCardCategory());

        return response;

    }

    @Test
    void shouldFailGetBankAccountFieldFormattingWhenNoOAuthIsProvided() {
        try {
            checkoutApi.instrumentsClient().getBankAccountFieldFormatting(CountryCode.GB, Currency.GBP, BankAccountFieldQuery.builder().build());
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutAuthorizationException);
            assertEquals("Operation requires OAUTH authorization type", e.getMessage());
        }
    }

}