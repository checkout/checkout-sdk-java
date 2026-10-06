package com.checkout.accounts;

import com.checkout.GsonSerializer;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.DocumentType;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountsV3SerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeProcessingDetailsWithPayments() {
        final ProcessingDetails processingDetails = ProcessingDetails.builder()
                .annualProcessingVolume(1000000)
                .averageTransactionValue(5000)
                .averageOrderFulfillmentTime(3)
                .highestTransactionValue(25000)
                .currency(Currency.GBP)
                .settlementCountry("GB")
                .targetCountries(Collections.singletonList("GB"))
                .payments(ProcessingDetailsPayments.builder()
                        .ach(ProcessingDetailsAch.builder()
                                .annualAchVolume(1000000)
                                .averageAchTransactionSize(5000)
                                .estimatedMonthlyCreditVolume(100000)
                                .averageCreditAmount(5000)
                                .build())
                        .build())
                .build();

        final String json = serializer.toJson(processingDetails);

        assertEquals(JsonParser.parseString("{\"settlement_country\":\"GB\",\"target_countries\":[\"GB\"],"
                        + "\"annual_processing_volume\":1000000,\"average_transaction_value\":5000,"
                        + "\"highest_transaction_value\":25000,\"currency\":\"GBP\","
                        + "\"average_order_fulfillment_time\":3,"
                        + "\"payments\":{\"ach\":{\"annual_ach_volume\":1000000,\"average_ach_transaction_size\":5000,"
                        + "\"estimated_monthly_credit_volume\":100000,\"average_credit_amount\":5000}}}"),
                JsonParser.parseString(json), json);
        assertEquals(processingDetails, serializer.fromJson(json, ProcessingDetails.class));
    }

    @Test
    void shouldSerializeAgreedTerms() {
        final AgreedTerms agreedTerms = AgreedTerms.builder()
                .date("2026-07-20T10:00:00Z")
                .ipAddress("203.0.113.42")
                .name("John Representative")
                .email("john@example.com")
                .version("1.0")
                .build();

        final String json = serializer.toJson(agreedTerms);

        assertTrue(json.contains("\"date\""));
        assertTrue(json.contains("\"ip_address\""));
        assertTrue(json.contains("\"name\""));
        assertTrue(json.contains("\"email\""));
        assertTrue(json.contains("\"version\""));
    }

    @Test
    void shouldSerializeCompanyV3Fields() {
        final Company company = Company.builder()
                .legalName("Super Hero Masks Inc.")
                .tradingName("Super Hero Masks")
                .businessRegistrationNumber("01234567")
                .businessType(BusinessType.LIMITED_COMPANY)
                .additionalTradingNames(Collections.singletonList("SHM"))
                .isRegisteredCompany(true)
                .dateOfIncorporation(DateOfIncorporation.builder().day(1).month(6).year(2010).build())
                .build();

        final String json = serializer.toJson(company);

        assertTrue(json.contains("\"additional_trading_names\""));
        assertTrue(json.contains("\"is_registered_company\""));
        assertTrue(json.contains("\"business_type\""));
        assertTrue(json.contains("limited_company"));
        assertTrue(json.contains("\"date_of_incorporation\""));
        assertTrue(json.contains("\"day\""));
    }

    @Test
    void shouldSerializeRepresentativeV3Fields() {
        final Representative representative = Representative.builder()
                .id("rep_r2y49v5j1skna5zx0swaprf2he")
                .individual(RepresentativeIndividual.builder()
                        .firstName("John")
                        .middleName("Paul")
                        .lastName("Representative")
                        .dateOfBirth(DateOfBirth.builder().day(5).month(6).year(1995).build())
                        .placeOfBirth(PlaceOfBirth.builder().country(CountryCode.US).build())
                        .citizenships(Collections.singletonList(Citizenship.builder()
                                .type("citizenship")
                                .country(CountryCode.US)
                                .build()))
                        .nationalIdType(NationalIdType.SSN)
                        .nationalIdNumber("AB123456C")
                        .emailAddress("john@example.com")
                        .phone(AccountPhone.builder().countryCode(CountryCode.US).number("4155678901").build())
                        .address(Address.builder()
                                .addressLine1("123 Main Street")
                                .city("San Francisco")
                                .state("CA")
                                .zip("94105")
                                .country(CountryCode.US)
                                .build())
                        .build())
                .companyPosition(CompanyPosition.CEO)
                .ownershipPercentage(100)
                .roles(Arrays.asList(EntityRoles.UBO, EntityRoles.AUTHORISED_SIGNATORY, EntityRoles.DIRECTOR, EntityRoles.CONTROL_PERSON))
                .build();

        final String json = serializer.toJson(representative);

        assertEquals(JsonParser.parseString("{\"id\":\"rep_r2y49v5j1skna5zx0swaprf2he\","
                        + "\"roles\":[\"ubo\",\"authorised_signatory\",\"director\",\"control_person\"],"
                        + "\"company_position\":\"ceo\",\"ownership_percentage\":100,"
                        + "\"individual\":{\"first_name\":\"John\",\"middle_name\":\"Paul\",\"last_name\":\"Representative\","
                        + "\"date_of_birth\":{\"day\":5,\"month\":6,\"year\":1995},"
                        + "\"place_of_birth\":{\"country\":\"US\"},"
                        + "\"citizenships\":[{\"type\":\"citizenship\",\"country\":\"US\"}],"
                        + "\"national_id_type\":\"ssn\",\"national_id_number\":\"AB123456C\","
                        + "\"email_address\":\"john@example.com\","
                        + "\"phone\":{\"country_code\":\"US\",\"number\":\"4155678901\"},"
                        + "\"address\":{\"address_line1\":\"123 Main Street\",\"city\":\"San Francisco\","
                        + "\"state\":\"CA\",\"zip\":\"94105\",\"country\":\"US\"}}}"),
                JsonParser.parseString(json), json);
        assertEquals(representative, serializer.fromJson(json, Representative.class));
    }

    @Test
    void shouldSerializeFinancialStatementsDocument() {
        final OnboardSubEntityDocuments documents = OnboardSubEntityDocuments.builder()
                .financialStatements(FinancialStatements.builder()
                        .type(FinancialStatementsType.FINANCIAL_STATEMENTS)
                        .front("file_3g7msixotdi2bfqpuwrckgnotu")
                        .build())
                .build();

        final String json = serializer.toJson(documents);

        assertTrue(json.contains("\"financial_statements\""));
        assertTrue(json.contains("financial_statements"));
        assertTrue(json.contains("\"front\""));
    }

    @Test
    void shouldSerializeAndRoundtripEntityEmailAddressesWithPciComplianceContact() {
        final EntityEmailAddresses emailAddresses = EntityEmailAddresses.builder()
                .primary("admin@example.com")
                .pciComplianceContact("pci@example.com")
                .build();

        final String json = serializer.toJson(emailAddresses);

        assertTrue(json.contains("\"primary\""));
        assertTrue(json.contains("\"pci_compliance_contact\""));

        final EntityEmailAddresses roundtrip = serializer.fromJson(json, EntityEmailAddresses.class);

        assertEquals("admin@example.com", roundtrip.getPrimary());
        assertEquals("pci@example.com", roundtrip.getPciComplianceContact());
        assertEquals(emailAddresses, roundtrip);
    }

    @Test
    void shouldSerializeOnboardEntityRequestWithAgreedTermsAndSellerCategory() {
        final OnboardEntityRequest request = OnboardEntityRequest.builder()
                .reference("ref_1")
                .sellerCategory("cat_electronics")
                .agreedTerms(AgreedTerms.builder().date("2026-07-20T10:00:00Z").version("1.0").build())
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"seller_category\""));
        assertTrue(json.contains("\"agreed_terms\""));
    }

    @Test
    void shouldDeserializeNewEnumValuesToExactSwaggerStrings() {
        assertEquals(EntityRoles.DIRECTOR, serializer.fromJson("\"director\"", EntityRoles.class));
        assertEquals(EntityRoles.CONTROL_PERSON, serializer.fromJson("\"control_person\"", EntityRoles.class));
        assertEquals(BusinessType.INDIVIDUAL_OR_SOLE_PROPRIETORSHIP, serializer.fromJson("\"individual_or_sole_proprietorship\"", BusinessType.class));
        assertEquals(BusinessType.GOVERNMENT_AGENCY, serializer.fromJson("\"government_agency\"", BusinessType.class));
        assertEquals(BusinessType.SEC_REGISTERED_ENTITY, serializer.fromJson("\"sec_registered_entity\"", BusinessType.class));
        assertEquals(CompanyPosition.CEO, serializer.fromJson("\"ceo\"", CompanyPosition.class));
        assertEquals(CompanyPosition.OTHER_NON_EXECUTIVE_NON_SENIOR, serializer.fromJson("\"other_non_executive_non_senior\"", CompanyPosition.class));
    }

    // ------------------------------------------------------------------------
    // Controlling company representative
    // EEA and GB Company Full (3.0) allow a representative that is a company:
    // { id, company: { legal_name, trading_name, registered_address },
    // ownership_percentage }. The field was not modelled.
    // ------------------------------------------------------------------------

    @Test
    void shouldSerializeControllingCompanyRepresentative() {
        final Representative representative = Representative.builder()
                .company(Company.builder()
                        .legalName("Parent Holdings Ltd")
                        .tradingName("Parent Holdings")
                        .registeredAddress(Address.builder()
                                .addressLine1("1 Main Street")
                                .city("London")
                                .zip("W1T 4TJ")
                                .country(CountryCode.GB)
                                .build())
                        .build())
                .ownershipPercentage(60)
                .build();

        assertEquals(JsonParser.parseString("{\"ownership_percentage\":60,\"company\":{"
                        + "\"legal_name\":\"Parent Holdings Ltd\",\"trading_name\":\"Parent Holdings\","
                        + "\"registered_address\":{\"address_line1\":\"1 Main Street\",\"city\":\"London\","
                        + "\"zip\":\"W1T 4TJ\",\"country\":\"GB\"}}}"),
                JsonParser.parseString(serializer.toJson(representative)));
    }

    // ------------------------------------------------------------------------
    // OnboardEntityDetailsResponse
    // GET /accounts/entities/{id} returns documents and processing_details; neither
    // was modelled, so the top-level documents could not be read back.
    // ------------------------------------------------------------------------

    @Test
    void shouldDeserializeEntityDetailsDocumentsAndProcessingDetails() {
        final OnboardEntityDetailsResponse response = serializer.fromJson("{"
                        + "\"id\":\"ent_qx5bjrdqes9rxo9xi8fym2by6o\","
                        + "\"processing_details\":{\"currency\":\"USD\",\"annual_processing_volume\":1000000},"
                        + "\"documents\":{\"bank_verification\":{\"type\":\"bank_statement\","
                        + "\"front\":\"file_bankverificationaaaaaaaaaa\"}},"
                        + "\"company\":{\"representatives\":[{\"documents\":{\"proof_of_registration\":"
                        + "{\"type\":\"extract_from_trade_register\",\"front\":\"file_proofofregistrationaaaaaaa\"}}}]}}",
                OnboardEntityDetailsResponse.class);

        assertEquals(Currency.USD, response.getProcessingDetails().getCurrency());
        assertEquals(Long.valueOf(1000000), response.getProcessingDetails().getAnnualProcessingVolume());
        assertEquals(BankVerificationType.BANK_STATEMENT, response.getDocuments().getBankVerification().getType());
        assertEquals("file_bankverificationaaaaaaaaaa", response.getDocuments().getBankVerification().getFront());
        assertEquals(ProofOfRegistrationType.EXTRACT_FROM_TRADE_REGISTER, response.getCompany().getRepresentatives()
                .get(0).getDocuments().getProofOfRegistration().getType());
    }

    // Regression: processing_details amounts are integers in minor units with no maximum. Typed
    // as Integer, any value above 2,147,483,647 (about 21.4 million in a two-decimal currency)
    // made the whole GET /accounts/entities/{id} fail to deserialize.
    @Test
    void shouldDeserializeProcessingDetailsAmountsAboveIntegerRange() {
        final OnboardEntityDetailsResponse response = serializer.fromJson("{\"processing_details\":{"
                        + "\"annual_processing_volume\":3000000000,"
                        + "\"average_transaction_value\":2500000000,"
                        + "\"highest_transaction_value\":9000000000}}",
                OnboardEntityDetailsResponse.class);

        assertEquals(Long.valueOf(3000000000L), response.getProcessingDetails().getAnnualProcessingVolume());
        assertEquals(Long.valueOf(2500000000L), response.getProcessingDetails().getAverageTransactionValue());
        assertEquals(Long.valueOf(9000000000L), response.getProcessingDetails().getHighestTransactionValue());
    }

    // ------------------------------------------------------------------------
    // AccountsFilePurpose
    // submitFile sends getPurpose() on the wire, so every value is asserted as a string.
    // ------------------------------------------------------------------------

    @Test
    void shouldExposeEveryAccountsFilePurposeWireValue() {
        final Map<AccountsFilePurpose, String> expected = new EnumMap<>(AccountsFilePurpose.class);
        expected.put(AccountsFilePurpose.BANK_VERIFICATION, "bank_verification");
        expected.put(AccountsFilePurpose.IDENTIFICATION, "identification");
        expected.put(AccountsFilePurpose.IDENTITY_VERIFICATION, "identity_verification");
        expected.put(AccountsFilePurpose.COMPANY_VERIFICATION, "company_verification");
        expected.put(AccountsFilePurpose.FINANCIAL_VERIFICATION, "financial_verification");
        expected.put(AccountsFilePurpose.TAX_VERIFICATION, "tax_verification");
        expected.put(AccountsFilePurpose.ADDITIONAL_DOCUMENT, "additional_document");
        expected.put(AccountsFilePurpose.ARTICLES_OF_ASSOCIATION, "articles_of_association");
        expected.put(AccountsFilePurpose.CERTIFIED_AUTHORISED_SIGNATORY, "certified_authorised_signatory");
        expected.put(AccountsFilePurpose.COMPANY_OWNERSHIP, "company_ownership");
        expected.put(AccountsFilePurpose.PROOF_OF_LEGALITY, "proof_of_legality");
        expected.put(AccountsFilePurpose.PROOF_OF_PRINCIPAL_ADDRESS, "proof_of_principal_address");
        expected.put(AccountsFilePurpose.SHAREHOLDER_STRUCTURE, "shareholder_structure");
        expected.put(AccountsFilePurpose.PROOF_OF_RESIDENTIAL_ADDRESS, "proof_of_residential_address");
        expected.put(AccountsFilePurpose.PROOF_OF_REGISTRATION, "proof_of_registration");

        assertEquals(AccountsFilePurpose.values().length, expected.size(), "every value must be asserted");
        expected.forEach((purpose, wire) -> assertEquals(wire, purpose.getPurpose(), purpose.name()));
    }

    // ------------------------------------------------------------------------
    // Spec request examples (USISVSellerCompany3-0, USISVSellerSoleTrader3-0)
    // Each example is deserialized into OnboardEntityRequest and serialized back,
    // and the two JSON trees must match exactly.
    // ------------------------------------------------------------------------

    @Test
    void shouldRoundTripUsIsvSellerCompanyExample() {
        final String example = "{"
                + "\"reference\":\"isv-seller-example001\","
                + "\"agreed_terms\":{\"date\":\"2026-07-02T10:30:00.0000000+00:00\",\"ip_address\":\"8.8.8.8\","
                + "\"name\":\"Toby Arden\",\"email\":\"toby.arden@example.com\",\"version\":\"cko-platform-terms-1.0.0\"},"
                + "\"seller_category\":\"cat_retail_001\","
                + "\"processing_details\":{\"annual_processing_volume\":1000,\"average_transaction_value\":2000,"
                + "\"average_order_fulfillment_time\":3,\"target_countries\":[\"US\"],\"currency\":\"USD\","
                + "\"payments\":{\"ach\":{\"annual_ach_volume\":100000,\"average_ach_transaction_size\":5000,"
                + "\"estimated_monthly_credit_volume\":50000,\"average_credit_amount\":2500}}},"
                + "\"contact_details\":{\"phone\":{\"number\":\"4155678900\",\"country_code\":\"US\"},"
                + "\"email_addresses\":{\"primary\":\"toby.arden@example.com\","
                + "\"pci_compliance_contact\":\"pci.contact@example.com\"}},"
                + "\"profile\":{\"urls\":[\"https://www.isv-seller-example.com\"],\"mccs\":[\"5551\"],"
                + "\"holding_currencies\":[\"USD\"],\"default_holding_currency\":\"USD\"},"
                + "\"company\":{\"business_registration_number\":\"12-3456789\",\"business_type\":\"private_corporation\","
                + "\"legal_name\":\"ISV Seller Example Inc\",\"trading_name\":\"ISV Seller Example\","
                + "\"registered_address\":{\"address_line1\":\"123 Main Street\",\"city\":\"San Francisco\",\"state\":\"CA\","
                + "\"zip\":\"94105\",\"country\":\"US\"},\"principal_address\":{\"address_line1\":\"123 Main Street\","
                + "\"city\":\"San Francisco\",\"state\":\"CA\",\"zip\":\"94105\",\"country\":\"US\"},"
                + "\"date_of_incorporation\":{\"year\":2025,\"month\":10,\"day\":1},\"representatives\":[{\"roles\":[\"ubo\","
                + "\"control_person\"],\"ownership_percentage\":25,\"company_position\":\"ceo\","
                + "\"individual\":{\"first_name\":\"Toby\",\"last_name\":\"Arden\",\"email_address\":\"toby.arden@example.com\","
                + "\"national_id_type\":\"ssn\",\"national_id_number\":\"123456789\",\"date_of_birth\":{\"day\":15,\"month\":1,"
                + "\"year\":1990},\"place_of_birth\":{\"country\":\"US\"},\"citizenships\":[{\"country\":\"US\"}],"
                + "\"phone\":{\"country_code\":\"US\",\"number\":\"4155678901\"},"
                + "\"address\":{\"address_line1\":\"123 Main Street\",\"city\":\"San Francisco\",\"state\":\"CA\",\"zip\":\"94105\","
                + "\"country\":\"US\"}}},{\"roles\":[\"authorised_signatory\"],\"individual\":{\"first_name\":\"Alex\","
                + "\"last_name\":\"Morgan\",\"email_address\":\"alex.morgan@example.com\",\"national_id_type\":\"ssn\","
                + "\"national_id_number\":\"987654321\",\"date_of_birth\":{\"day\":22,\"month\":6,\"year\":1985},"
                + "\"place_of_birth\":{\"country\":\"US\"},\"citizenships\":[{\"country\":\"US\"}],"
                + "\"phone\":{\"country_code\":\"US\",\"number\":\"4155678902\"},"
                + "\"address\":{\"address_line1\":\"123 Main Street\",\"city\":\"San Francisco\",\"state\":\"CA\",\"zip\":\"94105\","
                + "\"country\":\"US\"}}}]}"
                + "}";

        final JsonObject actual = JsonParser.parseString(
                serializer.toJson(serializer.fromJson(example, OnboardEntityRequest.class))).getAsJsonObject();
        // is_draft is a primitive boolean on the request, so it is always serialized.
        actual.remove("is_draft");

        assertEquals(JsonParser.parseString(example), actual);
    }

    @Test
    void shouldRoundTripUsIsvSellerSoleTraderExample() {
        final String example = "{"
                + "\"reference\":\"isv-sole-trader-example001\","
                + "\"agreed_terms\":{\"date\":\"2026-07-02T10:30:00.0000000+00:00\",\"ip_address\":\"8.8.8.8\","
                + "\"name\":\"Hannah Bret\",\"email\":\"hannah.bret@example.com\",\"version\":\"cko-platform-terms-1.0.0\"},"
                + "\"seller_category\":\"cat_retail_001\","
                + "\"processing_details\":{\"annual_processing_volume\":1000,\"average_transaction_value\":2000,"
                + "\"average_order_fulfillment_time\":3,\"target_countries\":[\"US\"],\"currency\":\"USD\","
                + "\"payments\":{\"ach\":{\"annual_ach_volume\":100000,\"average_ach_transaction_size\":5000,"
                + "\"estimated_monthly_credit_volume\":50000,\"average_credit_amount\":2500}}},"
                + "\"contact_details\":{\"phone\":{\"number\":\"4155678900\",\"country_code\":\"US\"},"
                + "\"email_addresses\":{\"primary\":\"hannah.bret@example.com\","
                + "\"pci_compliance_contact\":\"pci.contact@example.com\"}},"
                + "\"profile\":{\"urls\":[\"https://www.isv-sole-trader-example.com\"],\"mccs\":[\"5551\"],"
                + "\"holding_currencies\":[\"USD\"],\"default_holding_currency\":\"USD\"},"
                + "\"company\":{\"business_type\":\"individual_or_sole_proprietorship\",\"is_registered_company\":false,"
                + "\"trading_name\":\"Hannah's Goods\",\"date_of_incorporation\":{\"year\":2025,\"month\":10,\"day\":1},"
                + "\"principal_address\":{\"address_line1\":\"123 Main Street\",\"city\":\"San Francisco\",\"state\":\"CA\","
                + "\"zip\":\"94105\",\"country\":\"US\"},\"representatives\":[{\"roles\":[\"ubo\"],\"ownership_percentage\":100,"
                + "\"individual\":{\"first_name\":\"Hannah\",\"last_name\":\"Bret\","
                + "\"email_address\":\"hannah.bret@example.com\",\"national_id_type\":\"ssn\","
                + "\"national_id_number\":\"123456789\",\"date_of_birth\":{\"day\":15,\"month\":1,\"year\":1990},"
                + "\"place_of_birth\":{\"country\":\"US\"},\"citizenships\":[{\"country\":\"US\"}],"
                + "\"phone\":{\"country_code\":\"US\",\"number\":\"4155678901\"},"
                + "\"address\":{\"address_line1\":\"123 Main Street\",\"city\":\"San Francisco\",\"state\":\"CA\",\"zip\":\"94105\","
                + "\"country\":\"US\"}}}]}"
                + "}";

        final JsonObject actual = JsonParser.parseString(
                serializer.toJson(serializer.fromJson(example, OnboardEntityRequest.class))).getAsJsonObject();
        // is_draft is a primitive boolean on the request, so it is always serialized.
        actual.remove("is_draft");

        assertEquals(JsonParser.parseString(example), actual);
    }

    // ------------------------------------------------------------------------
    // ContactDetails
    // phone with country_code, email_addresses and invitee, none of which the
    // spec examples reach together.
    // ------------------------------------------------------------------------

    @Test
    void shouldSerializeAndRoundTripContactDetailsWithInvitee() {
        final ContactDetails contactDetails = ContactDetails.builder()
                .phone(AccountPhone.builder().countryCode(CountryCode.GB).number("2072345678").build())
                .emailAddresses(EntityEmailAddresses.builder()
                        .primary("admin@example.com")
                        .pciComplianceContact("pci@example.com")
                        .build())
                .invitee(Invitee.builder().email("invitee@example.com").build())
                .build();

        final String json = serializer.toJson(contactDetails);

        assertEquals(JsonParser.parseString("{\"phone\":{\"country_code\":\"GB\",\"number\":\"2072345678\"},"
                        + "\"email_addresses\":{\"primary\":\"admin@example.com\","
                        + "\"pci_compliance_contact\":\"pci@example.com\"},"
                        + "\"invitee\":{\"email\":\"invitee@example.com\"}}"),
                JsonParser.parseString(json), json);
        assertEquals(contactDetails, serializer.fromJson(json, ContactDetails.class));
    }

    // ------------------------------------------------------------------------
    // Company
    // Every spec property, including principal_address, regulatory_licence_number,
    // financial_details and the full date_of_incorporation. The deprecated
    // document field is absent from every variant and stays unset.
    // ------------------------------------------------------------------------

    @Test
    void shouldSerializeAndRoundTripEveryCompanyField() {
        final Company company = Company.builder()
                .legalName("Super Hero Masks Ltd")
                .tradingName("Super Hero Masks")
                .businessRegistrationNumber("01234567")
                .dateOfIncorporation(DateOfIncorporation.builder().day(1).month(6).year(2010).build())
                .regulatoryLicenceNumber("FRN123456")
                .principalAddress(Address.builder()
                        .addressLine1("90 Tottenham Court Road")
                        .addressLine2("Floor 2")
                        .city("London")
                        .state("London")
                        .zip("W1T 4TJ")
                        .country(CountryCode.GB)
                        .build())
                .registeredAddress(Address.builder()
                        .addressLine1("1 Main Street")
                        .city("London")
                        .zip("W1T 4TJ")
                        .country(CountryCode.GB)
                        .build())
                .representatives(Collections.singletonList(Representative.builder()
                        .id("rep_xoo3xudh9mgxw6tv4140063pdi")
                        .individual(RepresentativeIndividual.builder().firstName("Jane").lastName("Doe").build())
                        .roles(Collections.singletonList(EntityRoles.UBO))
                        .ownershipPercentage(75)
                        .build()))
                .financialDetails(EntityFinancialDetails.builder()
                        .annualProcessingVolume(120000000L)
                        .averageTransactionValue(10000L)
                        .highestTransactionValue(2500000L)
                        .currency(Currency.GBP)
                        .build())
                .businessType(BusinessType.LIMITED_COMPANY)
                .additionalTradingNames(Arrays.asList("SHM", "Hero Masks"))
                .isRegisteredCompany(true)
                .build();

        final String json = serializer.toJson(company);

        assertEquals(JsonParser.parseString("{\"legal_name\":\"Super Hero Masks Ltd\","
                        + "\"trading_name\":\"Super Hero Masks\",\"business_registration_number\":\"01234567\","
                        + "\"date_of_incorporation\":{\"day\":1,\"month\":6,\"year\":2010},"
                        + "\"regulatory_licence_number\":\"FRN123456\","
                        + "\"principal_address\":{\"address_line1\":\"90 Tottenham Court Road\",\"address_line2\":\"Floor 2\","
                        + "\"city\":\"London\",\"state\":\"London\",\"zip\":\"W1T 4TJ\",\"country\":\"GB\"},"
                        + "\"registered_address\":{\"address_line1\":\"1 Main Street\",\"city\":\"London\","
                        + "\"zip\":\"W1T 4TJ\",\"country\":\"GB\"},"
                        + "\"representatives\":[{\"id\":\"rep_xoo3xudh9mgxw6tv4140063pdi\",\"roles\":[\"ubo\"],"
                        + "\"ownership_percentage\":75,\"individual\":{\"first_name\":\"Jane\",\"last_name\":\"Doe\"}}],"
                        + "\"financial_details\":{\"annual_processing_volume\":120000000,\"average_transaction_value\":10000,"
                        + "\"highest_transaction_value\":2500000,\"currency\":\"GBP\"},"
                        + "\"business_type\":\"limited_company\",\"additional_trading_names\":[\"SHM\",\"Hero Masks\"],"
                        + "\"is_registered_company\":true}"),
                JsonParser.parseString(json), json);
        assertEquals(company, serializer.fromJson(json, Company.class));
    }

    // ------------------------------------------------------------------------
    // Representative and Individual (v2.0)
    // The v2.0 shape puts the person fields directly on the representative, and
    // the v2.0 top-level individual carries identification and financial_details.
    // ------------------------------------------------------------------------

    @Test
    @SuppressWarnings("deprecation")
    void shouldSerializeAndRoundTripV2Representative() {
        final Representative representative = Representative.builder()
                .id("rep_r2y49v5j1skna5zx0swaprf2he")
                .firstName("John")
                .middleName("Paul")
                .lastName("Doe")
                .dateOfBirth(DateOfBirth.builder().day(5).month(6).year(1995).build())
                .phone(AccountPhone.builder().number("2072345678").build())
                .address(Address.builder()
                        .addressLine1("90 Tottenham Court Road")
                        .city("London")
                        .zip("W1T 4TJ")
                        .country(CountryCode.GB)
                        .build())
                .placeOfBirth(PlaceOfBirth.builder().country(CountryCode.FR).build())
                .identification(Identification.builder().nationalIdNumber("AB123456C").build())
                .build();

        final String json = serializer.toJson(representative);

        assertEquals(JsonParser.parseString("{\"id\":\"rep_r2y49v5j1skna5zx0swaprf2he\","
                        + "\"first_name\":\"John\",\"middle_name\":\"Paul\",\"last_name\":\"Doe\","
                        + "\"date_of_birth\":{\"day\":5,\"month\":6,\"year\":1995},"
                        + "\"phone\":{\"number\":\"2072345678\"},"
                        + "\"address\":{\"address_line1\":\"90 Tottenham Court Road\",\"city\":\"London\","
                        + "\"zip\":\"W1T 4TJ\",\"country\":\"GB\"},"
                        + "\"place_of_birth\":{\"country\":\"FR\"},"
                        + "\"identification\":{\"national_id_number\":\"AB123456C\"}}"),
                JsonParser.parseString(json), json);
        assertEquals(representative, serializer.fromJson(json, Representative.class));
    }

    @Test
    void shouldSerializeAndRoundTripV2Individual() {
        final Individual individual = Individual.builder()
                .firstName("Jane")
                .middleName("Anne")
                .lastName("Doe")
                .tradingName("Jane's Crafts")
                .registeredAddress(Address.builder()
                        .addressLine1("90 Tottenham Court Road")
                        .city("London")
                        .zip("W1T 4TJ")
                        .country(CountryCode.GB)
                        .build())
                .dateOfBirth(DateOfBirth.builder().day(15).month(1).year(1990).build())
                .placeOfBirth(PlaceOfBirth.builder().country(CountryCode.GB).build())
                .identification(Identification.builder().nationalIdNumber("QQ123456C").build())
                .financialDetails(EntityFinancialDetails.builder()
                        .annualProcessingVolume(5000000L)
                        .averageTransactionValue(2500L)
                        .highestTransactionValue(100000L)
                        .currency(Currency.GBP)
                        .build())
                .build();

        final String json = serializer.toJson(individual);

        assertEquals(JsonParser.parseString("{\"first_name\":\"Jane\",\"middle_name\":\"Anne\","
                        + "\"last_name\":\"Doe\",\"trading_name\":\"Jane's Crafts\","
                        + "\"registered_address\":{\"address_line1\":\"90 Tottenham Court Road\",\"city\":\"London\","
                        + "\"zip\":\"W1T 4TJ\",\"country\":\"GB\"},"
                        + "\"date_of_birth\":{\"day\":15,\"month\":1,\"year\":1990},"
                        + "\"place_of_birth\":{\"country\":\"GB\"},"
                        + "\"identification\":{\"national_id_number\":\"QQ123456C\"},"
                        + "\"financial_details\":{\"annual_processing_volume\":5000000,\"average_transaction_value\":2500,"
                        + "\"highest_transaction_value\":100000,\"currency\":\"GBP\"}}"),
                JsonParser.parseString(json), json);
        assertEquals(individual, serializer.fromJson(json, Individual.class));
    }

    // ------------------------------------------------------------------------
    // Identification
    // The spec has national_id_number only; the deprecated document stays unset.
    // ------------------------------------------------------------------------

    @Test
    void shouldSerializeAndRoundTripIdentification() {
        final Identification identification = Identification.builder().nationalIdNumber("AB123456C").build();

        final String json = serializer.toJson(identification);

        assertEquals(JsonParser.parseString("{\"national_id_number\":\"AB123456C\"}"), JsonParser.parseString(json), json);
        assertEquals(identification, serializer.fromJson(json, Identification.class));
    }

    // ------------------------------------------------------------------------
    // AccountsFileRequest
    // The purpose is sent as a multipart text part built from getPurpose(), not
    // through Gson, so the wire value is the getPurpose() string.
    // ------------------------------------------------------------------------

    @Test
    void shouldSendAccountsFileRequestPurposeWireValue() {
        final AccountsFileRequest request = AccountsFileRequest.builder()
                .purpose(AccountsFilePurpose.IDENTITY_VERIFICATION)
                .build();

        assertEquals("identity_verification", request.getPurpose().getPurpose());
    }

    // ------------------------------------------------------------------------
    // Enum wire values
    // Every value is serialized through Gson and compared to the spec enum string.
    // ------------------------------------------------------------------------

    @Test
    void shouldSerializeEveryIdentityVerificationDocumentTypeWireValue() {
        final Map<DocumentType, String> expected = new EnumMap<>(DocumentType.class);
        expected.put(DocumentType.PASSPORT, "passport");
        expected.put(DocumentType.NATIONAL_IDENTITY_CARD, "national_identity_card");
        expected.put(DocumentType.DRIVING_LICENSE, "driving_license");
        expected.put(DocumentType.CITIZEN_CARD, "citizen_card");
        expected.put(DocumentType.RESIDENCE_PERMIT, "residence_permit");
        expected.put(DocumentType.ELECTORAL_ID, "electoral_id");

        assertEveryWireValue(DocumentType.class, expected);
    }

    @Test
    void shouldSerializeEveryCompanyVerificationTypeWireValue() {
        final Map<CompanyVerificationType, String> expected = new EnumMap<>(CompanyVerificationType.class);
        expected.put(CompanyVerificationType.INCORPORATION_DOCUMENT, "incorporation_document");
        expected.put(CompanyVerificationType.ARTICLES_OF_ASSOCIATION, "articles_of_association");

        assertEveryWireValue(CompanyVerificationType.class, expected);
    }

    @Test
    void shouldSerializeEveryArticlesOfAssociationTypeWireValue() {
        final Map<ArticlesOfAssociationType, String> expected = new EnumMap<>(ArticlesOfAssociationType.class);
        expected.put(ArticlesOfAssociationType.ARTICLES_OF_ASSOCIATION, "articles_of_association");
        expected.put(ArticlesOfAssociationType.MEMORANDUM_OF_ASSOCIATION, "memorandum_of_association");

        assertEveryWireValue(ArticlesOfAssociationType.class, expected);
    }

    @Test
    void shouldSerializeEveryNationalIdTypeWireValue() {
        final Map<NationalIdType, String> expected = new EnumMap<>(NationalIdType.class);
        expected.put(NationalIdType.SSN, "ssn");
        expected.put(NationalIdType.ITIN, "itin");
        expected.put(NationalIdType.PASSPORT, "passport");
        expected.put(NationalIdType.DRIVING_LICENSE, "driving_license");
        expected.put(NationalIdType.NATIONAL_ID_CARD, "national_id_card");
        expected.put(NationalIdType.RESIDENCE_PERMIT, "residence_permit");
        expected.put(NationalIdType.OTHER, "other");

        assertEveryWireValue(NationalIdType.class, expected);
    }

    @Test
    void shouldSerializeEveryEntityRolesWireValue() {
        final Map<EntityRoles, String> expected = new EnumMap<>(EntityRoles.class);
        expected.put(EntityRoles.UBO, "ubo");
        expected.put(EntityRoles.LEGAL_REPRESENTATIVE, "legal_representative");
        expected.put(EntityRoles.AUTHORISED_SIGNATORY, "authorised_signatory");
        expected.put(EntityRoles.DIRECTOR, "director");
        expected.put(EntityRoles.CONTROL_PERSON, "control_person");

        assertEveryWireValue(EntityRoles.class, expected);
    }

    @Test
    void shouldSerializeEveryCompanyPositionWireValue() {
        final Map<CompanyPosition, String> expected = new EnumMap<>(CompanyPosition.class);
        expected.put(CompanyPosition.CEO, "ceo");
        expected.put(CompanyPosition.CFO, "cfo");
        expected.put(CompanyPosition.COO, "coo");
        expected.put(CompanyPosition.MANAGING_MEMBER, "managing_member");
        expected.put(CompanyPosition.GENERAL_PARTNER, "general_partner");
        expected.put(CompanyPosition.PRESIDENT, "president");
        expected.put(CompanyPosition.VICE_PRESIDENT, "vice_president");
        expected.put(CompanyPosition.TREASURER, "treasurer");
        expected.put(CompanyPosition.OTHER_SENIOR_MANAGEMENT, "other_senior_management");
        expected.put(CompanyPosition.OTHER_EXECUTIVE_OFFICER, "other_executive_officer");
        expected.put(CompanyPosition.OTHER_NON_EXECUTIVE_NON_SENIOR, "other_non_executive_non_senior");

        assertEveryWireValue(CompanyPosition.class, expected);
    }

    @Test
    void shouldSerializeEveryBusinessTypeWireValue() {
        final Map<BusinessType, String> expected = new EnumMap<>(BusinessType.class);
        expected.put(BusinessType.GENERAL_PARTNERSHIP, "general_partnership");
        expected.put(BusinessType.LIMITED_PARTNERSHIP, "limited_partnership");
        expected.put(BusinessType.PUBLIC_LIMITED_COMPANY, "public_limited_company");
        expected.put(BusinessType.LIMITED_COMPANY, "limited_company");
        expected.put(BusinessType.PROFESSIONAL_ASSOCIATION, "professional_association");
        expected.put(BusinessType.UNINCORPORATED_ASSOCIATION, "unincorporated_association");
        expected.put(BusinessType.AUTO_ENTREPRENEUR, "auto_entrepreneur");
        expected.put(BusinessType.INDIVIDUAL_OR_SOLE_PROPRIETORSHIP, "individual_or_sole_proprietorship");
        expected.put(BusinessType.SCOTTISH_LIMITED_PARTNERSHIP, "scottish_limited_partnership");
        expected.put(BusinessType.LIMITED_LIABILITY_CORPORATION, "limited_liability_corporation");
        expected.put(BusinessType.PRIVATE_CORPORATION, "private_corporation");
        expected.put(BusinessType.PUBLICLY_TRADED_CORPORATION, "publicly_traded_corporation");
        expected.put(BusinessType.GOVERNMENT_AGENCY, "government_agency");
        expected.put(BusinessType.NON_PROFIT_ENTITY, "non_profit_entity");
        expected.put(BusinessType.TRUST, "trust");
        expected.put(BusinessType.CLUB_OR_SOCIETY, "club_or_society");
        expected.put(BusinessType.REGULATED_FINANCIAL_INSTITUTION, "regulated_financial_institution");
        expected.put(BusinessType.CFTC_REGISTERED_ENTITY, "cftc_registered_entity");
        expected.put(BusinessType.SEC_REGISTERED_ENTITY, "sec_registered_entity");

        assertEveryWireValue(BusinessType.class, expected);
    }

    private <E extends Enum<E>> void assertEveryWireValue(final Class<E> type, final Map<E, String> expected) {
        assertEquals(type.getEnumConstants().length, expected.size(), "every value must be asserted");
        expected.forEach((value, wire) -> {
            assertEquals("\"" + wire + "\"", serializer.toJson(value), value.name());
            assertEquals(value, serializer.fromJson("\"" + wire + "\"", type), wire);
        });
    }
}
