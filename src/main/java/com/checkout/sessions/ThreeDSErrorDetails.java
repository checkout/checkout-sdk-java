package com.checkout.sessions;

import lombok.Data;

/**
 * Provides additional information about the error returned.
 * <p>
 * Used by {@link ThreeDSInfo#getErrorDetails()}.
 */
@Data
public final class ThreeDSErrorDetails {

    /**
     * An error code identifying the type of issue.
     * [Optional]
     */
    private String errorCode;

    /**
     * A code that specifies which 3D Secure component identified the error.
     * [Optional]
     */
    private String errorComponent;

    /**
     * Provides additional details about the issue.
     * [Optional]
     * max 2048 characters
     */
    private String errorDetail;

    /**
     * A description of the issue identified.
     * [Optional]
     * max 2048 characters
     */
    private String errorDescription;

}
