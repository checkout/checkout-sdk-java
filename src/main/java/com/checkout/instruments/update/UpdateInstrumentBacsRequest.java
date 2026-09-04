package com.checkout.instruments.update;

import com.checkout.common.InstrumentType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Update Bacs Direct Debit account details.
 *
 * <p>Nothing in this request is required by the specification.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class UpdateInstrumentBacsRequest extends UpdateInstrumentRequest {

    /**
     * The details of the Bacs Direct Debit account.
     * [Optional]
     */
    private UpdateBacsInstrumentData instrumentData;

    /**
     * The account holder details.
     * [Optional]
     */
    private UpdateBacsAccountHolder accountHolder;

    @Builder
    private UpdateInstrumentBacsRequest(final UpdateBacsInstrumentData instrumentData,
                                        final UpdateBacsAccountHolder accountHolder) {
        super(InstrumentType.BACS);
        this.instrumentData = instrumentData;
        this.accountHolder = accountHolder;
    }

    public UpdateInstrumentBacsRequest() {
        super(InstrumentType.BACS);
    }

}
