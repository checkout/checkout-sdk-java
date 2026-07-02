package com.checkout.onboardingsimulator.responses;

import com.checkout.common.Resource;
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
    private String entityId;

    /**
     * The status before the update.
     * [Optional]
     */
    private String previousStatus;

    /**
     * The status after the update.
     * [Optional]
     */
    private String currentStatus;

    /**
     * The requirement fields that are now marked as due.
     * [Optional]
     */
    private List<String> requirementsDue;
}
