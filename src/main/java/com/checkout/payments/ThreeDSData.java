package com.checkout.payments;

import com.checkout.common.Exemption;
import com.checkout.common.ThreeDSEnrollmentStatus;
import lombok.Data;

@Data
public final class ThreeDSData {

    /**
     * Whether the 3DS authentication was downgraded.
     * [Optional]
     */
    private Boolean downgraded;

    /**
     * The 3DS enrollment status of the card.
     * [Optional]
     */
    private ThreeDSEnrollmentStatus enrolled;

    /**
     * The reason for the 3DS upgrade.
     * [Optional]
     */
    private String upgradeReason;

    /**
     * Whether the 3DS signature is valid.
     * [Optional]
     */
    private String signatureValid;

    /**
     * The 3DS authentication response code.
     * [Optional]
     */
    private String authenticationResponse;

    /**
     * The 3DS cryptogram (CAVV) for the transaction.
     * [Optional]
     */
    private String cryptogram;

    /**
     * The 3DS transaction identifier (XID).
     * [Optional]
     */
    private String xid;

    /**
     * The 3DS protocol version used for authentication.
     * [Optional]
     */
    private String version;

    /**
     * The 3DS exemption type applied to the payment.
     * [Optional]
     */
    private Exemption exemption;

    /**
     * The 3DS exemption type that was applied by the issuer.
     * [Optional]
     */
    private String exemptionApplied;

    /**
     * Whether the cardholder was challenged during authentication.
     * [Optional]
     */
    private Boolean challenged;

    /**
     * The Electronic Commerce Indicator (ECI) value from the 3DS authentication.
     * [Optional]
     */
    private String eci;

    /**
     * The reason for the authentication status result.
     * [Optional]
     */
    private String authenticationStatusReason;

    /**
     * The trusted listing status for the cardholder.
     * [Optional]
     */
    private TrustedListing trustedListing;

}
