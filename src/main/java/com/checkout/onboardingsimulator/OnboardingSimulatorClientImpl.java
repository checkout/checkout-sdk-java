package com.checkout.onboardingsimulator;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.ItemsResponse;
import com.checkout.SdkAuthorizationType;
import com.checkout.onboardingsimulator.entities.SimulatorAvailableRequirement;
import com.checkout.onboardingsimulator.entities.SimulatorScenario;
import com.checkout.onboardingsimulator.requests.SimulatorSetRequirementsDueRequest;
import com.checkout.onboardingsimulator.requests.SimulatorSetStatusRequest;
import com.checkout.onboardingsimulator.responses.SimulatorRunScenarioResponse;
import com.checkout.onboardingsimulator.responses.SimulatorSetRequirementsDueResponse;
import com.checkout.onboardingsimulator.responses.SimulatorSetStatusResponse;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class OnboardingSimulatorClientImpl extends AbstractClient implements OnboardingSimulatorClient {

    private static final String SIMULATE_PATH = "simulate";
    private static final String ENTITIES_PATH = "entities";
    private static final String REQUIREMENTS_DUE_PATH = "requirements-due";
    private static final String SCENARIOS_PATH = "scenarios";
    private static final String STATUS_PATH = "status";

    private static final Type AVAILABLE_REQUIREMENTS_TYPE = new TypeToken<ItemsResponse<SimulatorAvailableRequirement>>() {}.getType();
    private static final Type SCENARIOS_TYPE = new TypeToken<ItemsResponse<SimulatorScenario>>() {}.getType();

    public OnboardingSimulatorClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.OAUTH);
    }

    @Override
    public CompletableFuture<SimulatorSetRequirementsDueResponse> setRequirementsDue(final String entityId, final SimulatorSetRequirementsDueRequest request) {
        validateParams("entityId", entityId, "request", request);
        return apiClient.postAsync(buildPath(SIMULATE_PATH, ENTITIES_PATH, entityId, REQUIREMENTS_DUE_PATH),
                sdkAuthorization(), SimulatorSetRequirementsDueResponse.class, request, null);
    }

    @Override
    public CompletableFuture<SimulatorRunScenarioResponse> runScenario(final String entityId, final String scenarioId) {
        validateParams("entityId", entityId, "scenarioId", scenarioId);
        return apiClient.postAsync(buildPath(SIMULATE_PATH, ENTITIES_PATH, entityId, SCENARIOS_PATH, scenarioId),
                sdkAuthorization(), SimulatorRunScenarioResponse.class, null, null);
    }

    @Override
    public CompletableFuture<SimulatorSetStatusResponse> setEntityStatus(final String entityId, final SimulatorSetStatusRequest request) {
        validateParams("entityId", entityId, "request", request);
        return apiClient.postAsync(buildPath(SIMULATE_PATH, ENTITIES_PATH, entityId, STATUS_PATH),
                sdkAuthorization(), SimulatorSetStatusResponse.class, request, null);
    }

    @Override
    public CompletableFuture<ItemsResponse<SimulatorAvailableRequirement>> listAvailableRequirements() {
        return apiClient.getAsync(buildPath(SIMULATE_PATH, REQUIREMENTS_DUE_PATH),
                sdkAuthorization(), AVAILABLE_REQUIREMENTS_TYPE);
    }

    @Override
    public CompletableFuture<ItemsResponse<SimulatorScenario>> listScenarios() {
        return apiClient.getAsync(buildPath(SIMULATE_PATH, SCENARIOS_PATH),
                sdkAuthorization(), SCENARIOS_TYPE);
    }

    // Synchronous methods
    @Override
    public SimulatorSetRequirementsDueResponse setRequirementsDueSync(final String entityId, final SimulatorSetRequirementsDueRequest request) {
        validateParams("entityId", entityId, "request", request);
        return apiClient.post(buildPath(SIMULATE_PATH, ENTITIES_PATH, entityId, REQUIREMENTS_DUE_PATH),
                sdkAuthorization(), SimulatorSetRequirementsDueResponse.class, request, null);
    }

    @Override
    public SimulatorRunScenarioResponse runScenarioSync(final String entityId, final String scenarioId) {
        validateParams("entityId", entityId, "scenarioId", scenarioId);
        return apiClient.post(buildPath(SIMULATE_PATH, ENTITIES_PATH, entityId, SCENARIOS_PATH, scenarioId),
                sdkAuthorization(), SimulatorRunScenarioResponse.class, null, null);
    }

    @Override
    public SimulatorSetStatusResponse setEntityStatusSync(final String entityId, final SimulatorSetStatusRequest request) {
        validateParams("entityId", entityId, "request", request);
        return apiClient.post(buildPath(SIMULATE_PATH, ENTITIES_PATH, entityId, STATUS_PATH),
                sdkAuthorization(), SimulatorSetStatusResponse.class, request, null);
    }

    @Override
    public ItemsResponse<SimulatorAvailableRequirement> listAvailableRequirementsSync() {
        return apiClient.get(buildPath(SIMULATE_PATH, REQUIREMENTS_DUE_PATH),
                sdkAuthorization(), AVAILABLE_REQUIREMENTS_TYPE);
    }

    @Override
    public ItemsResponse<SimulatorScenario> listScenariosSync() {
        return apiClient.get(buildPath(SIMULATE_PATH, SCENARIOS_PATH),
                sdkAuthorization(), SCENARIOS_TYPE);
    }
}
