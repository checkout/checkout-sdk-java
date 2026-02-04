package com.checkout.instruments;

import com.checkout.EmptyResponse;
import com.checkout.common.AccountHolder;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.Phone;
import com.checkout.customers.CustomerRequest;
import com.checkout.instruments.create.CreateCustomerInstrumentRequest;
import com.checkout.instruments.create.CreateInstrumentTokenRequest;
import com.checkout.instruments.create.CreateInstrumentTokenResponse;
import com.checkout.instruments.get.BankAccountFieldQuery;
import com.checkout.instruments.get.BankAccountFieldResponse;
import com.checkout.instruments.get.GetInstrumentResponse;
import com.checkout.instruments.update.UpdateInstrumentCardRequest;
import com.checkout.instruments.update.UpdateInstrumentCardResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.checkout.TestHelper.generateRandomEmail;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InstrumentsTestIT extends CardTokenInstrumentsTestIT {

    @Test
    void shouldCreateAndGetInstrument() {
        final String customerId = createCustomer();
        
        final CreateInstrumentTokenRequest request = createTokenInstrumentRequest(customerId);
        final CreateInstrumentTokenResponse createResponse = blocking(() -> checkoutApi.instrumentsClient().create(request));
        validateInstrumentCreation(createResponse);
        
        final GetInstrumentResponse getResponse = blocking(() -> checkoutApi.instrumentsClient().get(createResponse.getId()));
        validateInstrumentResponse(getResponse);
    }

    @Test
    void shouldUpdateCardInstrument() {
        final String customerId = createCustomer();
        
        final CreateInstrumentTokenRequest request = createTokenInstrumentRequest(customerId);
        final CreateInstrumentTokenResponse createResponse = blocking(() -> checkoutApi.instrumentsClient().create(request));
        validateInstrumentCreation(createResponse);

        final UpdateInstrumentCardRequest updateRequest = setupCardUpdateRequest();
        final UpdateInstrumentCardResponse updateResponse = blocking(() -> checkoutApi.instrumentsClient().update(createResponse.getId(), updateRequest));
        validateInstrumentUpdateResponse(updateResponse);
    }

    @Test
    void shouldDeleteInstrument() {
        final String customerId = createCustomer();
        
        final CreateInstrumentTokenRequest request = createTokenInstrumentRequest(customerId);
        final CreateInstrumentTokenResponse createResponse = blocking(() -> checkoutApi.instrumentsClient().create(request));
        validateInstrumentCreation(createResponse);

        final EmptyResponse deleteResponse = blocking(() -> checkoutApi.instrumentsClient().delete(createResponse.getId()));
        assertNotNull(deleteResponse);
        
        assertNotFound(checkoutApi.instrumentsClient().get(createResponse.getId()));
    }

    @Test
    @Disabled("Requires OAuth configuration")
    void shouldGetBankAccountFieldFormatting() {
        final BankAccountFieldQuery query = setupBankAccountFieldQuery();
        
        final BankAccountFieldResponse response = blocking(() -> checkoutApi.instrumentsClient().getBankAccountFieldFormatting(CountryCode.GB, Currency.GBP, query));
        assertNotNull(response);
    }

    // Synchronous test methods
    @Test
    void shouldCreateAndGetInstrumentSync() {
        final String customerId = createCustomer();
        
        final CreateInstrumentTokenRequest request = createTokenInstrumentRequest(customerId);
        final CreateInstrumentTokenResponse createResponse = checkoutApi.instrumentsClient().createSync(request);
        validateInstrumentCreation(createResponse);
        
        final GetInstrumentResponse getResponse = checkoutApi.instrumentsClient().getSync(createResponse.getId());
        validateInstrumentResponse(getResponse);
    }

    @Test
    void shouldUpdateCardInstrumentSync() {
        final String customerId = createCustomer();
        
        final CreateInstrumentTokenRequest request = createTokenInstrumentRequest(customerId);
        final CreateInstrumentTokenResponse createResponse = checkoutApi.instrumentsClient().createSync(request);
        validateInstrumentCreation(createResponse);

        final UpdateInstrumentCardRequest updateRequest = setupCardUpdateRequest();
        final UpdateInstrumentCardResponse updateResponse = checkoutApi.instrumentsClient().updateSync(createResponse.getId(), updateRequest);
        validateInstrumentUpdateResponse(updateResponse);
    }

    @Test
    void shouldDeleteInstrumentSync() {
        final String customerId = createCustomer();
        
        final CreateInstrumentTokenRequest request = createTokenInstrumentRequest(customerId);
        final CreateInstrumentTokenResponse createResponse = checkoutApi.instrumentsClient().createSync(request);
        validateInstrumentCreation(createResponse);

        final EmptyResponse deleteResponse = checkoutApi.instrumentsClient().deleteSync(createResponse.getId());
        assertNotNull(deleteResponse);
        
        assertNotFound(checkoutApi.instrumentsClient().get(createResponse.getId()));
    }

    @Test
    @Disabled("Requires OAuth configuration")
    void shouldGetBankAccountFieldFormattingSync() {
        final BankAccountFieldQuery query = setupBankAccountFieldQuery();
        
        final BankAccountFieldResponse response = checkoutApi.instrumentsClient().getBankAccountFieldFormattingSync(CountryCode.GB, Currency.GBP, query);
        assertNotNull(response);
    }

    // Common methods for setup and validation
    private String createCustomer() {
        final CustomerRequest customerRequest = CustomerRequest.builder()
                .email(generateRandomEmail())
                .name("Instruments Test Customer")
                .build();
        return blocking(() -> checkoutApi.customersClient().create(customerRequest)).getId();
    }

    private CreateInstrumentTokenRequest createTokenInstrumentRequest(String customerId) {
        final String token = requestToken().getToken();
        return CreateInstrumentTokenRequest.builder()
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
    }

    private UpdateInstrumentCardRequest setupCardUpdateRequest() {
        return UpdateInstrumentCardRequest.builder()
                .name("Updated Test Card")
                .expiryMonth(12)
                .expiryYear(2030)
                .build();
    }

    private BankAccountFieldQuery setupBankAccountFieldQuery() {
        return BankAccountFieldQuery.builder().build();
    }

    private void validateInstrumentCreation(CreateInstrumentTokenResponse response) {
        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getType());
    }

    private void validateInstrumentResponse(GetInstrumentResponse response) {
        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getFingerprint());
    }

    private void validateInstrumentUpdateResponse(UpdateInstrumentCardResponse response) {
        assertNotNull(response);
        assertNotNull(response.getType());
        assertNotNull(response.getFingerprint());
    }
}