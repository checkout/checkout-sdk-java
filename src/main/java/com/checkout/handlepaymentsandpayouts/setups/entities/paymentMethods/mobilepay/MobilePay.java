package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.mobilepay;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The MobilePay payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class MobilePay extends PaymentMethodBase {
}
