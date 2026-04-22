package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.sepa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * The SEPA mandate details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class SepaMandate {

    /**
     * The ID of the mandate.
     * [Optional]
     */
    private String id;

    /**
     * The type of mandate.
     * [Optional]
     */
    private SepaMandateType type;

    /**
     * The date the mandate was signed.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    private LocalDate dateOfSignature;
}
