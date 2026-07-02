package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.paybybank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The next available action for the Pay by Bank payment method (response only).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PayByBankAction {

    /**
     * The type of action.
     * [Optional]
     * Enum: "select_bank"
     */
    private String type;

    /**
     * The list of banks available for the customer to select.
     * [Optional]
     */
    private List<PayByBankBank> banks;
}
