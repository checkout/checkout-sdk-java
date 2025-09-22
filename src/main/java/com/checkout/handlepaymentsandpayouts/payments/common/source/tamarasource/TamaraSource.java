package com.checkout.handlepaymentsandpayouts.payments.common.source.tamarasource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * tamara source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class TamaraSource extends AbstractSource {

    /**
     * Initializes a new instance of the TamaraSource class.
     */
    public TamaraSource() {
        super(SourceType.TAMARA);
    }

}
