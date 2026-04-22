package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.alma;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The Alma payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class Alma extends PaymentMethodBase {
}
