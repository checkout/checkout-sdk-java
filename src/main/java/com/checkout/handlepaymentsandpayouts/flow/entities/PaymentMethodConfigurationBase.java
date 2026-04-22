package com.checkout.handlepaymentsandpayouts.flow.entities;

import com.checkout.common.AccountHolder;
import com.checkout.payments.StorePaymentDetailsType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Base class for payment method configurations containing common properties
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class PaymentMethodConfigurationBase {

    /**
     * Specifies whether you intend to store the cardholder's payment details. Default: "disabled"
     * [Optional]
     */
    private StorePaymentDetailsType storePaymentDetails = StorePaymentDetailsType.DISABLED;

    /**
     * The account holder's details.
     * [Optional]
     */
    private AccountHolder accountHolder;
}
