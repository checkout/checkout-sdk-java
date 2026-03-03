package com.checkout.identities.iddocumentverification.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Enumeration of ID document verification attempt statuses
 */
public enum IdDocumentVerificationAttemptStatus {

    /**
     * Checks in progress
     */
    @SerializedName("checks_in_progress")
    CHECKS_IN_PROGRESS,

    /**
     * Checks inconclusive
     */
    @SerializedName("checks_inconclusive")
    CHECKS_INCONCLUSIVE,

    /**
     * Attempt completed
     */
    @SerializedName("completed")
    COMPLETED,

    /**
     * Quality checks aborted
     */
    @SerializedName("quality_checks_aborted")
    QUALITY_CHECKS_ABORTED,

    /**
     * Quality checks in progress
     */
    @SerializedName("quality_checks_in_progress")
    QUALITY_CHECKS_IN_PROGRESS,

    /**
     * Attempt terminated
     */
    @SerializedName("terminated")
    TERMINATED
}