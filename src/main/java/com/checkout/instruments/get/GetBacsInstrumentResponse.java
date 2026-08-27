package com.checkout.instruments.get;

import com.checkout.common.InstrumentType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Bacs Direct Debit instrument response.
 *
 * <p>The id and the fingerprint are inherited from {@link GetInstrumentResponse}.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class GetBacsInstrumentResponse extends GetInstrumentResponse {

    /**
     * The underlying instrument type.
     * [Required]
     */
    private final InstrumentType type = InstrumentType.BACS;

    /**
     * The date and time the instrument was created.
     * [Required]
     * Format: date-time (RFC 3339)
     */
    private Instant createdOn;

    /**
     * The Vault ID currently attached to the instrument.
     * [Required]
     */
    private String vaultId;

    /**
     * The date and time the instrument was last modified.
     * [Optional]
     * Format: date-time (RFC 3339)
     */
    private Instant modifiedOn;

    /**
     * The account configuration for the instrument.
     * [Optional]
     */
    private GetBacsInstrumentAccount account;

    /**
     * The list of validations performed on the instrument. The API publishes no item schema for
     * this array, so each entry is exposed as an untyped map.
     * [Optional]
     */
    private List<Map<String, Object>> validations;

    /**
     * The details of the Bacs Direct Debit account.
     * [Optional]
     */
    private GetBacsInstrumentData instrumentData;

    /**
     * The account holder's details.
     * [Optional]
     */
    private GetBacsAccountHolder accountHolder;

}
