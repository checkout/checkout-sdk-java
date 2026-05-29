package com.checkout.onboardingsimulator;

import com.checkout.ItemsResponse;
import com.checkout.onboardingsimulator.entities.SimulatorAvailableRequirement;
import com.checkout.onboardingsimulator.entities.SimulatorScenario;
import com.checkout.onboardingsimulator.requests.SimulatorSetRequirementsDueRequest;
import com.checkout.onboardingsimulator.requests.SimulatorSetStatusRequest;
import com.checkout.onboardingsimulator.responses.SimulatorRunScenarioResponse;
import com.checkout.onboardingsimulator.responses.SimulatorSetRequirementsDueResponse;
import com.checkout.onboardingsimulator.responses.SimulatorSetStatusResponse;

import java.util.concurrent.CompletableFuture;

/**
 * Onboarding Simulator client. Sandbox only — endpoints are not registered in production.
 */
public interface OnboardingSimulatorClient {

    CompletableFuture<SimulatorSetRequirementsDueResponse> setRequirementsDue(String entityId, SimulatorSetRequirementsDueRequest request);

    CompletableFuture<SimulatorRunScenarioResponse> runScenario(String entityId, String scenarioId);

    CompletableFuture<SimulatorSetStatusResponse> setEntityStatus(String entityId, SimulatorSetStatusRequest request);

    CompletableFuture<ItemsResponse<SimulatorAvailableRequirement>> listAvailableRequirements();

    CompletableFuture<ItemsResponse<SimulatorScenario>> listScenarios();

    // Synchronous methods
    SimulatorSetRequirementsDueResponse setRequirementsDueSync(String entityId, SimulatorSetRequirementsDueRequest request);

    SimulatorRunScenarioResponse runScenarioSync(String entityId, String scenarioId);

    SimulatorSetStatusResponse setEntityStatusSync(String entityId, SimulatorSetStatusRequest request);

    ItemsResponse<SimulatorAvailableRequirement> listAvailableRequirementsSync();

    ItemsResponse<SimulatorScenario> listScenariosSync();
}
