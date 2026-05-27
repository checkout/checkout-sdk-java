package com.checkout.onboardingsimulator.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Request body for marking requirement fields as due on an entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class SimulatorSetRequirementsDueRequest {

    /**
     * The requirement fields to mark as due. Call the List available requirements endpoint
     * for a list of valid values.
     * [Required]
     */
    private List<String> fields;
}
