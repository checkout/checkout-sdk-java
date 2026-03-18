package com.checkout.issuing.disputes.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Merchant details in dispute response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisputeMerchant {

    /**
     * The merchant's identifier. This can vary from one acquirer to another.
     */
    private String id;

    /**
     * The merchant's name
     */
    private String name;

    /**
     * The city where the merchant is located
     */
    private String city;

    /**
     * The state where the merchant is located (US only)
     */
    private String state;

    /**
     * The two-digit country code where the merchant is located
     */
    private String countryCode;

    /**
     * The merchant's category code for the disputed transaction
     */
    private String categoryCode;

    /**
     * Any evidence submitted by the merchant during the dispute lifecycle
     */
    private List<String> evidence;

}