package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.vipps;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The Vipps payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class Vipps extends PaymentMethodBase {
}
