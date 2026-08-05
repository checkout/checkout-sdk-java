package com.checkout.sessions;

import lombok.Data;

/**
 * This object provides more information about the 3DS experience.
 * <p>
 * Returned as the {@code 3ds} property of {@link GetSessionResponse}.
 */
@Data
public final class ThreeDSInfo {

    /**
     * The CReq message, encoded in Base 64.
     * [Optional]
     */
    private String challengeRequest;

    /**
     * The number of authentication attempts performed by the cardholder.
     * [Optional]
     * max 2 characters
     */
    private String interactionCounter;

    /**
     * Provides additional information about the error returned.
     * [Optional]
     */
    private ThreeDSErrorDetails errorDetails;

}
