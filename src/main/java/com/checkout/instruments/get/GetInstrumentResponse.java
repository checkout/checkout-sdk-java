package com.checkout.instruments.get;

import com.checkout.HttpMetadata;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The shared properties of a retrieved payment instrument.
 *
 * <p>The account holder is declared by each concrete variant rather than here, because the
 * specification gives every variant a different account holder shape: the bank account variant
 * refers to the shared AccountHolder, while the card, SEPA and Bacs Direct Debit variants each
 * declare their own inline shape.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class GetInstrumentResponse extends HttpMetadata {

    /**
     * The unique identifier of the payment source or destination that can be used later for
     * payments.
     * [Required]
     */
    protected String id;

    /**
     * A token that can uniquely identify this instrument across all customers.
     * [Required]
     * ^([a-z0-9]{26})$
     */
    protected String fingerprint;

    /**
     * The customer that the instrument is associated with.
     * [Optional]
     */
    protected InstrumentCustomerResponse customer;

}
