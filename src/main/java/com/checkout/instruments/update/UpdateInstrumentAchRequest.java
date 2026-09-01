package com.checkout.instruments.update;

import com.checkout.common.InstrumentType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Update ACH bank account details.
 *
 * <p>Nothing in this request is required by the specification.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class UpdateInstrumentAchRequest extends UpdateInstrumentRequest {

    /**
     * The details of the bank account.
     * [Optional]
     */
    private UpdateAchInstrumentData instrumentData;

    /**
     * The account holder details.
     * [Optional]
     */
    private UpdateAchAccountHolder accountHolder;

    @Builder
    private UpdateInstrumentAchRequest(final UpdateAchInstrumentData instrumentData,
                                       final UpdateAchAccountHolder accountHolder) {
        super(InstrumentType.ACH);
        this.instrumentData = instrumentData;
        this.accountHolder = accountHolder;
    }

    public UpdateInstrumentAchRequest() {
        super(InstrumentType.ACH);
    }

}
