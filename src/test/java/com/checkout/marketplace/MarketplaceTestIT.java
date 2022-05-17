package com.checkout.marketplace;

import com.checkout.CheckoutApiException;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.IdResponse;
import com.checkout.marketplace.balances.BalancesQuery;
import com.checkout.marketplace.balances.BalancesResponse;
import com.checkout.marketplace.balances.CurrencyAccountBalance;
import com.checkout.marketplace.transfers.CreateTransferRequest;
import com.checkout.marketplace.transfers.CreateTransferResponse;
import com.checkout.marketplace.transfers.TransferDestination;
import com.checkout.marketplace.transfers.TransferSource;
import com.checkout.marketplace.transfers.TransferType;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
        final OnboardEntityResponse entityResponse = blocking(() -> fourApi.marketplaceClient().createEntity(onboardEntityRequest));
        assertNotNull(entityResponse);
        final String entityId = entityResponse.getId();
        assertNotNull(entityId);
        assertEquals(randomReference, entityResponse.getReference());

        final OnboardEntityDetailsResponse entityDetailsResponse = blocking(() -> fourApi.marketplaceClient().getEntity(entityId));
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
        final OnboardEntityResponse updatedEntityResponse = blocking(() -> fourApi.marketplaceClient().updateEntity(onboardEntityRequest, entityId));
        assertNotNull(updatedEntityResponse);

        final OnboardEntityDetailsResponse verifyUpdated = blocking(() -> fourApi.marketplaceClient().getEntity(entityId));
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
        final IdResponse fileResponse = blocking(() -> fourApi.marketplaceClient().submitFile(fileRequest));
        assertNotNull(fileResponse);
        assertNotNull(fileResponse.getId());
    }

    @Test
    void shouldInitiateTransferOfFunds() {

        final CreateTransferRequest transferRequest = CreateTransferRequest.builder()
                .transferType(TransferType.COMMISSION)
                .source(TransferSource.builder()
                        .id("ent_kidtcgc3ge5unf4a5i6enhnr5m")
                        .amount(100L)
                        .build())
                .destination(TransferDestination.builder()
                        .id("ent_w4jelhppmfiufdnatam37wrfc4")
                        .build())
                .build();

        final CreateTransferResponse response = blocking(() -> fourApi.marketplaceClient().initiateTransferOfFunds(transferRequest));
        assertNotNull(response.getId());
        assertEquals("pending", response.getStatus());
    }

    @Test
    void shouldInitiateTransferOfFunds_idempotently() throws ExecutionException, InterruptedException {

        final CreateTransferRequest transferRequest = CreateTransferRequest.builder()
                .transferType(TransferType.COMMISSION)
                .source(TransferSource.builder()
                        .id("ent_kidtcgc3ge5unf4a5i6enhnr5m")
                        .amount(100L)
                        .build())
                .destination(TransferDestination.builder()
                        .id("ent_w4jelhppmfiufdnatam37wrfc4")
                        .build())
                .build();

        final String idempotencyKey = UUID.randomUUID().toString();

        final CreateTransferResponse response = blocking(() -> fourApi.marketplaceClient().initiateTransferOfFunds(transferRequest, idempotencyKey));
        assertNotNull(response.getId());
        assertEquals("pending", response.getStatus());

        try {
            fourApi.marketplaceClient().initiateTransferOfFunds(transferRequest, idempotencyKey).get();
            fail("Should not get here!");
        } catch (final InterruptedException | ExecutionException e) {
            assertTrue(e.getCause() instanceof CheckoutApiException);
            final CheckoutApiException checkoutException = (CheckoutApiException) e.getCause();
            assertEquals(409, checkoutException.getHttpStatusCode());
        }
    }

    @Test
    void shouldRetrieveEntityBalances() {
        final BalancesQuery query = BalancesQuery.builder()
                .query("currency:" + Currency.GBP)
                .build();

        final BalancesResponse balancesResponse = blocking(() -> fourApi.marketplaceClient().retrieveEntityBalances("ent_kidtcgc3ge5unf4a5i6enhnr5m", query));
        assertNotNull(balancesResponse);
        assertNotNull(balancesResponse.getData());
        for (final CurrencyAccountBalance balance : balancesResponse.getData()) {
            assertNotNull(balance.getDescriptor());
            assertNotNull(balance.getHoldingCurrency());
            assertNotNull(balance.getBalances());
        }
    }

}