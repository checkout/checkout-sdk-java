package com.checkout.issuing.disputes.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Evidence as returned from dispute responses
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class DisputeFileEvidence {

    /**
     * The unique identifier for an uploaded file
     */
    private String fileId;

    /**
     * A brief description of the evidence
     */
    private String description;

}