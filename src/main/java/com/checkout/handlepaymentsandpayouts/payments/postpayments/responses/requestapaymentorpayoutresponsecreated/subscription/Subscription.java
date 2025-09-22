package com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated.subscription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * subscription
 * The details of the subscription.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Subscription {

    /**
     * The ID or reference linking a series of recurring payments together.
     * [Optional]
     * &lt;= 50
     */
    private String id;

}
