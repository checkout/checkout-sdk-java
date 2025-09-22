package com.checkout.handlepaymentsandpayouts.payments.common.source.almasource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * alma source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class AlmaSource extends AbstractSource {

    /**
     * Initializes a new instance of the AlmaSource class.
     */
    public AlmaSource() {
        super(SourceType.ALMA);
    }

}
