package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.bizum.Bizum;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.klarna.Klarna;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.stcpay.Stcpay;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.tabby.Tabby;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Payment methods configuration
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethods {

    /**
     * Klarna payment method configuration
     */
    private Klarna klarna;

    /**
     * STC Pay payment method configuration
     */
    private Stcpay stcpay;

    /**
     * Tabby payment method configuration
     */
    private Tabby tabby;

    /**
     * Bizum payment method configuration
     */
    private Bizum bizum;
}