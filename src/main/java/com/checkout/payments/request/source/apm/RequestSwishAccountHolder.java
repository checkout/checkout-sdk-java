package com.checkout.payments.request.source.apm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The account holder details for a Swish payment.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class RequestSwishAccountHolder {

    /**
     * The account holder's first name.
     * [Required]
     * max 50 characters
     */
    private String firstName;

    /**
     * The account holder's last name.
     * [Required]
     * max 50 characters
     */
    private String lastName;
}