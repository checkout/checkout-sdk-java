package com.checkout.compliance.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Describes a single response field provided for a compliance request.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ComplianceRequestRespondedField {

    private String name;

    private String value;

    private Boolean notAvailable;

}
