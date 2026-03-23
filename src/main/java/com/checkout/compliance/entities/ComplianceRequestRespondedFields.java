package com.checkout.compliance.entities;

import lombok.Data;

import java.util.List;

/**
 * Groups the responded fields by party (sender/recipient).
 */
@Data
public class ComplianceRequestRespondedFields {

    private List<ComplianceRequestRespondedField> sender;

    private List<ComplianceRequestRespondedField> recipient;

}
