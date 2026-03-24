package com.checkout.handlepaymentsandpayouts.googlepay.requests;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request to enroll an entity to the Google Pay Service.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class GooglePayEnrollmentRequest {

    /**
     * The unique identifier of the entity to enroll.
     */
    @SerializedName("entityId")
    private String entityId;

    /**
     * The email address of the user accepting the Google terms of service.
     */
    @SerializedName("emailAddress")
    private String emailAddress;

    /**
     * Indicates acceptance of the Google terms of service. Must be true to proceed with enrollment.
     */
    @SerializedName("acceptTermsOfService")
    private Boolean acceptTermsOfService;

}
