package com.checkout.payments.response.source;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static com.checkout.common.PaymentSourceType.BACS;

/**
 * Bacs Direct Debit source.
 *
 * <p>The specification declares a type and an id only, both of which the shared
 * {@link AbstractResponseSource} already carries.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class BacsResponseSource extends AbstractResponseSource implements ResponseSource {

    public BacsResponseSource() {
        this.type = BACS;
    }

}
