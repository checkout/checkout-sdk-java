package com.checkout.handlepaymentsandpayouts.payments.common.source.alipaycnsource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * alipay_cn source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class AlipayCnSource extends AbstractSource {

    /**
     * The payment instrument identifier
     * [Required]
     * ^(src)_(\w{26})$
     */
    private String id;

    /**
     * Initializes a new instance of the AlipayCnSource class.
     */
    @Builder
    private AlipayCnSource(
        final String id
    ) {
        super(SourceType.ALIPAY_CN);
        this.id = id;
    }

    public AlipayCnSource() {
        super(SourceType.ALIPAY_CN);
    }

}
