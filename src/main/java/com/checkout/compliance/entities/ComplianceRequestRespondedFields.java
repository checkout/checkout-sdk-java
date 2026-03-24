package com.checkout.compliance.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Groups the responded fields by party (sender/recipient).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ComplianceRequestRespondedFields {

    private List<ComplianceRequestRespondedField> sender;

    private List<ComplianceRequestRespondedField> recipient;

}
