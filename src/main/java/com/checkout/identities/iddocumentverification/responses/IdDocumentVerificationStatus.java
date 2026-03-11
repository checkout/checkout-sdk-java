package com.checkout.identities.iddocumentverification.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Enumeration of ID document verification statuses
 */
public enum IdDocumentVerificationStatus {

    /**
     * ID document verification created
     */
    @SerializedName("created")
    CREATED,

    /**
     * Quality checks in progress
     */
    @SerializedName("quality_checks_in_progress")
    QUALITY_CHECKS_IN_PROGRESS,

    /**
     * Checks in progress
     */
    @SerializedName("checks_in_progress")
    CHECKS_IN_PROGRESS,

    /**
     * ID document verification approved
     */
    @SerializedName("approved")
    APPROVED,

    /**
     * ID document verification declined
     */
    @SerializedName("declined")
    DECLINED,

    /**
     * Retry required
     */
    @SerializedName("retry_required")
    RETRY_REQUIRED,

    /**
     * ID document verification inconclusive
     */
    @SerializedName("inconclusive")
    INCONCLUSIVE
}