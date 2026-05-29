package com.checkout.onboardingsimulator;

import com.checkout.ApiClient;
import com.checkout.CheckoutArgumentException;
import com.checkout.CheckoutConfiguration;
import com.checkout.ItemsResponse;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.onboardingsimulator.entities.SimulatorAvailableRequirement;
import com.checkout.onboardingsimulator.entities.SimulatorEntityStatus;
import com.checkout.onboardingsimulator.entities.SimulatorScenario;
import com.checkout.onboardingsimulator.requests.SimulatorSetRequirementsDueRequest;
import com.checkout.onboardingsimulator.requests.SimulatorSetStatusRequest;
import com.checkout.onboardingsimulator.responses.SimulatorRunScenarioResponse;
import com.checkout.onboardingsimulator.responses.SimulatorSetRequirementsDueResponse;
import com.checkout.onboardingsimulator.responses.SimulatorSetStatusResponse;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OnboardingSimulatorClientImplTest {

    private static final String ENTITY_ID = "ent_w4jelhppmfiufdnatam37wrfc4";
    private static final String SCENARIO_ID = "go_active";
    private static final Type AVAILABLE_REQUIREMENTS_TYPE = new TypeToken<ItemsResponse<SimulatorAvailableRequirement>>() { }
            .getType();
    private static final Type SCENARIOS_TYPE = new TypeToken<ItemsResponse<SimulatorScenario>>() { }
            .getType();

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    private OnboardingSimulatorClient client;

    @BeforeEach
    void setUp() {
        lenient().when(sdkCredentials.getAuthorization(SdkAuthorizationType.OAUTH)).thenReturn(authorization);
        lenient().when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        client = new OnboardingSimulatorClientImpl(apiClient, configuration);
    }

    @Test
    void shouldSetRequirementsDue() throws ExecutionException, InterruptedException {
        final SimulatorSetRequirementsDueRequest request = createSetRequirementsDueRequest();
        final SimulatorSetRequirementsDueResponse expectedResponse = createSetRequirementsDueResponse();

        when(apiClient.postAsync(eq("simulate/entities/" + ENTITY_ID + "/requirements-due"), eq(authorization),
                eq(SimulatorSetRequirementsDueResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final SimulatorSetRequirementsDueResponse actualResponse = client.setRequirementsDue(ENTITY_ID, request).get();

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldRunScenario() throws ExecutionException, InterruptedException {
        final SimulatorRunScenarioResponse expectedResponse = createRunScenarioResponse();

        when(apiClient.postAsync(eq("simulate/entities/" + ENTITY_ID + "/scenarios/" + SCENARIO_ID), eq(authorization),
                eq(SimulatorRunScenarioResponse.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final SimulatorRunScenarioResponse actualResponse = client.runScenario(ENTITY_ID, SCENARIO_ID).get();

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldSetEntityStatus() throws ExecutionException, InterruptedException {
        final SimulatorSetStatusRequest request = createSetStatusRequest();
        final SimulatorSetStatusResponse expectedResponse = createSetStatusResponse();

        when(apiClient.postAsync(eq("simulate/entities/" + ENTITY_ID + "/status"), eq(authorization),
                eq(SimulatorSetStatusResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final SimulatorSetStatusResponse actualResponse = client.setEntityStatus(ENTITY_ID, request).get();

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldListAvailableRequirements() throws ExecutionException, InterruptedException {
        final ItemsResponse<SimulatorAvailableRequirement> expectedResponse = createAvailableRequirementsResponse();

        when(apiClient.getAsync(eq("simulate/requirements-due"), eq(authorization), eq(AVAILABLE_REQUIREMENTS_TYPE)))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final ItemsResponse<SimulatorAvailableRequirement> actualResponse = client.listAvailableRequirements().get();

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldListScenarios() throws ExecutionException, InterruptedException {
        final ItemsResponse<SimulatorScenario> expectedResponse = createScenariosResponse();

        when(apiClient.getAsync(eq("simulate/scenarios"), eq(authorization), eq(SCENARIOS_TYPE)))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final ItemsResponse<SimulatorScenario> actualResponse = client.listScenarios().get();

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldSetRequirementsDueSync() {
        final SimulatorSetRequirementsDueRequest request = createSetRequirementsDueRequest();
        final SimulatorSetRequirementsDueResponse expectedResponse = createSetRequirementsDueResponse();

        when(apiClient.post(eq("simulate/entities/" + ENTITY_ID + "/requirements-due"), eq(authorization),
                eq(SimulatorSetRequirementsDueResponse.class), eq(request), isNull()))
                .thenReturn(expectedResponse);

        final SimulatorSetRequirementsDueResponse actualResponse = client.setRequirementsDueSync(ENTITY_ID, request);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldRunScenarioSync() {
        final SimulatorRunScenarioResponse expectedResponse = createRunScenarioResponse();

        when(apiClient.post(eq("simulate/entities/" + ENTITY_ID + "/scenarios/" + SCENARIO_ID), eq(authorization),
                eq(SimulatorRunScenarioResponse.class), isNull(), isNull()))
                .thenReturn(expectedResponse);

        final SimulatorRunScenarioResponse actualResponse = client.runScenarioSync(ENTITY_ID, SCENARIO_ID);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldSetEntityStatusSync() {
        final SimulatorSetStatusRequest request = createSetStatusRequest();
        final SimulatorSetStatusResponse expectedResponse = createSetStatusResponse();

        when(apiClient.post(eq("simulate/entities/" + ENTITY_ID + "/status"), eq(authorization),
                eq(SimulatorSetStatusResponse.class), eq(request), isNull()))
                .thenReturn(expectedResponse);

        final SimulatorSetStatusResponse actualResponse = client.setEntityStatusSync(ENTITY_ID, request);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldListAvailableRequirementsSync() {
        final ItemsResponse<SimulatorAvailableRequirement> expectedResponse = createAvailableRequirementsResponse();

        when(apiClient.get(eq("simulate/requirements-due"), eq(authorization), eq(AVAILABLE_REQUIREMENTS_TYPE)))
                .thenReturn(expectedResponse);

        final ItemsResponse<SimulatorAvailableRequirement> actualResponse = client.listAvailableRequirementsSync();

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldListScenariosSync() {
        final ItemsResponse<SimulatorScenario> expectedResponse = createScenariosResponse();

        when(apiClient.get(eq("simulate/scenarios"), eq(authorization), eq(SCENARIOS_TYPE)))
                .thenReturn(expectedResponse);

        final ItemsResponse<SimulatorScenario> actualResponse = client.listScenariosSync();

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldThrowExceptionWhenEntityIdIsNullForSetRequirementsDue() {
        try {
            client.setRequirementsDue(null, createSetRequirementsDueRequest());
            fail();
        } catch (final CheckoutArgumentException exception) {
            assertEquals("entityId cannot be null", exception.getMessage());
        }

        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldThrowExceptionWhenRequestIsNullForSetRequirementsDue() {
        try {
            client.setRequirementsDue(ENTITY_ID, null);
            fail();
        } catch (final CheckoutArgumentException exception) {
            assertEquals("request cannot be null", exception.getMessage());
        }

        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldThrowExceptionWhenScenarioIdIsNull() {
        try {
            client.runScenario(ENTITY_ID, null);
            fail();
        } catch (final CheckoutArgumentException exception) {
            assertEquals("scenarioId cannot be null", exception.getMessage());
        }

        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldThrowExceptionWhenRequestIsNullForSetEntityStatus() {
        try {
            client.setEntityStatus(ENTITY_ID, null);
            fail();
        } catch (final CheckoutArgumentException exception) {
            assertEquals("request cannot be null", exception.getMessage());
        }

        verifyNoInteractions(apiClient);
    }

    private SimulatorSetRequirementsDueRequest createSetRequirementsDueRequest() {
        return SimulatorSetRequirementsDueRequest.builder()
                .fields(Collections.singletonList("individual.identification.document"))
                .build();
    }

    private SimulatorSetStatusRequest createSetStatusRequest() {
        return SimulatorSetStatusRequest.builder()
                .status(SimulatorEntityStatus.ACTIVE)
                .build();
    }

    private SimulatorSetRequirementsDueResponse createSetRequirementsDueResponse() {
        return new SimulatorSetRequirementsDueResponse(
                ENTITY_ID,
                "active",
                "requirements_due",
                Collections.singletonList("individual.identification.document"));
    }

    private SimulatorRunScenarioResponse createRunScenarioResponse() {
        return new SimulatorRunScenarioResponse(
                ENTITY_ID,
                SCENARIO_ID,
                "Go Active",
                "requirements_due",
                "active",
                Collections.emptyList());
    }

    private SimulatorSetStatusResponse createSetStatusResponse() {
        return new SimulatorSetStatusResponse(ENTITY_ID, "pending", "active");
    }

    private ItemsResponse<SimulatorAvailableRequirement> createAvailableRequirementsResponse() {
        final ItemsResponse<SimulatorAvailableRequirement> response = new ItemsResponse<>();
        response.setItems(Collections.singletonList(new SimulatorAvailableRequirement(
                "individual.identification.document",
                "string")));
        return response;
    }

    private ItemsResponse<SimulatorScenario> createScenariosResponse() {
        final ItemsResponse<SimulatorScenario> response = new ItemsResponse<>();
        response.setItems(Collections.singletonList(new SimulatorScenario(
                SCENARIO_ID,
                "Go Active",
                "Moves the entity to active status",
                "set_status",
                "active",
                Collections.emptyList())));
        return response;
    }

    private <T> void validateResponse(final T expectedResponse, final T actualResponse) {
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
    }
}