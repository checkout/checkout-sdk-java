package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.bizum;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Bizum payment method configuration.
 * <p>[Read-only]</p>
 * No method-specific fields; inherits {@link PaymentMethodBase#status status} and {@link PaymentMethodBase#flags flags} only.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class Bizum extends PaymentMethodBase {
}