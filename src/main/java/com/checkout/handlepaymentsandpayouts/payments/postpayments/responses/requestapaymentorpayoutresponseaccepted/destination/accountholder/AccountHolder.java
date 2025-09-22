package com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponseaccepted.destination.accountholder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AccountHolder {

    /**
     * The payment destination identifier (e.g., a card source identifier)
     * = 30 characters ^(src)_(\w{26})$
     * [Optional]
     */
    private String id;

}
