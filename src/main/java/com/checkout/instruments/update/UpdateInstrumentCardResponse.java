package com.checkout.instruments.update;

import com.checkout.common.InstrumentType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Update card instrument response.
 *
 * <p>The fingerprint is inherited from {@link UpdateInstrumentResponse}. The specification gives
 * this variant a type and a fingerprint only, with no id.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class UpdateInstrumentCardResponse extends UpdateInstrumentResponse {

    /**
     * The type of instrument.
     * [Required]
     */
    private final InstrumentType type = InstrumentType.CARD;

}
