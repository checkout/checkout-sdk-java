package com.checkout.identities.addressdocumentverification.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Enumeration of address document verification statuses
 */
public enum AddressDocumentVerificationStatus {

    /**
     * Address document verification created
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
     * Address document verification approved
     */
    @SerializedName("approved")
    APPROVED,

    /**
     * Address document verification declined
     */
    @SerializedName("declined")
    DECLINED,

    /**
     * Retry required
     */
    @SerializedName("retry_required")
    RETRY_REQUIRED,

    /**
     * Address document verification inconclusive
     */
    @SerializedName("inconclusive")
    INCONCLUSIVE
}
