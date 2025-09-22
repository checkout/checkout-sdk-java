package com.checkout.handlepaymentsandpayouts.payments.common.source.bancontactsource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * bancontact source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class BancontactSource extends AbstractSource {

    /**
     * The IBAN of the Consumer Bank account used for payment (if applicable).
     * [Optional]
     * &lt;= 34
     */
    private String iban;

    /**
     * Initializes a new instance of the BancontactSource class.
     */
    @Builder
    private BancontactSource(
        final String iban
    ) {
        super(SourceType.BANCONTACT);
        this.iban = iban;
    }

    public BancontactSource() {
        super(SourceType.BANCONTACT);
    }

}
