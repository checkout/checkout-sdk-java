package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.alipay;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.OsType;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.TerminalType;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The Alipay HK payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class AlipayHk extends PaymentMethodBase {

    /**
     * The client-side terminal type.
     * [Optional]
     * Enum: "APP" "WAP" "WEB"
     */
    @SerializedName("terminal_type")
    private TerminalType terminalType;

    /**
     * The customer's operating system type. Required when terminal_type is not WEB.
     * [Optional]
     * Enum: "ANDROID" "IOS"
     */
    @SerializedName("os_type")
    private OsType osType;
}
