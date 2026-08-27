package com.checkout.instruments.create;

import com.checkout.common.InstrumentType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Store Bacs Direct Debit account details.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CreateInstrumentBacsRequest extends CreateInstrumentRequest {

    /**
     * The account configuration for the instrument.
     * [Required]
     */
    private CreateBacsInstrumentAccount account;

    /**
     * The details of the Bacs Direct Debit account.
     * [Required]
     */
    private CreateBacsInstrumentData instrumentData;

    /**
     * The account holder details.
     * [Required]
     */
    private CreateBacsAccountHolder accountHolder;

    /**
     * The customer's details. Associates the instrument with an existing or new customer.
     * [Optional]
     */
    private CreateCustomerInstrumentRequest customer;

    @Builder
    private CreateInstrumentBacsRequest(final CreateBacsInstrumentAccount account,
                                        final CreateBacsInstrumentData instrumentData,
                                        final CreateBacsAccountHolder accountHolder,
                                        final CreateCustomerInstrumentRequest customer) {
        super(InstrumentType.BACS);
        this.account = account;
        this.instrumentData = instrumentData;
        this.accountHolder = accountHolder;
        this.customer = customer;
    }

    public CreateInstrumentBacsRequest() {
        super(InstrumentType.BACS);
    }

}
