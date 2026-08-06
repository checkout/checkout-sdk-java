package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;

/**
 * Indicates the scheme this authentication is carried out against.
 * <p>
 * Used by {@link GetSessionResponse#getScheme()},
 * {@link CreateSessionAcceptedResponse#getScheme()},
 * {@link GetSessionResponseAfterChannelDataSupplied#getScheme()},
 * {@link SchemeInfo#getName()} and
 * {@link com.checkout.sessions.source.SessionSource#getScheme()}.
 * <p>
 * [Required] on the session responses
 */
public enum SessionScheme {

    /**
     * American Express.
     */
    @SerializedName("amex")
    AMEX,

    /**
     * Cartes Bancaires.
     */
    @SerializedName("cartes_bancaires")
    CARTES_BANCAIRES,

    /**
     * Diners Club.
     */
    @SerializedName("diners")
    DINERS,

    /**
     * Discover.
     */
    @SerializedName("discover")
    DISCOVER,

    /**
     * JCB.
     */
    @SerializedName("jcb")
    JCB,

    /**
     * Mastercard.
     */
    @SerializedName("mastercard")
    MASTERCARD,

    /**
     * Unified Payments Interface (UPI).
     */
    @SerializedName("upi")
    UPI,

    /**
     * Visa.
     */
    @SerializedName("visa")
    VISA

}
