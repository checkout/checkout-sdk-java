package com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.segment;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * segment
 * The dimension details about business segment for payment request. At least one dimension required.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Segment {

    /**
     * The brand of business segment.
     * [Optional]
     * &lt;= 50
     */
    private String brand;

    /**
     * The category of business segment.
     * [Optional]
     * &lt;= 50
     */
    private String businessCategory;

    /**
     * The market of business segment.
     * [Optional]
     * &lt;= 50
     */
    private String market;

}
