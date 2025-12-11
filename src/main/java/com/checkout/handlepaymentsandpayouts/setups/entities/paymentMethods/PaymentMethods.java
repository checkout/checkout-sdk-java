package com.checkout.handlepaymentsandpayouts.setups.entities;

import com.checkout.handlepaymentsandpayouts.setups.entities.Bizum;
import com.checkout.handlepaymentsandpayouts.setups.entities.Klarna;
import com.checkout.handlepaymentsandpayouts.setups.entities.Stcpay;
import com.checkout.handlepaymentsandpayouts.setups.entities.Tabby;
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