package com.checkout.accounts;

import com.checkout.CheckoutApi;
import com.checkout.CheckoutSdk;
import com.checkout.Environment;
import com.checkout.OAuthScope;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import com.checkout.accounts.reserverules.entities.HoldingDuration;
import com.checkout.accounts.reserverules.entities.RollingReserveRule;
import com.checkout.accounts.reserverules.responses.ReserveRuleCreateResponse;
import com.checkout.accounts.reserverules.responses.ReserveRuleRequest;
import com.checkout.accounts.reserverules.responses.ReserveRuleResponse;
import com.checkout.accounts.reserverules.responses.ReserveRulesResponse;
import com.checkout.accounts.files.request.FileUploadRequest;
import com.checkout.accounts.files.response.FileUploadResponse;
import com.checkout.accounts.files.response.FileDetailsResponse;
import com.checkout.accounts.files.entities.FilePurpose;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.IdResponse;
import com.checkout.common.InstrumentType;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

import static com.checkout.TestHelper.generateRandomEmail;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AccountsTestIT extends SandboxTestFixture {

    AccountsTestIT() {
        super(PlatformType.DEFAULT_OAUTH);
    }

    @Disabled("Recently giving a 503 with 'no healthy upstream' description from the API, disabled")
    @Test
    void shouldCreateGetAndUpdateOnboardIndividualEntity() {
        final String randomReference = RandomStringUtils.random(15, true, true);
        final OnboardEntityRequest onboardEntityRequest = buildIndividualEntity(randomReference);

        final OnboardEntityResponse entityResponse = blocking(() -> checkoutApi.accountsClient().createEntity(onboardEntityRequest, "2.0"));
        validateEntityCreationResponse(entityResponse, randomReference);

        final OnboardEntityDetailsResponse entityDetailsResponse = blocking(() -> checkoutApi.accountsClient().getEntity(entityResponse.getId(), "2.0"));
        validateIndividualEntityDetails(entityDetailsResponse, onboardEntityRequest, randomReference);

        onboardEntityRequest.getIndividual().setFirstName("Jhon");
        final OnboardEntityResponse updatedEntityResponse = blocking(() -> checkoutApi.accountsClient().updateEntity(onboardEntityRequest, entityResponse.getId(), "2.0"));
        assertNotNull(updatedEntityResponse);

        final OnboardEntityDetailsResponse verifyUpdated = blocking(() -> checkoutApi.accountsClient().getEntity(entityResponse.getId(), "2.0"));
        assertEquals(onboardEntityRequest.getIndividual().getFirstName(), verifyUpdated.getIndividual().getFirstName());
    }

    @Disabled("Recently giving a 503 with 'no healthy upstream' description from the API, disabled")
    @Test
    void shouldCreateGetAndUpdateOnboardCompanyEntity() {
        final String randomReference = RandomStringUtils.random(15, true, true);
        final OnboardEntityRequest onboardEntityRequest = buildCompanyEntity(randomReference);

        final OnboardEntityResponse entityResponse = blocking(() -> checkoutApi.accountsClient().createEntity(onboardEntityRequest, "2.0"));
        validateEntityCreationResponse(entityResponse, randomReference);

        final OnboardEntityDetailsResponse entityDetailsResponse = blocking(() -> checkoutApi.accountsClient().getEntity(entityResponse.getId(), "2.0"));
        validateCompanyEntityDetails(entityDetailsResponse, onboardEntityRequest, randomReference);
    }

    @Test
    void shouldUploadAccountsFile() throws URISyntaxException {
        final IdResponse fileResponse = uploadFile();
        validateFileUploadResponse(fileResponse);
    }

    @Disabled("Recently giving a 503 with 'no healthy upstream' description from the API, disabled")
    @Test
    void shouldUploadFileForEntity() {
        final String entityId = createTestEntity();
        final FileUploadRequest fileUploadRequest = FileUploadRequest.builder()
                .purpose(FilePurpose.IDENTITY_VERIFICATION)
                .build();

        final FileUploadResponse response = blocking(() -> checkoutApi.accountsClient().uploadFile(entityId, fileUploadRequest));
        validateFileUploadResponseForEntity(response);
    }

    @Test
    void shouldRetrieveFileForEntity() {
        final String entityId = createTestEntity();
        final FileUploadRequest fileUploadRequest = FileUploadRequest.builder()
                .purpose(FilePurpose.IDENTITY_VERIFICATION)
                .build();

        final FileUploadResponse uploadResponse = blocking(() -> checkoutApi.accountsClient().uploadFile(entityId, fileUploadRequest));
        validateFileUploadResponseForEntity(uploadResponse);

        final FileDetailsResponse detailsResponse = blocking(() -> checkoutApi.accountsClient().retrieveFile(entityId, uploadResponse.getId()));
        validateFileDetailsResponseForEntity(detailsResponse, uploadResponse.getId());
    }

    @Test
    void shouldCreateAndRetrievePaymentInstrument() throws URISyntaxException {
        final CheckoutApi checkoutApi = getAccountsCheckoutApi();

        final OnboardEntityResponse entityResponse = blocking(() -> checkoutApi.accountsClient()
                .createEntity(buildCompanyEntity(RandomStringUtils.random(15, true, true)), "2.0"));
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
    @Disabled("Recently giving a 503 with 'no healthy upstream' description from the API, disabled")
    @Test
    void shouldCreateGetAndUpdateOnboardIndividualEntitySync() {
        final String randomReference = RandomStringUtils.random(15, true, true);
        final OnboardEntityRequest onboardEntityRequest = buildIndividualEntity(randomReference);

        final OnboardEntityResponse entityResponse = checkoutApi.accountsClient().createEntitySync(onboardEntityRequest, "2.0");
        validateEntityCreationResponse(entityResponse, randomReference);

        final OnboardEntityDetailsResponse entityDetailsResponse = checkoutApi.accountsClient().getEntitySync(entityResponse.getId(), "2.0");
        validateIndividualEntityDetails(entityDetailsResponse, onboardEntityRequest, randomReference);

        onboardEntityRequest.getIndividual().setFirstName("Jhon");
        final OnboardEntityResponse updatedEntityResponse = checkoutApi.accountsClient().updateEntitySync(onboardEntityRequest, entityResponse.getId(), "2.0");
        assertNotNull(updatedEntityResponse);

        final OnboardEntityDetailsResponse verifyUpdated = checkoutApi.accountsClient().getEntitySync(entityResponse.getId(), "2.0");
        assertEquals(onboardEntityRequest.getIndividual().getFirstName(), verifyUpdated.getIndividual().getFirstName());
    }

    @Test
    @Disabled("Recently giving a 503 with 'no healthy upstream' description from the API, disabled")
    void shouldCreateGetAndUpdateOnboardCompanyEntitySync() {
        final String randomReference = RandomStringUtils.random(15, true, true);
        final OnboardEntityRequest onboardEntityRequest = buildCompanyEntity(randomReference);

        final OnboardEntityResponse entityResponse = checkoutApi.accountsClient().createEntitySync(onboardEntityRequest, "2.0");
        validateEntityCreationResponse(entityResponse, randomReference);

        final OnboardEntityDetailsResponse entityDetailsResponse = checkoutApi.accountsClient().getEntitySync(entityResponse.getId(), "2.0");
        validateCompanyEntityDetails(entityDetailsResponse, onboardEntityRequest, randomReference);
    }

    // ===================== Accounts API schema_version 3.0 =====================
    // v3.0 onboards a sub-entity as a company whose representatives carry nested `individual`
    // details + `roles`, plus a required `processing_details` object. These mirror the 2.0
    // onboarding tests above (which pin schema_version to "2.0"); here createEntity/getEntity/
    // updateEntity use the SDK default (3.0). They run through the accounts-scoped OAuth client,
    // which is the one provisioned for v3.0 onboarding.
    @Test
    void shouldCreateGetAndUpdateOnboardCompanyEntityV3() {
        final CheckoutApi checkoutApi = getAccountsCheckoutApi();
        final String randomReference = RandomStringUtils.random(15, true, true);
        final OnboardEntityRequest onboardEntityRequest = buildCompanyEntityV3(randomReference);

        final OnboardEntityResponse entityResponse = blocking(() -> checkoutApi.accountsClient().createEntity(onboardEntityRequest));
        validateEntityCreationResponse(entityResponse, randomReference);

        final OnboardEntityDetailsResponse entityDetailsResponse = blocking(() -> checkoutApi.accountsClient().getEntity(entityResponse.getId()));
        validateCompanyEntityDetails(entityDetailsResponse, onboardEntityRequest, randomReference);

        final OnboardEntityResponse updatedEntityResponse = blocking(() -> checkoutApi.accountsClient().updateEntity(onboardEntityRequest, entityResponse.getId()));
        assertNotNull(updatedEntityResponse);
        assertNotNull(updatedEntityResponse.getId());
    }

    @Test
    void shouldCreateGetAndUpdateOnboardCompanyEntitySyncV3() {
        final CheckoutApi checkoutApi = getAccountsCheckoutApi();
        final String randomReference = RandomStringUtils.random(15, true, true);
        final OnboardEntityRequest onboardEntityRequest = buildCompanyEntityV3(randomReference);

        final OnboardEntityResponse entityResponse = checkoutApi.accountsClient().createEntitySync(onboardEntityRequest);
        validateEntityCreationResponse(entityResponse, randomReference);

        final OnboardEntityDetailsResponse entityDetailsResponse = checkoutApi.accountsClient().getEntitySync(entityResponse.getId());
        validateCompanyEntityDetails(entityDetailsResponse, onboardEntityRequest, randomReference);

        final OnboardEntityResponse updatedEntityResponse = checkoutApi.accountsClient().updateEntitySync(onboardEntityRequest, entityResponse.getId());
        assertNotNull(updatedEntityResponse);
        assertNotNull(updatedEntityResponse.getId());
    }

    @Test
    void shouldCreateAndRetrievePaymentInstrumentV3() throws URISyntaxException {
        final CheckoutApi checkoutApi = getAccountsCheckoutApi();

        final OnboardEntityResponse entityResponse = blocking(() -> checkoutApi.accountsClient()
                .createEntity(buildCompanyEntityV3(RandomStringUtils.random(15, true, true))));
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

    @Test
    void shouldCreateAndRetrievePaymentInstrumentSyncV3() throws URISyntaxException {
        final CheckoutApi checkoutApi = getAccountsCheckoutApi();

        final OnboardEntityResponse entityResponse = checkoutApi.accountsClient()
                .createEntitySync(buildCompanyEntityV3(RandomStringUtils.random(15, true, true)));
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

    @Test
    void shouldUploadAccountsFileSync() throws URISyntaxException {
        final IdResponse fileResponse = uploadFileSync();
        validateFileUploadResponse(fileResponse);
    }

    @Disabled("Recently giving a 503 with 'no healthy upstream' description from the API, disabled")
    @Test
    void shouldCreateAndRetrievePaymentInstrumentSync() throws URISyntaxException {
        final CheckoutApi checkoutApi = getAccountsCheckoutApi();

        final OnboardEntityResponse entityResponse = checkoutApi.accountsClient()
                .createEntitySync(buildCompanyEntity(RandomStringUtils.random(15, true, true)), "2.0");
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

    @Test
    void shouldUploadFileForEntitySync() {
        final String entityId = createTestEntity();
        final FileUploadRequest fileUploadRequest = FileUploadRequest.builder()
                .purpose(FilePurpose.IDENTITY_VERIFICATION)
                .build();

        final FileUploadResponse response = checkoutApi.accountsClient().uploadFileSync(entityId, fileUploadRequest);
        validateFileUploadResponseForEntity(response);
    }

    @Test
    void shouldRetrieveFileForEntitySync() {
        final String entityId = createTestEntity();
        final FileUploadRequest fileUploadRequest = FileUploadRequest.builder()
                .purpose(FilePurpose.IDENTITY_VERIFICATION)
                .build();

        final FileUploadResponse uploadResponse = checkoutApi.accountsClient().uploadFileSync(entityId, fileUploadRequest);
        validateFileUploadResponseForEntity(uploadResponse);

        final FileDetailsResponse detailsResponse = checkoutApi.accountsClient().retrieveFileSync(entityId, uploadResponse.getId());
        validateFileDetailsResponseForEntity(detailsResponse, uploadResponse.getId());
    }

    @Test
    void shouldGetEntityMembers() {
        final String entityId = createTestEntity();

        final EntityMembersResponse response = blocking(() -> checkoutApi.accountsClient().getEntityMembers(entityId));
        validateEntityMembersResponse(response);
    }

    @Test
    void shouldReinviteEntityMember() {
        final String entityId = createTestEntity();
        
        // Get entity members first to find a valid user ID
        final EntityMembersResponse membersResponse = blocking(() -> checkoutApi.accountsClient().getEntityMembers(entityId));
        validateEntityMembersResponse(membersResponse);
        
        // Skip test if no members found
        if (membersResponse.getData() == null || membersResponse.getData().isEmpty()) {
            System.out.println("Skipping reinvite test - no entity members found");
            return;
        }
        
        final String userId = membersResponse.getData().get(0).getUserId();
        assertNotNull(userId, "First entity member should have a user ID");

        final EntityMemberResponse response = blocking(() -> checkoutApi.accountsClient().reinviteEntityMember(entityId, userId));
        validateEntityMemberResponse(response);
    }

    @Test
    void shouldCreateGetAndUpdateReserveRule() {
        final String entityId = createTestEntity();
        final ReserveRuleRequest createRequest = buildReserveRuleRequest();

        final ReserveRuleCreateResponse createResponse = blocking(() -> checkoutApi.accountsClient().createReserveRule(entityId, createRequest));
        validateReserveRuleCreateResponse(createResponse);

        final ReserveRuleResponse getResponse = blocking(() -> checkoutApi.accountsClient().getReserveRule(entityId, createResponse.getId()));
        validateReserveRuleResponse(getResponse);

        // Get Etag from the creation response headers
        String etag = null;            
        if (createResponse != null)
        {
            etag = createResponse.getEtag();
        }

        // Update (with the If-Match header when using the etag)
        final ReserveRuleRequest updateRequest = buildReserveRuleRequestWithEtag(etag); // Set the Etag for concurrency control
        final ReserveRuleCreateResponse updateResponse = blocking(() -> checkoutApi.accountsClient().updateReserveRule(entityId, createResponse.getId(), updateRequest));
        validateReserveRuleCreateResponse(updateResponse);
    }

    @Test
    void shouldGetReserveRules() {
        final String entityId = createTestEntity();

        final ReserveRulesResponse response = blocking(() -> checkoutApi.accountsClient().getReserveRules(entityId));
        validateReserveRulesResponse(response);
    }

    @Disabled("Requires a sub-entity with pending requirements")
    @Test
    void shouldGetEntityRequirements() {
        final String entityId = Objects.requireNonNull(System.getenv("CHECKOUT_DEFAULT_ENTITY_ID"));

        final EntityRequirementListResponse response = blocking(() -> checkoutApi.accountsClient().getEntityRequirements(entityId, "2.0"));
        validateEntityRequirementListResponse(response);
    }

    @Disabled("Requires a sub-entity with a known requirement id")
    @Test
    void shouldGetEntityRequirementDetails() {
        final String entityId = Objects.requireNonNull(System.getenv("CHECKOUT_DEFAULT_ENTITY_ID"));
        final EntityRequirementListResponse listResponse = blocking(() -> checkoutApi.accountsClient().getEntityRequirements(entityId, "2.0"));
        assertNotNull(listResponse.getData());
        assertNotNull(listResponse.getData().get(0));

        final String requirementId = listResponse.getData().get(0).getId();
        final EntityRequirementDetailsResponse detailsResponse = blocking(() -> checkoutApi.accountsClient().getEntityRequirementDetails(entityId, requirementId));
        validateEntityRequirementDetailsResponse(detailsResponse, requirementId);
    }

    @Disabled("Requires a sub-entity with a resolvable requirement")
    @Test
    void shouldResolveEntityRequirement() {
        final String entityId = Objects.requireNonNull(System.getenv("CHECKOUT_DEFAULT_ENTITY_ID"));
        final EntityRequirementListResponse listResponse = blocking(() -> checkoutApi.accountsClient().getEntityRequirements(entityId, "2.0"));
        assertNotNull(listResponse.getData());
        assertNotNull(listResponse.getData().get(0));

        final String requirementId = listResponse.getData().get(0).getId();
        final EntityRequirementUpdateRequest updateRequest = EntityRequirementUpdateRequest.builder()
                .value("Acme Holdings Limited")
                .build();
        final EntityRequirementUpdateResponse response = blocking(() -> checkoutApi.accountsClient().resolveEntityRequirement(entityId, requirementId, updateRequest));
        validateEntityRequirementUpdateResponse(response, requirementId);
    }

    // Synchronous methods
    @Test
    void shouldGetEntityMembersSync() {
        final String entityId = createTestEntity();

        final EntityMembersResponse response = checkoutApi.accountsClient().getEntityMembersSync(entityId);
        validateEntityMembersResponse(response);
    }

    @Test
    void shouldReinviteEntityMemberSync() {
        final String entityId = createTestEntity();
        
        // Get entity members first to find a valid user ID
        final EntityMembersResponse membersResponse = checkoutApi.accountsClient().getEntityMembersSync(entityId);
        validateEntityMembersResponse(membersResponse);
        
        // Skip test if no members found
        if (membersResponse.getData() == null || membersResponse.getData().isEmpty()) {
            System.out.println("Skipping reinvite sync test - no entity members found");
            return;
        }
        
        final String userId = membersResponse.getData().get(0).getUserId();
        assertNotNull(userId, "First entity member should have a user ID");

        final EntityMemberResponse response = checkoutApi.accountsClient().reinviteEntityMemberSync(entityId, userId);
        validateEntityMemberResponse(response);
    }

    @Test
    void shouldCreateGetAndUpdateReserveRuleSync() {
        final String entityId = createTestEntity();
        final ReserveRuleRequest createRequest = buildReserveRuleRequest();

        final ReserveRuleCreateResponse createResponse = checkoutApi.accountsClient().createReserveRuleSync(entityId, createRequest);
        validateReserveRuleCreateResponse(createResponse);

        final ReserveRuleResponse getResponse = checkoutApi.accountsClient().getReserveRuleSync(entityId, createResponse.getId());
        validateReserveRuleResponse(getResponse);

        // Get Etag from the creation response headers
        String etag = null;            
        if (createResponse != null)
        {
            etag = createResponse.getEtag();
        }

        // Update (with the If-Match header when using the etag)
        final ReserveRuleRequest updateRequest = buildReserveRuleRequestWithEtag(etag); // Set the Etag for concurrency control
        final ReserveRuleCreateResponse updateResponse = checkoutApi.accountsClient().updateReserveRuleSync(entityId, createResponse.getId(), updateRequest);
        validateReserveRuleCreateResponse(updateResponse);
    }

    @Test
    void shouldGetReserveRulesSync() {
        final String entityId = createTestEntity();

        final ReserveRulesResponse response = checkoutApi.accountsClient().getReserveRulesSync(entityId);
        validateReserveRulesResponse(response);
    }

    // Common methods
    private String createTestEntity() {
        final String randomReference = RandomStringUtils.random(15, true, true);
        final OnboardEntityRequest entityRequest = OnboardEntityRequest.builder()
                .reference(randomReference)
                .contactDetails(ContactDetails.builder()
                        .phone(buildAccountPhone())
                        .emailAddresses(EntityEmailAddresses.builder()
                                .primary(generateRandomEmail())
                                .build())
                        .build())
                .profile(buildProfile())
                .company(Company.builder()
                        .businessRegistrationNumber("01234567")
                        .legalName("Reserve Rules Test Inc.")
                        .tradingName("Reserve Rules Test")
                        .principalAddress(TestHelper.createAddress())
                        .registeredAddress(TestHelper.createAddress())
                        .representatives(Collections.singletonList(Representative.builder()
                                .firstName("John")
                                .lastName("Doe")
                                .address(TestHelper.createAddress())
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
                                        .country(CountryCode.GB)
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

        final OnboardEntityResponse entityResponse = blocking(() -> checkoutApi.accountsClient().createEntity(entityRequest, "2.0"));
        assertNotNull(entityResponse);
        assertNotNull(entityResponse.getId());
        return entityResponse.getId();
    }

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

    private void validateFileUploadResponseForEntity(final FileUploadResponse fileResponse) {
        assertNotNull(fileResponse);
        assertNotNull(fileResponse.getId());
        assertNotNull(fileResponse.getMaximumSizeInBytes());
        assertNotNull(fileResponse.getDocumentTypesForPurpose());
        assertNotNull(fileResponse.getLinks());
    }

    private void validateFileDetailsResponseForEntity(final FileDetailsResponse fileDetailsResponse, final String expectedFileId) {
        assertNotNull(fileDetailsResponse);
        assertEquals(expectedFileId, fileDetailsResponse.getId());
        assertNotNull(fileDetailsResponse.getStatus());
        assertNotNull(fileDetailsResponse.getPurpose());
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

    // Builds an onboarding request that conforms to the Accounts API v3.0 schema: the representative
    // carries nested `individual` details + `roles`, the company has `businessType` +
    // `dateOfIncorporation`, and `processingDetails` is populated. The holding-currency scope is
    // configured on the platform (USD here) while `processingDetails.currency` reflects the
    // sub-entity region (GBP) — the two are independent.
    private static OnboardEntityRequest buildCompanyEntityV3(final String randomReference) {
        final Address address = Address.builder()
                .addressLine1("90 Tottenham Court Road")
                .city("London")
                .zip("W1T4TJ")
                .country(CountryCode.GB)
                .build();

        return OnboardEntityRequest.builder()
                .reference(randomReference)
                .contactDetails(ContactDetails.builder()
                        .phone(AccountPhone.builder()
                                .countryCode(CountryCode.GB)
                                .number("2345678910")
                                .build())
                        .emailAddresses(EntityEmailAddresses.builder()
                                .primary(generateRandomEmail())
                                .build())
                        .build())
                .profile(Profile.builder()
                        .urls(Collections.singletonList("https://www.superheroexample.com"))
                        .mccs(Collections.singletonList("0742"))
                        .defaultHoldingCurrency(Currency.USD)
                        .holdingCurrencies(Collections.singletonList(Currency.USD))
                        .build())
                .company(Company.builder()
                        .businessRegistrationNumber("01234567")
                        .businessType(BusinessType.LIMITED_COMPANY)
                        .legalName("Super Hero Masks Inc.")
                        .tradingName("Super Hero Masks")
                        .dateOfIncorporation(DateOfIncorporation.builder()
                                .day(1)
                                .month(6)
                                .year(2010)
                                .build())
                        .principalAddress(address)
                        .registeredAddress(address)
                        .representatives(Collections.singletonList(Representative.builder()
                                .individual(RepresentativeIndividual.builder()
                                        .firstName("John")
                                        .lastName("Doe")
                                        .dateOfBirth(DateOfBirth.builder()
                                                .day(5)
                                                .month(6)
                                                .year(1995)
                                                .build())
                                        .placeOfBirth(PlaceOfBirth.builder()
                                                .country(CountryCode.GB)
                                                .build())
                                        .address(address)
                                        .build())
                                .roles(Arrays.asList(
                                        EntityRoles.UBO,
                                        EntityRoles.AUTHORISED_SIGNATORY,
                                        EntityRoles.DIRECTOR,
                                        EntityRoles.CONTROL_PERSON))
                                .build()))
                        .build())
                .processingDetails(ProcessingDetails.builder()
                        .annualProcessingVolume(1000000)
                        .averageTransactionValue(5000)
                        .averageOrderFulfillmentTime(3)
                        .highestTransactionValue(25000)
                        .currency(Currency.GBP)
                        .settlementCountry(CountryCode.GB.name())
                        .targetCountries(Collections.singletonList(CountryCode.GB.name()))
                        .payments(ProcessingDetailsPayments.builder()
                                .ach(ProcessingDetailsAch.builder()
                                        .annualAchVolume(1000000)
                                        .averageAchTransactionSize(5000)
                                        .estimatedMonthlyCreditVolume(100000)
                                        .averageCreditAmount(5000)
                                        .build())
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
                // The sandbox OAuth clients are not provisioned for the merchant-specific subdomain, so
                // the token request would come back invalid_client. Opting out explicitly until they are.
                .useLegacyDomain()
                .build();
    }

    private static void validateEntityMembersResponse(final EntityMembersResponse response) {
        assertNotNull(response);
        assertNotNull(response.getData());
    }

    private static void validateEntityMemberResponse(final EntityMemberResponse response) {
        assertNotNull(response);
        assertNotNull(response.getUserId());
    }

    // Reserve Rules builders and validators
    private static ReserveRuleRequest buildReserveRuleRequest() {
        return ReserveRuleRequest.builder()
                .type("rolling")
                .validFrom(java.time.Instant.now().plusSeconds(3600)) // 1 hour from now
                .rolling(RollingReserveRule.builder()
                        .percentage(10.0)
                        .holdingDuration(HoldingDuration.builder()
                                .weeks(2)
                                .build())
                        .build())
                .build();
    }

    private static ReserveRuleRequest buildReserveRuleRequestWithEtag(String etag) {
        return ReserveRuleRequest.builder()
                .type("rolling")
                .validFrom(java.time.Instant.now().plusSeconds(3600)) // 1 hour from now
                .rolling(RollingReserveRule.builder()
                        .percentage(10.0)
                        .holdingDuration(HoldingDuration.builder()
                                .weeks(2)
                                .build())
                        .build())
                .ifMatch(etag)
                .build();
    }

    private static void validateReserveRuleCreateResponse(final ReserveRuleCreateResponse response) {
        assertNotNull(response);
        assertNotNull(response.getId());
    }

    private static void validateReserveRuleResponse(final ReserveRuleResponse response) {
        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getType());
        assertNotNull(response.getValidFrom());
        assertNotNull(response.getRolling());
    }

    private static void validateReserveRulesResponse(final ReserveRulesResponse response) {
        assertNotNull(response);
        assertNotNull(response.getData());
    }

    private static void validateEntityRequirementListResponse(final EntityRequirementListResponse response) {
        assertNotNull(response);
        assertNotNull(response.getData());
    }

    private static void validateEntityRequirementDetailsResponse(final EntityRequirementDetailsResponse response, final String requirementId) {
        assertNotNull(response);
        assertEquals(requirementId, response.getId());
        assertNotNull(response.getResource());
    }

    private static void validateEntityRequirementUpdateResponse(final EntityRequirementUpdateResponse response, final String requirementId) {
        assertNotNull(response);
        assertEquals(requirementId, response.getId());
        assertEquals(EntityRequirementUpdateStatus.PROCESSING, response.getStatus());
        assertNotNull(response.getSubmittedAt());
    }

}