package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.dana;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.OsType;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.TerminalType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The Dana payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class Dana extends PaymentMethodBase {

    /**
     * The client-side terminal type.
     * [Optional]
     * Enum: "APP" "WAP" "WEB"
     */
    private TerminalType terminalType;

    /**
     * The customer's operating system type. Required when terminal_type is not WEB.
     * [Optional]
     * Enum: "ANDROID" "IOS"
     */
    private OsType osType;
}
