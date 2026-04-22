package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.paynow;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The PayNow payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class PayNow extends PaymentMethodBase {
}
