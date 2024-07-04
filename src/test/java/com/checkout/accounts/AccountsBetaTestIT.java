package com.checkout.accounts;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static com.checkout.TestHelper.generateRandomEmail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AccountsBetaTestIT extends SandboxTestFixture {

    AccountsBetaTestIT() {
        super(PlatformType.DEFAULT_OAUTH);
    }

    @Test
    void shouldCreateGetAndUpdateOnboardIndividualEntity() {
        final String randomReference = RandomStringUtils.random(15, true, true);
        final OnboardEntityRequest onboardEntityRequest = OnboardEntityRequest.builder()
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
        final OnboardEntityResponse entityResponse = blocking(() -> checkoutApi.accountsBetaClient().createEntity(onboardEntityRequest));
        assertNotNull(entityResponse);
        final String entityId = entityResponse.getId();
        assertNotNull(entityId);
        assertEquals(randomReference, entityResponse.getReference());

        final OnboardEntityDetailsResponse entityDetailsResponse = blocking(() -> checkoutApi.accountsBetaClient().getEntity(entityId));
        assertNotNull(entityDetailsResponse);
        assertEquals(entityId, entityDetailsResponse.getId());
        assertEquals(randomReference, entityDetailsResponse.getReference());
        assertEquals(onboardEntityRequest.getContactDetails(), entityDetailsResponse.getContactDetails());
        assertNotNull(entityDetailsResponse.getIndividual());
        assertEquals(onboardEntityRequest.getIndividual().getFirstName(), entityDetailsResponse.getIndividual().getFirstName());
        assertEquals(onboardEntityRequest.getIndividual().getTradingName(), entityDetailsResponse.getIndividual().getTradingName());
        assertEquals(onboardEntityRequest.getIndividual().getNationalTaxId(), entityDetailsResponse.getIndividual().getNationalTaxId());
        assertEquals(onboardEntityRequest.getIndividual().getDateOfBirth(), entityDetailsResponse.getIndividual().getDateOfBirth());

        onboardEntityRequest.getIndividual().setFirstName("Jhon");
        final OnboardEntityResponse updatedEntityResponse = blocking(() -> checkoutApi.accountsBetaClient().updateEntity(onboardEntityRequest, entityId));
        assertNotNull(updatedEntityResponse);

        final OnboardEntityDetailsResponse verifyUpdated = blocking(() -> checkoutApi.accountsBetaClient().getEntity(entityId));
        assertNotNull(verifyUpdated);
        assertEquals(onboardEntityRequest.getIndividual().getFirstName(), verifyUpdated.getIndividual().getFirstName());

    }

    @Test
    void shouldCreateGetAndUpdateOnboardCompanyEntity() {
        final String randomReference = RandomStringUtils.random(15, true, true);
        final OnboardEntityRequest onboardEntityRequest = buildCompanyEntity(randomReference);

        final OnboardEntityResponse entityResponse = blocking(() -> checkoutApi.accountsBetaClient().createEntity(onboardEntityRequest));
        assertNotNull(entityResponse);
        final String entityId = entityResponse.getId();
        assertNotNull(entityId);
        assertEquals(randomReference, entityResponse.getReference());

        final OnboardEntityDetailsResponse entityDetailsResponse = blocking(() -> checkoutApi.accountsBetaClient().getEntity(entityId));
        assertNotNull(entityDetailsResponse);
        assertEquals(entityId, entityDetailsResponse.getId());
        assertEquals(randomReference, entityDetailsResponse.getReference());
        assertEquals(onboardEntityRequest.getContactDetails(), entityDetailsResponse.getContactDetails());
        assertNotNull(entityDetailsResponse.getCompany());
        assertEquals(onboardEntityRequest.getCompany().getBusinessType(), entityDetailsResponse.getCompany().getBusinessType());
        assertEquals(onboardEntityRequest.getCompany().getLegalName(), entityDetailsResponse.getCompany().getLegalName());
        assertEquals(onboardEntityRequest.getCompany().getTradingName(), entityDetailsResponse.getCompany().getTradingName());
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

}