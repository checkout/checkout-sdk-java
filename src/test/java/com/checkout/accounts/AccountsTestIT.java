package com.checkout.accounts;

import com.checkout.CheckoutApi;
import com.checkout.CheckoutSdk;
import com.checkout.Environment;
import com.checkout.OAuthScope;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.IdResponse;
import com.checkout.common.InstrumentType;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;

import static com.checkout.TestHelper.generateRandomEmail;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AccountsTestIT extends SandboxTestFixture {

    AccountsTestIT() {
        super(PlatformType.DEFAULT_OAUTH);
    }

    @Test
    void shouldCreateGetAndUpdateOnboardIndividualEntity() {
        final String randomReference = RandomStringUtils.random(15, true, true);
        final OnboardEntityRequest onboardEntityRequest = buildIndividualEntity(randomReference);

        final OnboardEntityResponse entityResponse = blocking(() -> checkoutApi.accountsClient().createEntity(onboardEntityRequest));
        validateEntityCreationResponse(entityResponse, randomReference);

        final OnboardEntityDetailsResponse entityDetailsResponse = blocking(() -> checkoutApi.accountsClient().getEntity(entityResponse.getId()));
        validateIndividualEntityDetails(entityDetailsResponse, onboardEntityRequest, randomReference);

        onboardEntityRequest.getIndividual().setFirstName("Jhon");
        final OnboardEntityResponse updatedEntityResponse = blocking(() -> checkoutApi.accountsClient().updateEntity(onboardEntityRequest, entityResponse.getId()));
        assertNotNull(updatedEntityResponse);

        final OnboardEntityDetailsResponse verifyUpdated = blocking(() -> checkoutApi.accountsClient().getEntity(entityResponse.getId()));
        assertEquals(onboardEntityRequest.getIndividual().getFirstName(), verifyUpdated.getIndividual().getFirstName());
    }

    @Test
    void shouldCreateGetAndUpdateOnboardCompanyEntity() {
        final String randomReference = RandomStringUtils.random(15, true, true);
        final OnboardEntityRequest onboardEntityRequest = buildCompanyEntity(randomReference);

        final OnboardEntityResponse entityResponse = blocking(() -> checkoutApi.accountsClient().createEntity(onboardEntityRequest));
        validateEntityCreationResponse(entityResponse, randomReference);

        final OnboardEntityDetailsResponse entityDetailsResponse = blocking(() -> checkoutApi.accountsClient().getEntity(entityResponse.getId()));
        validateCompanyEntityDetails(entityDetailsResponse, onboardEntityRequest, randomReference);
    }

    @Test
    void shouldUploadAccountsFile() throws URISyntaxException {
        final IdResponse fileResponse = uploadFile();
        validateFileUploadResponse(fileResponse);
    }

    @Test
    void shouldCreateAndRetrievePaymentInstrument() throws URISyntaxException {
        final CheckoutApi checkoutApi = getAccountsCheckoutApi();

        final OnboardEntityResponse entityResponse = blocking(() -> checkoutApi.accountsClient()
                .createEntity(buildCompanyEntity(RandomStringUtils.random(15, true, true))));
        assertNotNull(entityResponse);
        assertNotNull(entityResponse.getId());

        final IdResponse file = uploadFile();
        final PaymentInstrumentRequest instrumentRequest = buildPaymentInstrumentRequest(file.getId());

        final IdResponse instrumentResponse = blocking(() -> checkoutApi.accountsClient().createPaymentInstrument(entityResponse.getId(), instrumentRequest));
        assertNotNull(instrumentResponse);
        assertNotNull(instrumentResponse.getId());

        final PaymentInstrumentDetailsResponse instrumentDetailsResponse = blocking(() -> checkoutApi.accountsClient().retrievePaymentInstrumentDetails(entityResponse.getId(), instrumentResponse.getId()));
        validatePaymentInstrumentDetailsResponse(instrumentDetailsResponse);
    }

    // Synchronous methods
    @Test
    void shouldCreateGetAndUpdateOnboardIndividualEntitySync() {
        final String randomReference = RandomStringUtils.random(15, true, true);
        final OnboardEntityRequest onboardEntityRequest = buildIndividualEntity(randomReference);

        final OnboardEntityResponse entityResponse = checkoutApi.accountsClient().createEntitySync(onboardEntityRequest);
        validateEntityCreationResponse(entityResponse, randomReference);

        final OnboardEntityDetailsResponse entityDetailsResponse = checkoutApi.accountsClient().getEntitySync(entityResponse.getId());
        validateIndividualEntityDetails(entityDetailsResponse, onboardEntityRequest, randomReference);

        onboardEntityRequest.getIndividual().setFirstName("Jhon");
        final OnboardEntityResponse updatedEntityResponse = checkoutApi.accountsClient().updateEntitySync(onboardEntityRequest, entityResponse.getId());
        assertNotNull(updatedEntityResponse);

        final OnboardEntityDetailsResponse verifyUpdated = checkoutApi.accountsClient().getEntitySync(entityResponse.getId());
        assertEquals(onboardEntityRequest.getIndividual().getFirstName(), verifyUpdated.getIndividual().getFirstName());
    }

    @Test
    void shouldCreateGetAndUpdateOnboardCompanyEntitySync() {
        final String randomReference = RandomStringUtils.random(15, true, true);
        final OnboardEntityRequest onboardEntityRequest = buildCompanyEntity(randomReference);

        final OnboardEntityResponse entityResponse = checkoutApi.accountsClient().createEntitySync(onboardEntityRequest);
        validateEntityCreationResponse(entityResponse, randomReference);

        final OnboardEntityDetailsResponse entityDetailsResponse = checkoutApi.accountsClient().getEntitySync(entityResponse.getId());
        validateCompanyEntityDetails(entityDetailsResponse, onboardEntityRequest, randomReference);
    }

    @Test
    void shouldUploadAccountsFileSync() throws URISyntaxException {
        final IdResponse fileResponse = uploadFileSync();
        validateFileUploadResponse(fileResponse);
    }

    @Test
    void shouldCreateAndRetrievePaymentInstrumentSync() throws URISyntaxException {
        final CheckoutApi checkoutApi = getAccountsCheckoutApi();

        final OnboardEntityResponse entityResponse = checkoutApi.accountsClient()
                .createEntitySync(buildCompanyEntity(RandomStringUtils.random(15, true, true)));
        assertNotNull(entityResponse);
        assertNotNull(entityResponse.getId());

        final IdResponse file = uploadFileSync();
        final PaymentInstrumentRequest instrumentRequest = buildPaymentInstrumentRequest(file.getId());

        final IdResponse instrumentResponse = checkoutApi.accountsClient().createPaymentInstrumentSync(entityResponse.getId(), instrumentRequest);
        assertNotNull(instrumentResponse);
        assertNotNull(instrumentResponse.getId());

        final PaymentInstrumentDetailsResponse instrumentDetailsResponse = checkoutApi.accountsClient().retrievePaymentInstrumentDetailsSync(entityResponse.getId(), instrumentResponse.getId());
        validatePaymentInstrumentDetailsResponse(instrumentDetailsResponse);
    }

    // Common methods
    private IdResponse uploadFileSync() throws URISyntaxException {
        final URL resource = getClass().getClassLoader().getResource("checkout.jpeg");
        final File file = new File(resource.toURI());
        final AccountsFileRequest fileRequest = AccountsFileRequest.builder()
                .file(file)
                .contentType(ContentType.IMAGE_JPEG)
                .purpose(AccountsFilePurpose.BANK_VERIFICATION)
                .build();
        final IdResponse fileResponse = checkoutApi.accountsClient().submitFileSync(fileRequest);
        assertNotNull(fileResponse);
        assertNotNull(fileResponse.getId());
        return fileResponse;
    }

    // Common methods
    private OnboardEntityRequest buildIndividualEntity(final String randomReference) {
        return OnboardEntityRequest.builder()
                .reference(randomReference)
                .contactDetails(ContactDetails.builder()
                        .phone(buildAccountPhone())
                        .emailAddresses(EntityEmailAddresses.builder()
                                .primary(generateRandomEmail())
                                .build())
                        .build())
                .profile(buildProfile())
                .individual(Individual.builder()
                        .firstName("Bruce")
                        .lastName("Wayne")
                        .tradingName("Batman's Super Hero Masks")
                        .registeredAddress(TestHelper.createAddress())
                        .nationalTaxId("TAX123456")
                        .dateOfBirth(DateOfBirth.builder()
                                .day(5)
                                .month(6)
                                .year(1995)
                                .build())
                        .placeOfBirth(PlaceOfBirth.builder()
                                .country(CountryCode.GB)
                                .build())
                        .identification(Identification.builder()
                                .nationalIdNumber("AB123456C")
                                .build())
                        .build())
                .build();
    }

    private PaymentInstrumentRequest buildPaymentInstrumentRequest(final String fileId) {
        return PaymentInstrumentRequest.builder()
                .label("Barclays")
                .type(InstrumentType.BANK_ACCOUNT)
                .currency(Currency.GBP)
                .country(CountryCode.GB)
                .defaultDestination(false)
                .instrumentDetails(InstrumentDetailsFasterPayments.builder()
                        .accountNumber("12334454")
                        .bankCode("050389")
                        .build())
                .document(InstrumentDocument.builder()
                        .type("bank_statement")
                        .fileId(fileId)
                        .build())
                .build();
    }

    private void validateEntityCreationResponse(final OnboardEntityResponse entityResponse, final String expectedReference) {
        assertNotNull(entityResponse);
        assertNotNull(entityResponse.getId());
        assertEquals(expectedReference, entityResponse.getReference());
    }

    private void validateIndividualEntityDetails(final OnboardEntityDetailsResponse entityDetailsResponse, 
                                               final OnboardEntityRequest onboardEntityRequest, 
                                               final String expectedReference) {
        assertNotNull(entityDetailsResponse);
        assertNotNull(entityDetailsResponse.getId());
        assertEquals(expectedReference, entityDetailsResponse.getReference());
        assertEquals(onboardEntityRequest.getContactDetails(), entityDetailsResponse.getContactDetails());
        assertNotNull(entityDetailsResponse.getIndividual());
        assertEquals(onboardEntityRequest.getIndividual().getFirstName(), entityDetailsResponse.getIndividual().getFirstName());
        assertEquals(onboardEntityRequest.getIndividual().getTradingName(), entityDetailsResponse.getIndividual().getTradingName());
        assertEquals(onboardEntityRequest.getIndividual().getNationalTaxId(), entityDetailsResponse.getIndividual().getNationalTaxId());
        assertEquals(onboardEntityRequest.getIndividual().getDateOfBirth(), entityDetailsResponse.getIndividual().getDateOfBirth());
    }

    private void validateCompanyEntityDetails(final OnboardEntityDetailsResponse entityDetailsResponse, 
                                            final OnboardEntityRequest onboardEntityRequest, 
                                            final String expectedReference) {
        assertNotNull(entityDetailsResponse);
        assertNotNull(entityDetailsResponse.getId());
        assertEquals(expectedReference, entityDetailsResponse.getReference());
        assertEquals(onboardEntityRequest.getContactDetails(), entityDetailsResponse.getContactDetails());
        assertNotNull(entityDetailsResponse.getCompany());
        assertEquals(onboardEntityRequest.getCompany().getBusinessType(), entityDetailsResponse.getCompany().getBusinessType());
        assertEquals(onboardEntityRequest.getCompany().getLegalName(), entityDetailsResponse.getCompany().getLegalName());
        assertEquals(onboardEntityRequest.getCompany().getTradingName(), entityDetailsResponse.getCompany().getTradingName());
    }

    private void validateFileUploadResponse(final IdResponse fileResponse) {
        assertNotNull(fileResponse);
        assertNotNull(fileResponse.getId());
    }

    private void validatePaymentInstrumentDetailsResponse(final PaymentInstrumentDetailsResponse instrumentDetailsResponse) {
        assertNotNull(instrumentDetailsResponse);
        assertNotNull(instrumentDetailsResponse.getId());
        assertNotNull(instrumentDetailsResponse.getStatus());
        assertNotNull(instrumentDetailsResponse.getLabel());
        assertNotNull(instrumentDetailsResponse.getType());
        assertNotNull(instrumentDetailsResponse.getCurrency());
        assertNotNull(instrumentDetailsResponse.getCountry());
        assertNotNull(instrumentDetailsResponse.getDocument());
    }

    private static Profile buildProfile() {
        return Profile.builder()
                .urls(Collections.singletonList("https://www.superheroexample.com"))
                .mccs(Collections.singletonList("0742"))
                .build();
    }

    private static AccountPhone buildAccountPhone() {
        return AccountPhone.builder()
                .number("2345678910")
                .build();
    }

    private static OnboardEntityRequest buildCompanyEntity(final String randomReference) {
        final Address address = Address.builder()
                .addressLine1("90 Tottenham Court Road")
                .city("London")
                .zip("W1T4TJ")
                .country(CountryCode.GB)
                .build();

        return OnboardEntityRequest.builder()
                .reference(randomReference)
                .contactDetails(ContactDetails.builder()
                        .phone(buildAccountPhone())
                        .build())
                .profile(buildProfile())
                .company(Company.builder()
                        .businessRegistrationNumber("01234567")
                        .legalName("Super Hero Masks Inc.")
                        .tradingName("Super Hero Masks")
                        .principalAddress(address)
                        .registeredAddress(address)
                        .representatives(Collections.singletonList(Representative.builder()
                                .firstName("John")
                                .lastName("Doe")
                                .address(address)
                                .identification(Identification.builder()
                                        .nationalIdNumber("AB123456C")
                                        .build())
                                .phone(buildAccountPhone())
                                .dateOfBirth(DateOfBirth.builder()
                                        .day(5)
                                        .month(6)
                                        .year(1995)
                                        .build())
                                .placeOfBirth(PlaceOfBirth.builder()
                                        .country(CountryCode.ES)
                                        .build())
                                .roles(Collections.singletonList(EntityRoles.UBO))
                                .build()))
                        .financialDetails(EntityFinancialDetails.builder()
                                .annualProcessingVolume(120000L)
                                .averageTransactionValue(10000L)
                                .highestTransactionValue(2500L)
                                .build())

                        .build())
                .build();
    }

    private IdResponse uploadFile() throws URISyntaxException {
        final URL resource = getClass().getClassLoader().getResource("checkout.jpeg");
        final File file = new File(resource.toURI());
        final AccountsFileRequest fileRequest = AccountsFileRequest.builder()
                .file(file)
                .contentType(ContentType.IMAGE_JPEG)
                .purpose(AccountsFilePurpose.BANK_VERIFICATION)
                .build();
        final IdResponse fileResponse = blocking(() -> checkoutApi.accountsClient().submitFile(fileRequest));
        assertNotNull(fileResponse);
        assertNotNull(fileResponse.getId());
        return fileResponse;
    }

    private CheckoutApi getAccountsCheckoutApi() {
        return CheckoutSdk.builder()
                .oAuth()
                .clientCredentials(
                        requireNonNull(System.getenv("CHECKOUT_DEFAULT_OAUTH_ACCOUNTS_CLIENT_ID")),
                        requireNonNull(System.getenv("CHECKOUT_DEFAULT_OAUTH_ACCOUNTS_CLIENT_SECRET")))
                .scopes(OAuthScope.ACCOUNTS)
                .environment(Environment.SANDBOX)
                .build();
    }

}