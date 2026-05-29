package com.checkout.handlepaymentsandpayouts.setups.entities.accountFundingTransaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Account funding transaction sender details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AccountFundingTransactionSender {

    /**
     * Date of birth of the sender.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    private LocalDate dateOfBirth;

    /**
     * The unique reference for the sender of the payment.
     * [Optional]
     */
    private String reference;

    /**
     * Sender identification details.
     * [Optional]
     */
    private AccountFundingTransactionIdentification identification;
}
