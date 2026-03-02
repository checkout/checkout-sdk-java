package com.checkout.issuing;

import com.checkout.common.IdResponse;
import com.checkout.issuing.controls.requests.controlgroup.CreateControlGroupRequest;
import com.checkout.issuing.controls.requests.controlgroup.ControlGroupQuery;
import com.checkout.issuing.controls.requests.controlgroup.VelocityControlGroupControl;
import com.checkout.issuing.controls.responses.controlgroup.ControlGroupResponse;
import com.checkout.issuing.controls.responses.controlgroup.ControlGroupsQueryResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled("3ds not configured and should not create cards every time")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IssuingControlGroupsTestIT extends BaseIssuingTestIT {

    private ControlGroupResponse controlGroup;

    @BeforeAll
    void setUp() {
        controlGroup = createControlGroup();
    }

    // Control Groups - Async Tests
    @Test
    void shouldCreateControlGroup() {
        validateControlGroupCreation(controlGroup);
    }

    @Test
    void shouldGetControlGroupDetails() {
        final ControlGroupResponse response = blocking(() ->
                issuingApi.issuingClient().getControlGroupDetails(controlGroup.getId()));

        validateControlGroupResponse(response, controlGroup.getId());
    }

    @Test
    void shouldGetControlGroups() {
        final ControlGroupQuery query = createControlGroupQuery();

        final ControlGroupsQueryResponse response = blocking(() ->
                issuingApi.issuingClient().getControlGroups(query));

        validateControlGroupsQueryResponse(response);
    }

    @Test
    void shouldRemoveControlGroup() {
        final ControlGroupResponse tempControlGroup = createControlGroup();
        
        final IdResponse response = blocking(() ->
                issuingApi.issuingClient().removeControlGroup(tempControlGroup.getId()));

        validateIdResponse(response, tempControlGroup.getId());
    }

    // Control Groups - Sync Tests
    @Test
    void shouldCreateControlGroupSync() {
        final ControlGroupResponse controlGroup = createControlGroupSync();
        validateControlGroupCreation(controlGroup);
    }

    @Test
    void shouldGetControlGroupDetailsSync() {
        final ControlGroupResponse response = 
                issuingApi.issuingClient().getControlGroupDetailsSync(controlGroup.getId());

        validateControlGroupResponse(response, controlGroup.getId());
    }

    @Test
    void shouldGetControlGroupsSync() {
        final ControlGroupQuery query = createControlGroupQuery();

        final ControlGroupsQueryResponse response = 
                issuingApi.issuingClient().getControlGroupsSync(query);

        validateControlGroupsQueryResponse(response);
    }

    @Test
    void shouldRemoveControlGroupSync() {
        final ControlGroupResponse tempControlGroup = createControlGroupSync();
        
        final IdResponse response = 
                issuingApi.issuingClient().removeControlGroupSync(tempControlGroup.getId());

        validateIdResponse(response, tempControlGroup.getId());
    }

    // Common methods
    private ControlGroupQuery createControlGroupQuery() {
        return ControlGroupQuery.builder().build();
    }

    private CreateControlGroupRequest createCreateControlGroupRequest() {
        return CreateControlGroupRequest.builder()
                .description("Test velocity control group")
                .controls(java.util.List.of(
                        VelocityControlGroupControl.builder()
                                .description("Test velocity control")
                                .velocityLimit(com.checkout.issuing.controls.requests.VelocityLimit.builder()
                                        .amountLimit(1000)
                                        .velocityWindow(com.checkout.issuing.controls.requests.VelocityWindow.builder()
                                                .type(com.checkout.issuing.controls.requests.VelocityWindowType.DAILY)
                                                .build())
                                        .build())
                                .build()
                ))
                .build();
    }

    private ControlGroupResponse createControlGroup() {
        final CreateControlGroupRequest request = createCreateControlGroupRequest();

        final ControlGroupResponse response = blocking(() ->
                issuingApi.issuingClient().createControlGroup(request));

        assertNotNull(response);
        return response;
    }

    private ControlGroupResponse createControlGroupSync() {
        final CreateControlGroupRequest request = createCreateControlGroupRequest();

        final ControlGroupResponse response = 
                issuingApi.issuingClient().createControlGroupSync(request);

        assertNotNull(response);
        return response;
    }

    // Validation methods
    private void validateControlGroupCreation(ControlGroupResponse controlGroup) {
        assertNotNull(controlGroup);
        assertNotNull(controlGroup.getId());
        assertEquals("Test velocity control group", controlGroup.getDescription());
    }

    private void validateControlGroupResponse(ControlGroupResponse response, String expectedId) {
        assertNotNull(response);
        assertEquals(expectedId, response.getId());
    }

    private void validateControlGroupsQueryResponse(ControlGroupsQueryResponse response) {
        assertNotNull(response);
        // Note: May be empty if control groups were removed by other tests
    }

    private void validateIdResponse(IdResponse response, String expectedId) {
        assertNotNull(response);
        assertEquals(expectedId, response.getId());
    }
}