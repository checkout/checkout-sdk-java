package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;

/**
 * The status of a preferred experience on a session response.
 * <p>
 * Used by {@link ExperienceOutcome#getStatus()}.
 * <p>
 * [Optional]
 */
public enum ExperienceStatus {

    /**
     * The experience is available for this session.
     */
    @SerializedName("available")
    AVAILABLE,

    /**
     * The experience has not been processed.
     */
    @SerializedName("unprocessed")
    UNPROCESSED,

    /**
     * The experience has been processed.
     */
    @SerializedName("processed")
    PROCESSED,

    /**
     * The experience is not available for this session.
     */
    @SerializedName("unavailable")
    UNAVAILABLE

}
