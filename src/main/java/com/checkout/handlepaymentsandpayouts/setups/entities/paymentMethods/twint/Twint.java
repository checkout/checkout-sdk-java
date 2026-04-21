package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.twint;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The Twint payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class Twint extends PaymentMethodBase {
}
