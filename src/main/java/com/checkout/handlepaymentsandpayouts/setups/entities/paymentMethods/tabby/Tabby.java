package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.tabby;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodInitialization;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Tabby payment method configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class Tabby extends PaymentMethodBase {

    /**
     * The initialization state of the payment method. Defaults to disabled.
     * <p>[Optional]</p>
     */
    private PaymentMethodInitialization initialization = PaymentMethodInitialization.DISABLED;

    /**
     * The available payment types for Tabby (for example, {@code installments}).
     * <p>[Read-only]</p>
     */
    @SerializedName("payment_types")
    private List<String> paymentTypes;
}