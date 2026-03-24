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
public final class CardDetails extends CardBase {

    /**
     * The card number
     * [Required]
     */
    private String number;
}