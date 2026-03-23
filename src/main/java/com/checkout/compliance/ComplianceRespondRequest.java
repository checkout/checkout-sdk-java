package com.checkout.compliance;

import com.checkout.compliance.entities.ComplianceRequestRespondedFields;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request payload to respond to a compliance request.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ComplianceRespondRequest {

    /**
     * The fields being responded to, grouped by sender and recipient.
     */
    private ComplianceRequestRespondedFields fields;

    /**
     * Optional free-text comment provided with the response.
     */
    private String comments;

}
