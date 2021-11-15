package com.checkout.payments.four.response.source;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CurrencyAccountResponseSource extends AbstractResponseSource implements ResponseSource {

    private Long amount;

}
