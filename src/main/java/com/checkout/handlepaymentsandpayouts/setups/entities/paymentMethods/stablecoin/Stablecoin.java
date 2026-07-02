package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.stablecoin;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The Stablecoin payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class Stablecoin extends PaymentMethodBase {
}
