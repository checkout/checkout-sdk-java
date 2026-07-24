package com.checkout.accounts;

import com.checkout.GsonSerializer;
import com.checkout.common.Currency;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

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
}
