package com.checkout.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The identification of a representative or individual on the Accounts API v2.0 US variants.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Identification {

    /**
     * Social Security Number (SSN), or Individual Taxpayer Identification Number (ITIN) for non-US
     * citizens.
     * [Required]
     * ^\d{9}$
     * 9 characters
     */
    private String nationalIdNumber;

    /**
     * Not defined by the Accounts API: the identification object carries {@code national_id_number}
     * only. Retained so existing code keeps compiling.
     *
     * @deprecated Not part of any Accounts API schema; the API does not read it. Will be removed in a
     * future major version.
     */
    @Deprecated
    private Document document;

}
