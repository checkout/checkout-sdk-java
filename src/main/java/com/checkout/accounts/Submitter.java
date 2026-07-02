package com.checkout.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Captured as evidence of the end-user's consent to onboarding.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Submitter {

    /**
     * IP address of the end-user (the sub-entity's representative) submitting the onboarding request.
     * [Required]
     */
    private String ipAddress;
}
