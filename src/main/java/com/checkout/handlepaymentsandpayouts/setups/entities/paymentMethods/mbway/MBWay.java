package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.mbway;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The MBWay payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class MBWay extends PaymentMethodBase {
}
