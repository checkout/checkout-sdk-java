package com.checkout.instruments.create;

import com.checkout.common.InstrumentType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Store ACH bank account instrument response.
 *
 * <p>The id and the fingerprint are inherited from {@link CreateInstrumentResponse}. The
 * fingerprint is required for this variant and matches the pattern {@code ^([a-z0-9]{26})$}.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CreateInstrumentAchResponse extends CreateInstrumentResponse {

    /**
     * The type of instrument.
     * [Required]
     */
    private final InstrumentType type = InstrumentType.ACH;

}
