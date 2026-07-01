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
     * The unique identifier of the sub-merchant
     */
    private String id;

    /**
     * The category of products or services offered by the sub-merchant
     */
    private String productCategory;

    /**
     * The number of trades or transactions the sub-merchant has conducted
     */
    private Integer numberOfTrades;

    /**
     * The date the sub-merchant was registered.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    private LocalDate registrationDate;
}