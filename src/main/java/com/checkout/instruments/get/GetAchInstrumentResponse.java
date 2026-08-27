package com.checkout.instruments.get;

import com.checkout.common.InstrumentType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

/**
 * ACH instrument response.
 *
 * <p>The id and the fingerprint are inherited from {@link GetInstrumentResponse}.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class GetAchInstrumentResponse extends GetInstrumentResponse {

    /**
     * The underlying instrument type.
     * [Required]
     */
    private final InstrumentType type = InstrumentType.ACH;

    /**
     * The date and time the instrument was created.
     * [Required]
     * Format: date-time (RFC 3339)
     */
    private Instant createdOn;

    /**
     * The date and time the instrument was last modified.
     * [Required]
     * Format: date-time (RFC 3339)
     */
    private Instant modifiedOn;

    /**
     * The Vault ID currently attached to the instrument.
     * [Required]
     */
    private String vaultId;

    /**
     * The details of the bank account.
     * [Required]
     */
    private GetAchInstrumentData instrumentData;

    /**
     * The account holder details.
     * [Required]
     */
    private GetAchAccountHolder accountHolder;

}
