package com.checkout.identities.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The applicant's mobile phone number, if sharing the attempt URL via SMS.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PhoneNumber {

    /**
     * The international phone country code. This is a dialling prefix, not an ISO country code.
     * [Required]
     * Pattern: ^\+(\d+)$
     * Example: +33
     */
    private String countryCode;

    /**
     * The applicant's mobile number, without the country code.
     * [Required]
     * Pattern: ^\d{1,14}$
     * Example: 5555550102
     */
    private String number;

}
