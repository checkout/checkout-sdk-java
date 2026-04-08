package com.checkout.issuing.newfields;

import com.checkout.issuing.BaseIssuingTestIT;
import com.checkout.issuing.cardholders.CardholderResponse;
import com.checkout.issuing.cards.requests.create.CardLifetime;
import com.checkout.issuing.cards.requests.create.LifetimeUnit;
import com.checkout.issuing.cards.requests.create.PhysicalCardRequest;
import com.checkout.issuing.cards.requests.create.VirtualCardRequest;
import com.checkout.issuing.cards.requests.update.IssuingCardMetadata;
import com.checkout.issuing.cards.responses.CardResponse;
import org.junit.jupiter.api.BeforeAll;

import java.time.LocalDate;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Integration tests for new fields on card creation:
 * {@code metadata} (udf1–udf5) and {@code revocation_date}.
 *
 * <p>Disabled by default — requires issuing OAuth credentials and
 * the {@code CHECKOUT_DEFAULT_OAUTH_ISSUING_*} environment variables.</p>
 */
@Disabled("Requires issuing OAuth credentials and an enabled issuing sandbox account")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IssuingCardNewFieldsIT extends BaseIssuingTestIT {

    private CardholderResponse cardholder;

    @BeforeAll
    void setUp() {
        cardholder = createCardholder();
    }

    @Test
    void shouldCreateVirtualCardWithMetadata() {
        final VirtualCardRequest request = VirtualCardRequest.builder()
                .cardholderId(cardholder.getId())
                .cardProductId("pro_7syjig3jq3mezlc3vjrdpfitl4")
                .lifetime(CardLifetime.builder().unit(LifetimeUnit.YEARS).value(1).build())
                .metadata(IssuingCardMetadata.builder()
                        .udf1("integration_test")
                        .udf2("new_fields_suite")
                        .build())
                .build();

        final CardResponse response = blocking(() ->
                issuingApi.issuingClient().createCard(request));

        assertNotNull(response);
        assertNotNull(response.getId());
    }

    @Test
    void shouldCreateVirtualCardWithRevocationDate() {
        final VirtualCardRequest request = VirtualCardRequest.builder()
                .cardholderId(cardholder.getId())
                .cardProductId("pro_7syjig3jq3mezlc3vjrdpfitl4")
                .lifetime(CardLifetime.builder().unit(LifetimeUnit.YEARS).value(1).build())
                .revocationDate(LocalDate.of(2027, 12, 31))
                .build();

        final CardResponse response = blocking(() ->
                issuingApi.issuingClient().createCard(request));

        assertNotNull(response);
        assertNotNull(response.getId());
    }

    @Test
    void shouldCreateVirtualCardWithBothNewFields() {
        final VirtualCardRequest request = VirtualCardRequest.builder()
                .cardholderId(cardholder.getId())
                .cardProductId("pro_7syjig3jq3mezlc3vjrdpfitl4")
                .lifetime(CardLifetime.builder().unit(LifetimeUnit.MONTHS).value(12).build())
                .metadata(IssuingCardMetadata.builder()
                        .udf1("campaign_q4")
                        .udf3("region_emea")
                        .udf5("priority_high")
                        .build())
                .revocationDate(LocalDate.of(2027, 6, 30))
                .build();

        final CardResponse response = blocking(() ->
                issuingApi.issuingClient().createCard(request));

        assertNotNull(response);
        assertNotNull(response.getId());
    }

    @Test
    void shouldCreatePhysicalCardWithMetadata() {
        final PhysicalCardRequest request = PhysicalCardRequest.builder()
                .cardholderId(cardholder.getId())
                .cardProductId("pro_7syjig3jq3mezlc3vjrdpfitl4")
                .lifetime(CardLifetime.builder().unit(LifetimeUnit.YEARS).value(3).build())
                .metadata(IssuingCardMetadata.builder()
                        .udf1("physical_card_test")
                        .build())
                .build();

        final CardResponse response = blocking(() ->
                issuingApi.issuingClient().createCard(request));

        assertNotNull(response);
        assertNotNull(response.getId());
    }

    @Test
    void shouldCreatePhysicalCardWithRevocationDate() {
        final PhysicalCardRequest request = PhysicalCardRequest.builder()
                .cardholderId(cardholder.getId())
                .cardProductId("pro_7syjig3jq3mezlc3vjrdpfitl4")
                .lifetime(CardLifetime.builder().unit(LifetimeUnit.YEARS).value(2).build())
                .revocationDate(LocalDate.of(2026, 9, 15))
                .build();

        final CardResponse response = blocking(() ->
                issuingApi.issuingClient().createCard(request));

        assertNotNull(response);
        assertNotNull(response.getId());
    }
}
