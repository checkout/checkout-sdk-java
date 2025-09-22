package com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.source;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * source
 * The source of the unreferenced refund.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Source {

    /**
     * The unreferenced refund source type.
     * [Required]
     */
    private String type;

    /**
     * The ID of the currency account that will fund the unreferenced refund.
     * [Required]
     * ^(ca)_(\w{26})$
     * 29 characters
     */
    private String id;

}
