package com.checkout.identities.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Session information
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ApplicantSessionInformation {
    /**
     * The applicant's IP address during the attempt.
     */
    private String ipAddress;
}