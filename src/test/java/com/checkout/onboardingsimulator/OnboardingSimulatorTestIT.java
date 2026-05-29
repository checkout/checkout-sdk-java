package com.checkout.onboardingsimulator;

import com.checkout.ItemsResponse;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.onboardingsimulator.entities.SimulatorAvailableRequirement;
import com.checkout.onboardingsimulator.entities.SimulatorEntityStatus;
import com.checkout.onboardingsimulator.entities.SimulatorScenario;
import com.checkout.onboardingsimulator.requests.SimulatorSetRequirementsDueRequest;
import com.checkout.onboardingsimulator.requests.SimulatorSetStatusRequest;
import com.checkout.onboardingsimulator.responses.SimulatorRunScenarioResponse;
import com.checkout.onboardingsimulator.responses.SimulatorSetRequirementsDueResponse;
import com.checkout.onboardingsimulator.responses.SimulatorSetStatusResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OnboardingSimulatorTestIT extends SandboxTestFixture {

    OnboardingSimulatorTestIT() {
        super(PlatformType.DEFAULT_OAUTH);
    }

    @Disabled("Sandbox-only. Requires an existing sub-entity ID")
    @Test
    void shouldListAvailableRequirements() {
        final ItemsResponse<SimulatorAvailableRequirement> response = blocking(() -> checkoutApi.onboardingSimulatorClient().listAvailableRequirements());

        assertNotNull(response);
        assertNotNull(response.getItems());
        assertFalse(response.getItems().isEmpty());
        assertNotNull(response.getItems().get(0).getField());
    }

    @Disabled("Sandbox-only. Requires an existing sub-entity ID")
    @Test
    void shouldListScenarios() {
        final ItemsResponse<SimulatorScenario> response = blocking(() -> checkoutApi.onboardingSimulatorClient().listScenarios());

        assertNotNull(response);
        assertNotNull(response.getItems());
        assertFalse(response.getItems().isEmpty());
        assertNotNull(response.getItems().get(0).getId());
    }

    @Disabled("Sandbox-only. Requires an existing sub-entity ID")
    @Test
    void shouldSetRequirementsDue() {
        final String entityId = Objects.requireNonNull(System.getenv("CHECKOUT_DEFAULT_ENTITY_ID"));
        final SimulatorSetRequirementsDueRequest request = SimulatorSetRequirementsDueRequest.builder()
                .fields(Collections.singletonList("individual.identification.document"))
                .build();

        final SimulatorSetRequirementsDueResponse response = blocking(() -> checkoutApi.onboardingSimulatorClient().setRequirementsDue(entityId, request));

        assertNotNull(response);
        assertEquals(entityId, response.getEntityId());
        assertNotNull(response.getCurrentStatus());
        assertNotNull(response.getRequirementsDue());
    }

    @Disabled("Sandbox-only. Requires an existing sub-entity ID")
    @Test
    void shouldRunScenario() {
        final String entityId = Objects.requireNonNull(System.getenv("CHECKOUT_DEFAULT_ENTITY_ID"));
        final String scenarioId = "go_active";

        final SimulatorRunScenarioResponse response = blocking(() -> checkoutApi.onboardingSimulatorClient().runScenario(entityId, scenarioId));

        assertNotNull(response);
        assertEquals(entityId, response.getEntityId());
        assertEquals(scenarioId, response.getScenarioId());
        assertNotNull(response.getCurrentStatus());
    }

    @Disabled("Sandbox-only. Requires an existing sub-entity ID")
    @Test
    void shouldSetEntityStatus() {
        final String entityId = Objects.requireNonNull(System.getenv("CHECKOUT_DEFAULT_ENTITY_ID"));
        final SimulatorSetStatusRequest request = SimulatorSetStatusRequest.builder()
                .status(SimulatorEntityStatus.ACTIVE)
                .build();

        final SimulatorSetStatusResponse response = blocking(() -> checkoutApi.onboardingSimulatorClient().setEntityStatus(entityId, request));

        assertNotNull(response);
        assertEquals(entityId, response.getEntityId());
        assertNotNull(response.getCurrentStatus());
    }
}