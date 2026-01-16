package com.checkout.handlepaymentsandpayouts.setups.entities.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Customer email information
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEmail {

    /**
     * The customer's email address
     */
    private String address;

    /**
     * Whether the email address has been verified
     */
    private Boolean verified;
}