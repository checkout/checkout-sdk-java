package com.checkout.sessions;

import lombok.Data;

import java.util.List;

/**
 * The outcome of a single preferred experience.
 * <p>
 * Used by {@link SessionPreferredExperiences}.
 */
@Data
public final class ExperienceOutcome {

    /**
     * The status of the experience.
     * [Optional]
     */
    private ExperienceStatus status;

    /**
     * The reason(s) why processing the experience was unsuccessful.
     * [Optional]
     */
    private List<String> reason;

}
