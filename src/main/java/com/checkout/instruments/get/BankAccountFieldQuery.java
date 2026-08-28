package com.checkout.instruments.get;

import com.checkout.common.AccountHolderType;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

/**
 * The optional query parameters for the bank account field formatting request.
 */
@Data
@Builder
public final class BankAccountFieldQuery {

    /**
     * The type of account holder that will be used to filter the fields returned.
     * [Optional]
     * Enum: "individual" "corporate" "government"
     */
    @SerializedName("account-holder-type")
    private AccountHolderType accountHolderType;

    /**
     * The banking network that will be used to filter the fields returned.
     * [Optional]
     * Enum: "local" "sepa" "fps" "ach" "fedwire" "swift"
     */
    @SerializedName("payment-network")
    private PaymentNetwork paymentNetwork;

}
