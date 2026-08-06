package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;

/**
 * The authentication experience used for processing a session.
 * <p>
 * Used by {@link SessionRequest#getPreferredExperiences()} to indicate the chosen experience(s), and
 * returned by {@link GetSessionResponse#getExperience()} to report the one that was used.
 * <p>
 * [Optional]
 */
public enum Experience {

    /**
     * 3D Secure authentication.
     */
    @SerializedName("3ds")
    THREE_DS,

    /**
     * Google Secure Payment Authentication.
     */
    @SerializedName("google_spa")
    GOOGLE_SPA

}
