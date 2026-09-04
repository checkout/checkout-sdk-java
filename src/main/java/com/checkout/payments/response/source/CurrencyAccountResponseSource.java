package com.checkout.payments.response.source;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Currency account source.
 *
 * <p>The type and the id are inherited from {@link AbstractResponseSource}. For this variant the
 * specification declares the id required, with the pattern {@code ^(ca)_(\w{26})$}.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CurrencyAccountResponseSource extends AbstractResponseSource implements ResponseSource {

    /**
     * If specified, indicates the amount in the source currency to be paid out. If omitted, the
     * root amount in the destination currency is used.
     * [Optional]
     */
    private Long amount;

    /**
     * The currency of the currency account.
     * [Optional]
     */
    private String currency;

}
