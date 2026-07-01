package com.checkout.accounts;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class InstrumentDetailsAch implements InstrumentDetails {

    /**
     * The alphanumeric value that identifies the account.
     * [Required]
     */
    @SerializedName("account_number")
    private String accountNumber;

    /**
     * The 9-digit American Bankers Association (ABA) routing number that identifies the financial institution.
     * [Required]
     * ^[0-9]{9}$
     */
    @SerializedName("routing_number")
    private String routingNumber;

    /**
     * The type of bank account.
     * [Required]
     * Enum: "savings" "checking"
     */
    @SerializedName("account_type")
    private InstrumentAccountType accountType;

}
