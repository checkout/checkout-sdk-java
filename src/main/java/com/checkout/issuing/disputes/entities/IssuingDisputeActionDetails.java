package com.checkout.issuing.disputes.entities;

import lombok.Data;

/**
 * Provides instructions on the amendments required before the dispute can proceed, if the dispute
 * status is action_required. Use the Amend an Issuing dispute endpoint to submit your amendments.
 */
@Data
public final class IssuingDisputeActionDetails {

    /**
     * The amendments required before the dispute can proceed. For example, if you need to provide a
     * reason code, or update the submitted evidence.
     * <p>
     * [Optional]
     * </p>
     */
    private String instructions;

    /**
     * Specifies whether the dispute was previously amended and an action response was provided.
     * <p>
     * [Optional]
     * </p>
     */
    private String lastActionResponse;

}
