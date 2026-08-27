package com.checkout.payments.request.source.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.request.source.AbstractRequestSource;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Bacs Direct Debit source.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestBacsSource extends AbstractRequestSource {

    /**
     * The Bacs Direct Debit instrument ID.
     * [Required]
     * ^(src)_(\w{26})$
     */
    private String id;

    @Builder
    private RequestBacsSource(final String id) {
        super(PaymentSourceType.BACS);
        this.id = id;
    }

    public RequestBacsSource() {
        super(PaymentSourceType.BACS);
    }

}
