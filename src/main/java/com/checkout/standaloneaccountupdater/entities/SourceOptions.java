package com.checkout.standaloneaccountupdater.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class SourceOptions {

    /**
     * The card details
     */
    private CardDetails card;

    /**
     * Instrument reference
     */
    private InstrumentReference instrument;
}