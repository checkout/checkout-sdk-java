package com.checkout.standaloneaccountupdater.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Base class for card expiry date information
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class CardBase {

    /**
     * The expiry month of the card
     * [Required]
     */
    private Integer expiryMonth;

    /**
     * The four-digit expiry year of the card
     * [Required]
     */
    private Integer expiryYear;
}