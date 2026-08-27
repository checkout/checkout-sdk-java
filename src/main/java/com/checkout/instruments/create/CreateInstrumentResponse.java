package com.checkout.instruments.create;

import com.checkout.HttpMetadata;
import com.checkout.common.CustomerResponse;
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

    /**
     * The customer that the instrument is associated with.
     *
     * <p>Only the token and bank_account variants declare this property. The sepa, ach and bacs
     * store responses do not, so on those three it is always null and does not belong here.
     * [Optional]
     *
     * @deprecated declared on the wrong level. This will move to
     * {@link CreateInstrumentTokenResponse} and {@link CreateInstrumentBankAccountResponse}, the
     * only two variants the specification declares it on, in the next major version. Read it from
     * the concrete response type rather than from this base class.
     */
    @Deprecated
    protected CustomerResponse customer;

}
