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
public class PaymentAction {

    /**
     * The type of action required
     */
    private String type;

    /**
     * A URL the customer should be redirected to for further action (if type requires redirect)
     */
    private String url;

    /**
     * Additional data needed for the action
     */
    private Object data;
}