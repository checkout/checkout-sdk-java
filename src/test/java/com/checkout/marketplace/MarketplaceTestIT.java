package com.checkout.marketplace;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MarketplaceTestIT extends SandboxTestFixture {

    MarketplaceTestIT() {
        super(PlatformType.FOUR_OAUTH);
    }

    @Test
    void shouldCreateGetAndUpdateOnboardEntity() {
        final String randomReference = RandomStringUtils.random(15, true, true);
        final OnboardEntityRequest onboardEntityRequest = OnboardEntityRequest.builder()
                .reference(randomReference)
                .contactDetails(ContactDetails.builder()
                        .phone(Phone.builder()
                                .number("2345678910")
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
                                .country(CountryCode.GB)
                                .build())
                        .nationalTaxId("TAX123456")
                        .dateOfBirth(DateOfBirth.builder()
                                .day(5)
                                .month(6)
                                .year(1995)
                                .build())
                        .identification(Identification.builder()
                                .nationalIdNumber("AB123456C")
                                .build())
                        .build())

                .build();
        final OnboardEntityResponse entityResponse = blocking(fourApi.marketplaceClient().createEntity(onboardEntityRequest));
        assertNotNull(entityResponse);
        final String entityId = entityResponse.getId();
        assertNotNull(entityId);
        assertEquals(randomReference, entityResponse.getReference());

        final OnboardEntityDetailsResponse entityDetailsResponse = blocking(fourApi.marketplaceClient().getEntity(entityId));
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
        final OnboardEntityResponse updatedEntityResponse = blocking(fourApi.marketplaceClient().updateEntity(onboardEntityRequest, entityId));
        assertNotNull(updatedEntityResponse);

        final OnboardEntityDetailsResponse verifyUpdated = blocking(fourApi.marketplaceClient().getEntity(entityId));
        assertNotNull(verifyUpdated);
        assertEquals(onboardEntityRequest.getIndividual().getFirstName(), verifyUpdated.getIndividual().getFirstName());

    }

    @Test
    void shouldUploadMarketplaceFile() throws URISyntaxException {
        //Upload your dispute file evidence
        final URL resource = getClass().getClassLoader().getResource("checkout.jpeg");
        final File file = new File(resource.toURI());
        final MarketplaceFileRequest fileRequest = MarketplaceFileRequest.builder()
                .file(file)
                .contentType(ContentType.IMAGE_JPEG)
                .purpose(MarketplaceFilePurpose.IDENTIFICATION)
                .build();
        final IdResponse fileResponse = blocking(fourApi.marketplaceClient().submitFile(fileRequest));
        assertNotNull(fileResponse);
        assertNotNull(fileResponse.getId());
    }

}