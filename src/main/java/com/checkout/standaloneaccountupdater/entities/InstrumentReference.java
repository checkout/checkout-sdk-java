package com.checkout.standaloneaccountupdater.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class InstrumentReference {

    /**
     * Unique instrument identifier
     * [Required]
     */
    private String id;
}