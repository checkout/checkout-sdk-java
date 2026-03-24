package com.checkout.standaloneaccountupdater.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public final class CardUpdated extends CardBase {

    /**
     * The encrypted full Primary Account Number (PAN). Returned only for PCI SAQ D merchants.
     */
    private String encryptedCardNumber;

    /**
     * The first 6 digits of the PAN.
     */
    private String bin;

    /**
     * Last 4 digits of the PAN.
     */
    private String last4;

    /**
     * Unique identifier for the card
     */
    private String fingerprint;
}