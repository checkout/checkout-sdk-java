package com.checkout.instruments.update;

import com.checkout.common.InstrumentType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Update ACH bank account instrument response.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class UpdateInstrumentAchResponse extends UpdateInstrumentResponse {

    /**
     * The underlying instrument type. For instruments created from Checkout.com tokens, this will
     * reflect the type of instrument that was tokenized.
     * [Required]
     */
    private final InstrumentType type = InstrumentType.ACH;

    /**
     * The unique identifier of the payment source or destination that can be used later for
     * payments.
     * [Required]
     */
    private String id;

}
