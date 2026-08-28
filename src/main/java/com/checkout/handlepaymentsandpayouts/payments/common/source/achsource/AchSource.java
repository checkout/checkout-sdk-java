package com.checkout.handlepaymentsandpayouts.payments.common.source.achsource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ach source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class AchSource extends AbstractSource {

    /**
     * The payment instrument identifier
     * [Required]
     * ^(src)_(\w{26})$
     */
    private String id;

    /**
     * Initializes a new instance of the AchSource class.
     */
    @Builder
    private AchSource(
        final String id
    ) {
        super(SourceType.ACH);
        this.id = id;
    }

    public AchSource() {
        super(SourceType.ACH);
    }

}
