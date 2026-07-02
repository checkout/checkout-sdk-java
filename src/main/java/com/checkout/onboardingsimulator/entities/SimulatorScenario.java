package com.checkout.onboardingsimulator.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A pre-defined scenario available in the Onboarding Simulator.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class SimulatorScenario {

    /**
     * The unique identifier of the scenario.
     * [Optional]
     */
    private String id;

    /**
     * Human-readable name of the scenario.
     * [Optional]
     */
    private String name;

    /**
     * Description of what the scenario does.
     * [Optional]
     */
    private String description;

    /**
     * The action type performed by this scenario.
     * [Optional]
     */
    private String action;

    /**
     * The resulting entity status after the scenario runs. Empty string for non-status actions.
     * [Optional]
     */
    private String status;

    /**
     * Requirement fields applied when the scenario runs, if any.
     * [Optional]
     */
    private List<String> requirementsDue;
}
