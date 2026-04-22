package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.benefit;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The Benefit payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class Benefit extends PaymentMethodBase {
}
