package com.checkout.payments.response.source.contexts;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentContextsStcpayResponseSource extends AbstractPaymentContextsResponseSource implements ResponseSource {

}