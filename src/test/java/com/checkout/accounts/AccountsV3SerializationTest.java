package com.checkout.accounts;

import com.checkout.GsonSerializer;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
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

        assertTrue(json.contains("\"annual_processing_volume\""));
        assertTrue(json.contains("\"average_order_fulfillment_time\""));
        assertTrue(json.contains("\"highest_transaction_value\""));
        assertTrue(json.contains("\"settlement_country\""));
        assertTrue(json.contains("\"target_countries\""));
        assertTrue(json.contains("\"payments\""));
        assertTrue(json.contains("\"ach\""));
        assertTrue(json.contains("\"annual_ach_volume\""));
        assertTrue(json.contains("\"average_ach_transaction_size\""));
        assertTrue(json.contains("\"estimated_monthly_credit_volume\""));
        assertTrue(json.contains("\"average_credit_amount\""));
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
                .id("rep_00000000000000000000000000")
                .individual(RepresentativeIndividual.builder()
                        .firstName("John")
                        .lastName("Representative")
                        .citizenships(Collections.singletonList(Citizenship.builder()
                                .type("citizenship")
                                .country(CountryCode.US)
                                .build()))
                        .nationalIdType(NationalIdType.SSN)
                        .nationalIdNumber("AB123456C")
                        .emailAddress("john@example.com")
                        .build())
                .companyPosition(CompanyPosition.CEO)
                .ownershipPercentage(100)
                .roles(Arrays.asList(EntityRoles.UBO, EntityRoles.AUTHORISED_SIGNATORY, EntityRoles.DIRECTOR, EntityRoles.CONTROL_PERSON))
                .build();

        final String json = serializer.toJson(representative);

        assertTrue(json.contains("\"individual\""));
        assertTrue(json.contains("\"first_name\""));
        assertTrue(json.contains("\"citizenships\""));
        assertTrue(json.contains("\"country\""));
        assertTrue(json.contains("\"national_id_type\""));
        assertTrue(json.contains("ssn"));
        assertTrue(json.contains("\"national_id_number\""));
        assertTrue(json.contains("\"email_address\""));
        assertTrue(json.contains("\"company_position\""));
        assertTrue(json.contains("ceo"));
        assertTrue(json.contains("\"ownership_percentage\""));
        assertTrue(json.contains("director"));
        assertTrue(json.contains("control_person"));
    }

    @Test
    void shouldSerializeFinancialStatementsDocument() {
        final OnboardSubEntityDocuments documents = OnboardSubEntityDocuments.builder()
                .financialStatements(FinancialStatements.builder()
                        .type(FinancialStatementsType.FINANCIAL_STATEMENTS)
                        .front("file_00000000000000000000000000")
                        .build())
                .build();

        final String json = serializer.toJson(documents);

        assertTrue(json.contains("\"financial_statements\""));
        assertTrue(json.contains("financial_statements"));
        assertTrue(json.contains("\"front\""));
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
                        + "\"id\":\"ent_aaaaaaaaaaaaaaaaaaaaaaaaaa\","
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
}
