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
     * [Required]
     * Format: email
     */
    private String primary;

}
