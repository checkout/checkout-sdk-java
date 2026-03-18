package com.checkout.handlepaymentsandpayouts.flow.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Configuration options specific to card payments
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CardConfiguration extends PaymentMethodConfigurationBase {
    // Empty class - inherits all properties from base
}