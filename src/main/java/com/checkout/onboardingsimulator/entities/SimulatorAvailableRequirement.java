package com.checkout.onboardingsimulator.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A requirement field available to mark as due in the Onboarding Simulator.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class SimulatorAvailableRequirement {

    /**
     * The public path of the requirement field.
     * [Optional]
     */
    private String field;

    /**
     * The data type of the field.
     * [Optional]
     */
    private String type;
}
