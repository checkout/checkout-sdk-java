package com.checkout.handlepaymentsandpayouts.payments.common.source.currencyaccountsource;

import com.checkout.handlepaymentsandpayouts.payments.common.source.AbstractSource;
import com.checkout.handlepaymentsandpayouts.payments.common.source.SourceType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * currency_account source Class
 * The source of the payment
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CurrencyAccountSource extends AbstractSource {

    /**
     * The ID of the currency account
     * [Required]
     * ^(ca)_(\w{26})$
     */
    private String id;

    /**
     * If specified, indicates the amount in the source currency to be paid out. If omitted, the root amount in the
     * destination currency will be used. The amount must be provided in the minor currency unit.
     * [Optional]
     */
    private Integer amount;

    /**
     * Initializes a new instance of the CurrencyAccountSource class.
     */
    @Builder
    private CurrencyAccountSource(
            final String id,
            final Integer amount
    ) {
        super(SourceType.CURRENCY_ACCOUNT);
        this.id = id;
        this.amount = amount;
    }

    public CurrencyAccountSource() {
        super(SourceType.CURRENCY_ACCOUNT);
    }

}
