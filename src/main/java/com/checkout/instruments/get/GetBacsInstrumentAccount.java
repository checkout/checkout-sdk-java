package com.checkout.instruments.get;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The account configuration for a stored Bacs Direct Debit instrument.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class GetBacsInstrumentAccount {

    /**
     * The ID of the client associated with the instrument.
     * [Optional]
     */
    private String clientId;

    /**
     * The ID of the processing channel associated with the instrument.
     * [Optional]
     */
    private String processingChannelId;

}
