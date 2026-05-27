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
 * Response from the Set requirements due simulator endpoint.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class SimulatorSetRequirementsDueResponse extends Resource {

    /**
     * The ID of the entity.
     * [Optional]
     */
    @SerializedName("entity_id")
    private String entityId;

    /**
     * The status before the update.
     * [Optional]
     */
    @SerializedName("previous_status")
    private String previousStatus;

    /**
     * The status after the update.
     * [Optional]
     */
    @SerializedName("current_status")
    private String currentStatus;

    /**
     * The requirement fields that are now marked as due.
     * [Optional]
     */
    @SerializedName("requirements_due")
    private List<String> requirementsDue;
}
