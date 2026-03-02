package com.checkout.issuing;

import com.checkout.EmptyResponse;
import com.checkout.issuing.cardholders.CardholderResponse;
import com.checkout.issuing.cards.responses.CardResponse;
import com.checkout.issuing.controls.requests.controlprofile.CreateControlProfileRequest;
import com.checkout.issuing.controls.requests.controlprofile.ControlProfileQuery;
import com.checkout.issuing.controls.requests.controlprofile.UpdateControlProfileRequest;
import com.checkout.issuing.controls.responses.controlprofile.ControlProfileResponse;
import com.checkout.issuing.controls.responses.controlprofile.ControlProfilesQueryResponse;
import com.checkout.payments.VoidResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled("3ds not configured and should not create cards every time")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IssuingControlProfilesTestIT extends BaseIssuingTestIT {

    private CardResponse card;
    private ControlProfileResponse controlProfile;

    @BeforeAll
    void setUp() {
        final CardholderResponse cardholder = createCardholder();
        card = createCard(cardholder.getId(), true);
        controlProfile = createControlProfile();
    }

    // Control Profiles - Async Tests
    @Test
    void shouldCreateControlProfile() {
        validateControlProfileCreation(controlProfile);
    }

    @Test
    void shouldGetControlProfileDetails() {
        final ControlProfileResponse response = blocking(() ->
                issuingApi.issuingClient().getControlProfileDetails(controlProfile.getId()));

        validateControlProfileResponse(response, controlProfile.getId());
    }

    @Test
    void shouldGetControlProfiles() {
        final ControlProfileQuery query = createControlProfileQuery();

        final ControlProfilesQueryResponse response = blocking(() ->
                issuingApi.issuingClient().getControlProfiles(query));

        validateControlProfilesQueryResponse(response);
    }

    @Test
    void shouldUpdateControlProfile() {
        final UpdateControlProfileRequest request = createUpdateControlProfileRequest();

        final ControlProfileResponse response = blocking(() ->
                issuingApi.issuingClient().updateControlProfile(controlProfile.getId(), request));

        validateUpdatedControlProfileResponse(response, controlProfile.getId());
    }

    @Test
    void shouldRemoveControlProfile() {
        final ControlProfileResponse tempControlProfile = createControlProfile();
        
        final EmptyResponse response = blocking(() ->
                issuingApi.issuingClient().removeControlProfile(tempControlProfile.getId()));

        validateEmptyResponse(response);
    }

    @Test
    void shouldAddTargetToControlProfile() {
        final VoidResponse response = blocking(() ->
                issuingApi.issuingClient().addTargetToControlProfile(controlProfile.getId(), card.getId()));

        validateVoidResponse(response);
    }

    @Test
    void shouldRemoveTargetFromControlProfile() {
        final VoidResponse response = blocking(() ->
                issuingApi.issuingClient().removeTargetFromControlProfile(controlProfile.getId(), card.getId()));

        validateVoidResponse(response);
    }

    // Control Profiles - Sync Tests
    @Test
    void shouldCreateControlProfileSync() {
        final ControlProfileResponse controlProfile = createControlProfileSync();
        validateControlProfileCreation(controlProfile);
    }

    @Test
    void shouldGetControlProfileDetailsSync() {
        final ControlProfileResponse response = 
                issuingApi.issuingClient().getControlProfileDetailsSync(controlProfile.getId());

        validateControlProfileResponse(response, controlProfile.getId());
    }

    @Test
    void shouldGetControlProfilesSync() {
        final ControlProfileQuery query = createControlProfileQuery();

        final ControlProfilesQueryResponse response = 
                issuingApi.issuingClient().getControlProfilesSync(query);

        validateControlProfilesQueryResponse(response);
    }

    @Test
    void shouldUpdateControlProfileSync() {
        final UpdateControlProfileRequest request = createUpdateControlProfileRequest();

        final ControlProfileResponse response = 
                issuingApi.issuingClient().updateControlProfileSync(controlProfile.getId(), request);

        validateUpdatedControlProfileResponse(response, controlProfile.getId());
    }

    @Test
    void shouldRemoveControlProfileSync() {
        final ControlProfileResponse tempControlProfile = createControlProfileSync();
        
        final EmptyResponse response = 
                issuingApi.issuingClient().removeControlProfileSync(tempControlProfile.getId());

        validateEmptyResponse(response);
    }

    @Test
    void shouldAddTargetToControlProfileSync() {
        final VoidResponse response = 
                issuingApi.issuingClient().addTargetToControlProfileSync(controlProfile.getId(), card.getId());

        validateVoidResponse(response);
    }

    @Test
    void shouldRemoveTargetFromControlProfileSync() {
        final VoidResponse response = 
                issuingApi.issuingClient().removeTargetFromControlProfileSync(controlProfile.getId(), card.getId());

        validateVoidResponse(response);
    }

    // Common methods
    private ControlProfileQuery createControlProfileQuery() {
        return ControlProfileQuery.builder().build();
    }

    private UpdateControlProfileRequest createUpdateControlProfileRequest() {
        return UpdateControlProfileRequest.builder()
                .name("Updated Control Profile")
                .build();
    }

    private CreateControlProfileRequest createCreateControlProfileRequest() {
        return CreateControlProfileRequest.builder()
                .name("Test Control Profile")
                .build();
    }

    private ControlProfileResponse createControlProfile() {
        final CreateControlProfileRequest request = createCreateControlProfileRequest();

        final ControlProfileResponse response = blocking(() ->
                issuingApi.issuingClient().createControlProfile(request));

        assertNotNull(response);
        return response;
    }

    private ControlProfileResponse createControlProfileSync() {
        final CreateControlProfileRequest request = createCreateControlProfileRequest();

        final ControlProfileResponse response = 
                issuingApi.issuingClient().createControlProfileSync(request);

        assertNotNull(response);
        return response;
    }

    // Validation methods
    private void validateControlProfileCreation(ControlProfileResponse controlProfile) {
        assertNotNull(controlProfile);
        assertNotNull(controlProfile.getId());
        assertEquals("Test Control Profile", controlProfile.getName());
    }

    private void validateControlProfileResponse(ControlProfileResponse response, String expectedId) {
        assertNotNull(response);
        assertEquals(expectedId, response.getId());
    }

    private void validateControlProfilesQueryResponse(ControlProfilesQueryResponse response) {
        assertNotNull(response);
        // Note: May be empty if control profiles were removed by other tests
    }

    private void validateUpdatedControlProfileResponse(ControlProfileResponse response, String expectedId) {
        assertNotNull(response);
        assertEquals(expectedId, response.getId());
    }

    private void validateEmptyResponse(EmptyResponse response) {
        assertNotNull(response);
    }

    private void validateVoidResponse(VoidResponse response) {
        assertNotNull(response);
    }
}