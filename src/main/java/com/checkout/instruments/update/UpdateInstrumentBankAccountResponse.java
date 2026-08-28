package com.checkout.instruments.update;

import com.checkout.common.InstrumentType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Update bank account instrument response.
 *
 * <p>The fingerprint is inherited from {@link UpdateInstrumentResponse}. The specification gives
 * this variant a type and a fingerprint only, with no id.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class UpdateInstrumentBankAccountResponse extends UpdateInstrumentResponse {

    /**
     * The type of instrument.
     * [Required]
     */
    private final InstrumentType type = InstrumentType.BANK_ACCOUNT;

}
