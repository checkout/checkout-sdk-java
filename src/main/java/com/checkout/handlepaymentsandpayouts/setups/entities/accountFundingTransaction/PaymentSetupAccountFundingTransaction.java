package com.checkout.handlepaymentsandpayouts.setups.entities.accountFundingTransaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Account funding transaction details for the payment.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentSetupAccountFundingTransaction {

    /**
     * Whether to process this payment as an account funding transaction.
     * [Optional]
     */
    private Boolean enabled;

    /**
     * Specifies the purpose of the account funding transaction.
     * [Optional]
     * Enum: "donations" "education" "emergency_need" "expatriation" "family_support"
     * "financial_services" "gifts" "income" "insurance" "investment" "it_services"
     * "leisure" "loan_payment" "medical_treatment" "other" "pension" "royalties"
     * "savings" "travel_and_tourism"
     */
    private AccountFundingTransactionPurpose purpose;

    /**
     * Account funding transaction sender details.
     * [Optional]
     */
    private AccountFundingTransactionSender sender;

    /**
     * Account funding transaction recipient details.
     * [Optional]
     */
    private AccountFundingTransactionRecipient recipient;
}
