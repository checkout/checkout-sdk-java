package com.checkout.accounts;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.IdResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;

import static com.checkout.TestHelper.generateRandomEmail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AccountsTestIT extends SandboxTestFixture {

    AccountsTestIT() {
        super(PlatformType.DEFAULT_OAUTH);
    }

    @Test
    void shouldCreateGetAndUpdateOnboardIndividualEntity() {
        final String randomReference = RandomStringUtils.random(15, true, true);
        final OnboardEntityRequest onboardEntityRequest = OnboardEntityRequest.builder()
                .reference(randomReference)
                .contactDetails(ContactDetails.builder()
                        .phone(AccountPhone.builder()
                                .number("2345678910")
                                .build())
                        .emailAddresses(EntityEmailAddresses.builder()
                                .primary(generateRandomEmail())
                                .build())
                        .build())
                .profile(Profile.builder()
                        .urls(Collections.singletonList("https://www.superheroexample.com"))
                        .mccs(Collections.singletonList("0742"))
                        .build())
                .individual(Individual.builder()
                        .firstName("Bruce")
                        .lastName("Wayne")
                        .tradingName("Batman's Super Hero Masks")
                        .registeredAddress(Address.builder()
                                .addressLine1("Checkout.com")
                                .addressLine1("90 Tottenham Court Road")
                                .city("London")
                                .state("London")
                                .zip("WIT 4TJ")
                                .country(CountryCode.ES)
                                .build())
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
        final OnboardEntityResponse entityResponse = blocking(() -> checkoutApi.accountsClient().createEntity(onboardEntityRequest));
        assertNotNull(entityResponse);
        final String entityId = entityResponse.getId();
        assertNotNull(entityId);
        assertEquals(randomReference, entityResponse.getReference());

        final OnboardEntityDetailsResponse entityDetailsResponse = blocking(() -> checkoutApi.accountsClient().getEntity(entityId));
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
        final OnboardEntityResponse updatedEntityResponse = blocking(() -> checkoutApi.accountsClient().updateEntity(onboardEntityRequest, entityId));
        assertNotNull(updatedEntityResponse);

        final OnboardEntityDetailsResponse verifyUpdated = blocking(() -> checkoutApi.accountsClient().getEntity(entityId));
        assertNotNull(verifyUpdated);
        assertEquals(onboardEntityRequest.getIndividual().getFirstName(), verifyUpdated.getIndividual().getFirstName());

    }

    @Test
    void shouldCreateGetAndUpdateOnboardCompanyEntity() {
        final String randomReference = RandomStringUtils.random(15, true, true);
        final OnboardEntityRequest onboardEntityRequest = OnboardEntityRequest.builder()
                .reference(randomReference)
                .contactDetails(ContactDetails.builder()
                        .phone(AccountPhone.builder()
                                .number("2345678910")
                                .build())
                        .build())
                .profile(Profile.builder()
                        .urls(Collections.singletonList("https://www.superheroexample.com"))
                        .mccs(Collections.singletonList("0742"))
                        .build())
                .company(Company.builder()
                        .businessRegistrationNumber("452349600005")
                        .businessType(BusinessType.PUBLIC_LIMITED_COMPANY)
                        .legalName("Super Hero Masks Inc.")
                        .tradingName("Super Hero Masks")
                        .principalAddress(Address.builder()
                                .addressLine1("Checkout.com")
                                .addressLine1("90 Tottenham Court Road")
                                .city("London")
                                .state("London")
                                .zip("WIT4TJ")
                                .country(CountryCode.ES)
                                .build())
                        .representatives(Collections.singletonList(Representative.builder()
                                .firstName("John")
                                .lastName("Doe")
                                .address(Address.builder()
                                        .addressLine1("Checkout.com")
                                        .addressLine1("90 Tottenham Court Road")
                                        .city("London")
                                        .state("London")
                                        .zip("WIT4TJ")
                                        .country(CountryCode.ES)
                                        .build())
                                        .identification(Identification.builder()
                                                .nationalIdNumber("AB123456C")
                                                .build())
                                        .phone(AccountPhone.builder()
                                                .number("2345678910")
                                                .build())
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

        final OnboardEntityResponse entityResponse = blocking(() -> checkoutApi.accountsClient().createEntity(onboardEntityRequest));
        assertNotNull(entityResponse);
        final String entityId = entityResponse.getId();
        assertNotNull(entityId);
        assertEquals(randomReference, entityResponse.getReference());

        final OnboardEntityDetailsResponse entityDetailsResponse = blocking(() -> checkoutApi.accountsClient().getEntity(entityId));
        assertNotNull(entityDetailsResponse);
        assertEquals(entityId, entityDetailsResponse.getId());
        assertEquals(randomReference, entityDetailsResponse.getReference());
        assertEquals(onboardEntityRequest.getContactDetails(), entityDetailsResponse.getContactDetails());
        assertNotNull(entityDetailsResponse.getCompany());
        assertEquals(onboardEntityRequest.getCompany().getBusinessType(), entityDetailsResponse.getCompany().getBusinessType());
        assertEquals(onboardEntityRequest.getCompany().getLegalName(), entityDetailsResponse.getCompany().getLegalName());
        assertEquals(onboardEntityRequest.getCompany().getTradingName(), entityDetailsResponse.getCompany().getTradingName());
    }

    @Test
    void shouldUploadAccountsFile() throws URISyntaxException {
        //Upload your dispute file evidence
        final URL resource = getClass().getClassLoader().getResource("checkout.jpeg");
        final File file = new File(resource.toURI());
        final AccountsFileRequest fileRequest = AccountsFileRequest.builder()
                .file(file)
                .contentType(ContentType.IMAGE_JPEG)
                .purpose(AccountsFilePurpose.IDENTITY_VERIFICATION)
                .build();
        final IdResponse fileResponse = blocking(() -> checkoutApi.accountsClient().submitFile(fileRequest));
        assertNotNull(fileResponse);
        assertNotNull(fileResponse.getId());
    }

}