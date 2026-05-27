package com.checkout.onboardingsimulator.requests;

import com.checkout.onboardingsimulator.entities.SimulatorEntityStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request body for forcing the entity to a specific status.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class SimulatorSetStatusRequest {

    /**
     * The status to set on the entity.
     * [Required]
     * Enum: "draft" "requirements_due" "pending" "active" "restricted" "rejected" "inactive"
     */
    private SimulatorEntityStatus status;
}
