package com.checkout.handlepaymentsandpayouts.googlepay;

import com.checkout.EmptyResponse;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.handlepaymentsandpayouts.googlepay.requests.GooglePayEnrollmentRequest;
import com.checkout.handlepaymentsandpayouts.googlepay.requests.GooglePayRegisterDomainRequest;
import com.checkout.handlepaymentsandpayouts.googlepay.responses.GooglePayDomainListResponse;
import com.checkout.handlepaymentsandpayouts.googlepay.responses.GooglePayEnrollmentStateResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Integration tests for Google Pay endpoints:
 *   POST /googlepay/enrollments
 *   POST /googlepay/enrollments/{entity_id}/domain
 *   GET  /googlepay/enrollments/{entity_id}/domains
 *   GET  /googlepay/enrollments/{entity_id}/state
 *
 * All tests are @Disabled because they require:
 * - A valid OAuth entity ID registered with Google Pay
 * - A verified domain
 * Enable them manually after setting up a valid Google Pay merchant account in sandbox.
 */
@Disabled("Requires a Google Pay enrolled entity and OAuth credentials — enable manually")
public class GooglePayTestIT extends SandboxTestFixture {

    private static final String ENTITY_ID = "ent_replace_with_real_entity_id";

    public GooglePayTestIT() {
        super(PlatformType.DEFAULT_OAUTH);
    }

    @Test
    void shouldEnrollEntity() {
        final EmptyResponse response = blocking(() ->
                checkoutApi.googlePayClient().enrollEntity(createEnrollmentRequest()));

        assertNotNull(response);
    }

    @Test
    void shouldRegisterDomain() {
        final EmptyResponse response = blocking(() ->
                checkoutApi.googlePayClient().registerDomain(ENTITY_ID, createRegisterDomainRequest()));

        assertNotNull(response);
    }

    @Test
    void shouldGetRegisteredDomains() {
        final GooglePayDomainListResponse response = blocking(() ->
                checkoutApi.googlePayClient().getRegisteredDomains(ENTITY_ID));

        validateDomainsResponse(response);
    }

    @Test
    void shouldGetEnrollmentState() {
        final GooglePayEnrollmentStateResponse response = blocking(() ->
                checkoutApi.googlePayClient().getEnrollmentState(ENTITY_ID));

        assertNotNull(response);
    }

    // Sync

    @Test
    void shouldEnrollEntitySync() {
        final EmptyResponse response = checkoutApi.googlePayClient().enrollEntitySync(createEnrollmentRequest());

        assertNotNull(response);
    }

    @Test
    void shouldRegisterDomainSync() {
        final EmptyResponse response = checkoutApi.googlePayClient().registerDomainSync(ENTITY_ID, createRegisterDomainRequest());

        assertNotNull(response);
    }

    @Test
    void shouldGetRegisteredDomainsSync() {
        validateDomainsResponse(checkoutApi.googlePayClient().getRegisteredDomainsSync(ENTITY_ID));
    }

    @Test
    void shouldGetEnrollmentStateSync() {
        assertNotNull(checkoutApi.googlePayClient().getEnrollmentStateSync(ENTITY_ID));
    }

    // Common methods

    private GooglePayEnrollmentRequest createEnrollmentRequest() {
        return GooglePayEnrollmentRequest.builder()
                .entityId(ENTITY_ID)
                .emailAddress("merchant@example.com")
                .acceptTermsOfService(true)
                .build();
    }

    private GooglePayRegisterDomainRequest createRegisterDomainRequest() {
        return GooglePayRegisterDomainRequest.builder()
                .webDomain("example.com")
                .build();
    }

    private void validateDomainsResponse(final GooglePayDomainListResponse response) {
        assertNotNull(response);
        assertNotNull(response.getDomains());
    }
}
