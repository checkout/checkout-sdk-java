package com.checkout.payments.response.source;

import com.checkout.common.PaymentSourceType;
import lombok.Data;

/**
 * The properties every typed payment response source shares.
 */
@Data
public abstract class AbstractResponseSource {

    /**
     * The payment source type.
     * [Required]
     */
    public PaymentSourceType type;

    /**
     * The payment source identifier that can be used for subsequent payments. For new sources,
     * this is only returned if the payment was approved.
     * [Optional]
     */
    public String id;

}
