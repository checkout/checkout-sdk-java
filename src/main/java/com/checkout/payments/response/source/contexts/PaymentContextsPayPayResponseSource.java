package com.checkout.payments.response.source.contexts;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public final class PaymentContextsPayPayResponseSource extends AbstractPaymentContextsResponseSource implements ResponseSource {

}
