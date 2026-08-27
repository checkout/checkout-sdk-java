package com.checkout.instruments.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The account configuration for a Bacs Direct Debit instrument being stored.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CreateBacsInstrumentAccount {

    /**
     * The ID of the processing channel to associate with the instrument.
     * [Required]
     * ^(pc)_(\w{26})$
     */
    private String processingChannelId;

}
