package com.checkout.identities.applicants.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Enumeration of applicant statuses
 */
public enum ApplicantStatus {

    /**
     * Applicant has been created
     */
    @SerializedName("created")
    CREATED,

    /**
     * Applicant has been updated
     */
    @SerializedName("updated") 
    UPDATED,

    /**
     * Applicant has been anonymized
     */
    @SerializedName("anonymized")
    ANONYMIZED
}