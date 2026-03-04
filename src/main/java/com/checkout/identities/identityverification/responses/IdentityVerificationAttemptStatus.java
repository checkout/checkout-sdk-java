package com.checkout.identities.identityverification.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Enumeration of identity verification attempt statuses
 */
public enum IdentityVerificationAttemptStatus {

    @SerializedName("capture_aborted")
    CAPTURE_ABORTED,

    @SerializedName("capture_in_progress")
    CAPTURE_IN_PROGRESS,

    @SerializedName("checks_inconclusive")
    CHECKS_INCONCLUSIVE,

    @SerializedName("checks_in_progress")
    CHECKS_IN_PROGRESS,

    @SerializedName("completed")
    COMPLETED,

    @SerializedName("expired")
    EXPIRED,

    @SerializedName("pending_redirection")
    PENDING_REDIRECTION,

    @SerializedName("capture_refused")
    CAPTURE_REFUSED
}