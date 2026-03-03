package com.checkout.identities.identityverification.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Enumeration of identity verification attempt statuses
 */
public enum IdentityVerificationAttemptStatus {

    /**
     * Attempt approved
     */
    @SerializedName("approved")
    APPROVED,

    /**
     * Attempt declined
     */
    @SerializedName("declined")
    DECLINED,

    /**
     * Capture failed
     */
    @SerializedName("capture_failed")
    CAPTURE_FAILED,

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
     * Attempt inconclusive
     */
    @SerializedName("inconclusive")
    INCONCLUSIVE,

    /**
     * Attempt pending
     */
    @SerializedName("pending")
    PENDING
}