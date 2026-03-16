package com.checkout.handlepaymentsandpayouts.flow.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Configuration options specific to stored card payments.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoredCardConfiguration {

    /**
     * The unique identifier for an existing customer.
     */
    private String customerId;

    /**
     * The unique identifiers for card Instruments.
     */
    private List<String> instrumentIds;

    /**
     * The unique identifier for the payment instrument to present to the customer as the default option.
     */
    private String defaultInstrumentId;
}