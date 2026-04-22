package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.eps;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The EPS payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class Eps extends PaymentMethodBase {
}
