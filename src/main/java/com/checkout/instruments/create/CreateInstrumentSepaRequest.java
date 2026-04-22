package com.checkout.instruments.create;

import com.checkout.common.AccountHolder;
import com.checkout.common.InstrumentType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CreateInstrumentSepaRequest extends CreateInstrumentRequest {

    /**
     * The details of the SEPA mandate, including IBAN, country, currency, payment type, and mandate ID.
     * [Required]
     */
    private InstrumentData instrumentData;

    /**
     * The account holder details. Must include first_name, last_name, and billing_address.
     * [Required]
     */
    private AccountHolder accountHolder;

    /**
     * The customer details. Associates the instrument with an existing or new customer.
     * [Optional]
     */
    private CreateCustomerInstrumentRequest customer;

    @Builder
    private CreateInstrumentSepaRequest(final InstrumentData instrumentData,
                                        final AccountHolder accountHolder,
                                        final CreateCustomerInstrumentRequest customer) {
        super(InstrumentType.SEPA);
        this.instrumentData = instrumentData;
        this.accountHolder = accountHolder;
        this.customer = customer;
    }

    public CreateInstrumentSepaRequest() {
        super(InstrumentType.SEPA);
    }
}
