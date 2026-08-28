package com.checkout.handlepaymentsandpayouts.payments.common.source.bacssource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * bacs source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class BacsSource extends AbstractSource {

    /**
     * The payment instrument identifier
     * [Required]
     * ^(src)_(\w{26})$
     */
    private String id;

    /**
     * Initializes a new instance of the BacsSource class.
     */
    @Builder
    private BacsSource(
        final String id
    ) {
        super(SourceType.BACS);
        this.id = id;
    }

    public BacsSource() {
        super(SourceType.BACS);
    }

}
