package com.checkout.sessions;

import lombok.Data;

/**
 * Details of Google SPA (Secure Payment Authentication).
 * <p>
 * Returned as {@link GetSessionResponse#getGoogleSpa()}. The spec models this as a choice between a
 * hosted variant, which returns only {@link #token}, and a non-hosted variant, which also returns the
 * challenge details. Both are modelled here with every property optional, so callers read whichever
 * the API populated.
 */
@Data
public final class GoogleSpaInfo {

    /**
     * The URL used to present the Google SPA challenge.
     * [Optional]
     */
    private String challengeUrl;

    /**
     * The initial timeout, in seconds.
     * [Optional]
     */
    private String initialTimeout;

    /**
     * The maximum timeout, in seconds.
     * [Optional]
     */
    private String maxTimeout;

    /**
     * Details of the challenge iframe displayed in the cardholder browser window.
     * [Optional]
     */
    private GoogleSpaIframe iframe;

    /**
     * Token for the given PAN provisioned and authenticated.
     * [Optional]
     */
    private GoogleSpaToken token;

}
