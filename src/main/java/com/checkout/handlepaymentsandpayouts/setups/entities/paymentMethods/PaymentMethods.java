package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.bizum.Bizum;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.klarna.Klarna;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.paypal.PayPal;
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
public final class PaymentMethods {

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
     * PayPal payment method configuration
     */
    private PayPal paypal;

    /**
     * Bizum payment method configuration
     */
    private Bizum bizum;
}