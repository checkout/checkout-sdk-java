package com.checkout.agenticcommerce.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A risk assessment signal provided by the platform to support fraud decisioning.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskSignal {

    /**
     * Risk signal type identifier.
     * <p>
     * [Required]
     */
    private String type;

    /**
     * Numeric risk score for this signal.
     * <p>
     * [Required]
     */
    private Integer score;

    /**
     * Action recommended or taken from the risk assessment.
     * <p>
     * [Required]
     */
    private String action;
}
