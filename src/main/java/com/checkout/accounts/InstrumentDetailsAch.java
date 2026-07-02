package com.checkout.accounts;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class InstrumentDetailsAch implements InstrumentDetails {

    /**
     * The alphanumeric value that identifies the account.
     * [Required]
     */
    private String accountNumber;

    /**
     * The 9-digit American Bankers Association (ABA) routing number that identifies the financial institution.
     * [Required]
     * ^[0-9]{9}$
     */
    private String routingNumber;

    /**
     * The type of bank account.
     * [Required]
     * Enum: "savings" "checking"
     */
    private InstrumentAccountType accountType;

}
