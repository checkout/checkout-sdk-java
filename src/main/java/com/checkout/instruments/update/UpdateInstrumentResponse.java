package com.checkout.instruments.update;

import com.checkout.HttpMetadata;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The response for the type of instrument updated.
 *
 * <p>The id is deliberately not declared here. The specification declares it on the sepa, ach and
 * bacs update responses only, so each of those three variants carries its own. The bank_account and
 * card update responses return a type and a fingerprint but no id, and would expose an always-null
 * property if the id lived on this base class.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class UpdateInstrumentResponse extends HttpMetadata {

    /**
     * A token that can uniquely identify this instrument across all customers.
     * [Required]
     * ^([a-z0-9]{26})$
     */
    private String fingerprint;

}
