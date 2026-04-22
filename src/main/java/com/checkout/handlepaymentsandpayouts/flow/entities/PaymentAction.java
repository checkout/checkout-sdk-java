package com.checkout.handlepaymentsandpayouts.flow.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Instruction for further payment action required by the customer
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentAction {

    /**
     * The type of action required.
     * [Optional]
     */
    private String type;

    /**
     * A URL the customer should be redirected to for further action (if type requires redirect).
     * [Optional]
     */
    private String url;

    /**
     * Additional data needed for the action.
     * [Optional]
     */
    private Object data;
}
