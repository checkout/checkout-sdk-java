package com.checkout.handlepaymentsandpayouts.setups.entities.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Sub-merchant information for order
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class OrderSubMerchant {

    /**
     * The unique identifier for the sub-merchant.
     * [Optional]
     */
    private String id;

    /**
     * The product category for the sub-merchant.
     * [Optional]
     */
    private String productCategory;

    /**
     * The number of orders the sub-merchant has processed.
     * [Optional]
     */
    private Integer numberOfSales;

    /**
     * The date the sub-merchant was registered.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    private LocalDate registrationDate;
}
