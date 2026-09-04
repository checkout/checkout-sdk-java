package com.checkout.instruments.create;

import com.checkout.common.CardCategory;
import com.checkout.common.CardType;
import com.checkout.common.CountryCode;
import com.checkout.common.InstrumentType;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

/**
 * Store SEPA mandate instrument response.
 *
 * <p>The id and the fingerprint are inherited from {@link CreateInstrumentResponse}. The
 * fingerprint is required for this variant and matches the pattern {@code ^([a-z0-9]{26})$}.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CreateInstrumentSepaResponse extends CreateInstrumentResponse {

    /**
     * The type of instrument.
     * [Required]
     */
    private final InstrumentType type = InstrumentType.SEPA;

}
