package com.checkout.onboardingsimulator.responses;

import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * Result of running a simulator scenario.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class SimulatorRunScenarioResponse extends Resource {

    /**
     * The ID of the entity.
     * [Optional]
     */
    @SerializedName("entity_id")
    private String entityId;

    /**
     * The ID of the scenario that was run.
     * [Optional]
     */
    @SerializedName("scenario_id")
    private String scenarioId;

    /**
     * The name of the scenario that was run.
     * [Optional]
     */
    @SerializedName("scenario_name")
    private String scenarioName;

    /**
     * The entity status before the scenario ran.
     * [Optional]
     */
    @SerializedName("previous_status")
    private String previousStatus;

    /**
     * The entity status after the scenario ran.
     * [Optional]
     */
    @SerializedName("current_status")
    private String currentStatus;

    /**
     * Requirement fields applied by the scenario, if any.
     * [Optional]
     */
    @SerializedName("requirements_due")
    private List<String> requirementsDue;
}
