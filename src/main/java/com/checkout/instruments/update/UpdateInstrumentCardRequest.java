package com.checkout.instruments.update;

import com.checkout.common.AccountHolder;
import com.checkout.common.InstrumentType;
import com.checkout.common.UpdateCustomerRequest;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Update card details.
 *
 * <p>Nothing in this request is required by the specification.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class UpdateInstrumentCardRequest extends UpdateInstrumentRequest {

    /**
     * The expiry month of the card.
     * [Optional]
     * min 1 characters
     * max 2 characters
     * min 1
     */
    private Integer expiryMonth;

    /**
     * The expiry year of the card.
     * [Optional]
     * min 4 characters
     * max 4 characters
     */
    private Integer expiryYear;

    /**
     * Name of the cardholder.
     * [Optional]
     */
    private String name;

    /**
     * The account holder details.
     * [Optional]
     */
    private AccountHolder accountHolder;

    /**
     * The customer's details.
     * [Optional]
     */
    private UpdateCustomerRequest customer;

    @Builder
    private UpdateInstrumentCardRequest(final Integer expiryMonth,
                                        final Integer expiryYear,
                                        final String name,
                                        final AccountHolder accountHolder,
                                        final UpdateCustomerRequest customer) {
        super(InstrumentType.CARD);
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.name = name;
        this.accountHolder = accountHolder;
        this.customer = customer;
    }

    public UpdateInstrumentCardRequest(final InstrumentType type) {
        super(InstrumentType.CARD);
    }

}
