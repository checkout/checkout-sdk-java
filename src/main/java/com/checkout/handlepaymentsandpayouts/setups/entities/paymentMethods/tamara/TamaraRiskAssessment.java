package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.tamara;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Risk signals about the customer, forwarded to Tamara with the payment.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class TamaraRiskAssessment {

    /**
     * Specifies whether the customer is a premium customer.
     * [Optional]
     */
    private Boolean premiumCustomer;

    /**
     * The customer's risk score.
     * [Optional]
     */
    private Integer riskScore;

    /**
     * The number of orders the customer has made in the last 24 hours. Serialized as
     * {@code order_count_last24h}.
     * [Optional]
     */
    private Integer orderCountLast24h;

    /**
     * The number of devices linked to the customer.
     * [Optional]
     */
    private Integer linkedDeviceCount;
}
