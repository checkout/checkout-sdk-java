package com.checkout.handlepaymentsandpayouts.payments.common.source.sepasource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * sepa source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class SepaSource extends AbstractSource {

    /**
     * The instrument ID
     * [Required]
     */
    private String id;

    /**
     * Initializes a new instance of the SepaSource class.
     */
    @Builder
    private SepaSource(
        final String id
    ) {
        super(SourceType.SEPA);
        this.id = id;
    }

    public SepaSource() {
        super(SourceType.SEPA);
    }

}
