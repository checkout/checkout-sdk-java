package com.checkout.payments.response.source;

import com.checkout.common.CheckoutUtils;
import com.checkout.common.PaymentSourceType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.EnumUtils;

import java.util.HashMap;

/**
 * The fallback payment response source, used for any source type the SDK does not model as a
 * dedicated class. The raw JSON is exposed as map entries.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class AlternativePaymentSourceResponse extends HashMap<String, Object> implements ResponseSource {

    /**
     * Returns the payment source type resolved from the raw type entry.
     *
     * @return the payment source type, or null if the type entry is absent or unrecognised.
     */
    @Override
    public PaymentSourceType getType() {
        return EnumUtils.getEnumIgnoreCase(PaymentSourceType.class, (String) get(CheckoutUtils.TYPE));
    }

}
