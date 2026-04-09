package com.checkout.metadata.card;

import lombok.Data;

import java.util.List;

/**
 * Additional scheme or local scheme capabilities for US-issued cards that can be used in a
 * PINless debit network. Returned when a full card number or 11-digit BIN is provided.
 */
@Data
public final class SchemeMetadata {

    /**
     * PINless debit metadata for the Accel network.
     * [Optional]
     */
    private List<PinlessDebitSchemeMetadata> accel;

    /**
     * PINless debit metadata for the Pulse network.
     * [Optional]
     */
    private List<PinlessDebitSchemeMetadata> pulse;

    /**
     * PINless debit metadata for the NYCE network.
     * [Optional]
     */
    private List<PinlessDebitSchemeMetadata> nyce;

    /**
     * PINless debit metadata for the Shazam network.
     * [Optional]
     */
    private List<PinlessDebitSchemeMetadata> shazam;

    /**
     * PINless debit metadata for the Star network.
     * [Optional]
     */
    private List<PinlessDebitSchemeMetadata> star;

}
