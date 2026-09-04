package com.checkout.instruments.create;

import com.checkout.common.InstrumentType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Store ACH bank account details.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CreateInstrumentAchRequest extends CreateInstrumentRequest {

    /**
     * The details of the bank account.
     * [Required]
     */
    private CreateAchInstrumentData instrumentData;

    /**
     * The account holder details.
     * [Required]
     */
    private CreateAchAccountHolder accountHolder;

    /**
     * The customer's details. Associates the instrument with an existing or new customer.
     * [Optional]
     */
    private CreateCustomerInstrumentRequest customer;

    @Builder
    private CreateInstrumentAchRequest(final CreateAchInstrumentData instrumentData,
                                       final CreateAchAccountHolder accountHolder,
                                       final CreateCustomerInstrumentRequest customer) {
        super(InstrumentType.ACH);
        this.instrumentData = instrumentData;
        this.accountHolder = accountHolder;
        this.customer = customer;
    }

    public CreateInstrumentAchRequest() {
        super(InstrumentType.ACH);
    }

}
