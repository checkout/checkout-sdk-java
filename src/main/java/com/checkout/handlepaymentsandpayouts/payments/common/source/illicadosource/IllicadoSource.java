package com.checkout.handlepaymentsandpayouts.payments.common.source.illicadosource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * illicado source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class IllicadoSource extends AbstractSource {

    /**
     * Initializes a new instance of the IllicadoSource class.
     */
    public IllicadoSource() {
        super(SourceType.ILLICADO);
    }

}
