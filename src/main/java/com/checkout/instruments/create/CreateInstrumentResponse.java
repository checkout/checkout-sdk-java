package com.checkout.instruments.create;

import com.checkout.HttpMetadata;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The shared properties of a stored payment instrument.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class CreateInstrumentResponse extends HttpMetadata {

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

}
