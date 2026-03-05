package com.checkout.identities.identityverification.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Enumeration of identity verification statuses
 */
public enum IdentityVerificationStatus {

    /**
     * Identity verification approved
     */
    @SerializedName("approved")
    APPROVED,

    /**
     * Capture in progress
     */
    @SerializedName("capture_in_progress")
    CAPTURE_IN_PROGRESS,

    /**
     * Checks in progress
     */
    @SerializedName("checks_in_progress")
    CHECKS_IN_PROGRESS,

    /**
     * Identity verification declined
     */
    @SerializedName("declined")
    DECLINED,

    /**
     * Identity verification inconclusive
     */
    @SerializedName("inconclusive")
    INCONCLUSIVE,

    /**
     * Identity verification pending
     */
    @SerializedName("pending")
    PENDING,

    /**
     * Identity verification refused
     */
    @SerializedName("refused")
    REFUSED,

    /**
     * Retry required
     */
    @SerializedName("retry_required")
    RETRY_REQUIRED
}