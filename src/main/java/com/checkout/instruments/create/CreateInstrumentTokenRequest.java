package com.checkout.instruments.create;

import com.checkout.common.AccountHolder;
import com.checkout.common.InstrumentType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Store token details.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CreateInstrumentTokenRequest extends CreateInstrumentRequest {

    /**
     * The Checkout.com token.
     * [Required]
     * ^(tok)_(\w{26})$|^(card_tok)_(\w{12})$
     */
    private String token;

    /**
     * The account holder details.
     * [Optional]
     */
    private AccountHolder accountHolder;

    /**
     * The customer's details. Associates the instrument with an existing or new customer.
     * [Optional]
     */
    private CreateCustomerInstrumentRequest customer;

    @Builder
    private CreateInstrumentTokenRequest(final String token,
                                         final AccountHolder accountHolder,
                                         final CreateCustomerInstrumentRequest customer) {
        super(InstrumentType.TOKEN);
        this.token = token;
        this.accountHolder = accountHolder;
        this.customer = customer;
    }

    public CreateInstrumentTokenRequest() {
        super(InstrumentType.TOKEN);
    }

}
