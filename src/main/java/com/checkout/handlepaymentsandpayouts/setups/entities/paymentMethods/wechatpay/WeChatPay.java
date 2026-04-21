package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.wechatpay;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The WeChatPay payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class WeChatPay extends PaymentMethodBase {
}
