package com.checkout.handlepaymentsandpayouts.payments.common.source.bankaccountsource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * bank_account source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class BankAccountSource extends AbstractSource {

    /**
     * The payment instrument identifier
     * [Required]
     * ^(src)_(\w{26})$
     */
    private String id;

    /**
     * Initializes a new instance of the BankAccountSource class.
     */
    @Builder
    private BankAccountSource(
        final String id
    ) {
        super(SourceType.BANK_ACCOUNT);
        this.id = id;
    }

    public BankAccountSource() {
        super(SourceType.BANK_ACCOUNT);
    }

}
