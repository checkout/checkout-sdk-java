package com.checkout.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Email addresses for this sub-entity.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class EntityEmailAddresses {

    /**
     * The main email address for this sub-entity.
     * [Required] in every variant that has email addresses.
     * Format: email
     */
    private String primary;

    /**
     * The email address of the person responsible for PCI compliance at this sub-entity.
     * [Required] for the US ISV Seller variants (3.0), together with primary; not part of the other variants.
     * Format: email
     */
    private String pciComplianceContact;

}
