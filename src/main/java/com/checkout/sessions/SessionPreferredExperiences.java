package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Reports the outcome of each experience requested for the session.
 * <p>
 * Returned as {@link GetSessionResponse#getPreferredExperiences()}. Named with the {@code Session}
 * prefix to distinguish it from {@link com.checkout.payments.PreferredExperiences}, which is an enum
 * in the payments domain.
 */
@Data
public final class SessionPreferredExperiences {

    /**
     * The outcome of the Google SPA experience.
     * [Optional]
     */
    private ExperienceOutcome googleSpa;

    /**
     * The outcome of the 3DS experience.
     * [Optional]
     */
    @SerializedName("3ds")
    private ExperienceOutcome threeDs;

}
