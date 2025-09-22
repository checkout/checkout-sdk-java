package com.checkout.handlepaymentsandpayouts.payments.common.source.octopussource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * octopus source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class OctopusSource extends AbstractSource {

    /**
     * Initializes a new instance of the OctopusSource class.
     */
    public OctopusSource() {
        super(SourceType.OCTOPUS);
    }

}
