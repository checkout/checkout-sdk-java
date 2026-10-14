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

    /**
     * @deprecated Use {@link #getNumberOfSales()}. The API field is {@code number_of_sales};
     * {@code number_of_trades} is not part of the API and was dropped.
     */
    @Deprecated
    public Integer getNumberOfTrades() {
        return numberOfSales;
    }

    /**
     * @deprecated Use {@link #setNumberOfSales(Integer)}. The API field is {@code number_of_sales};
     * {@code number_of_trades} is not part of the API and was dropped.
     */
    @Deprecated
    public void setNumberOfTrades(final Integer numberOfTrades) {
        this.numberOfSales = numberOfTrades;
    }

    public static class OrderSubMerchantBuilder {

        /**
         * @deprecated Use {@link #numberOfSales(Integer)}. The API field is {@code number_of_sales};
         * {@code number_of_trades} is not part of the API and was dropped.
         */
        @Deprecated
        public OrderSubMerchantBuilder numberOfTrades(final Integer numberOfTrades) {
            return numberOfSales(numberOfTrades);
        }
    }
}
