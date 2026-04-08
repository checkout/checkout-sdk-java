package com.checkout.handlepaymentsandpayouts.googlepay.requests;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class GooglePayEnrollmentRequest {

    /**
     * The unique identifier of the entity to enroll.
     * <p>
     * [Required]
     * </p>
     */
    @SerializedName("entity_id")
    private String entityId;

    /**
     * The email address of the user accepting the Google terms of service.
     * <p>
     * [Required]
     * </p>
     */
    @SerializedName("email_address")
    private String emailAddress;

    /**
     * Indicates acceptance of the Google terms of service. Must be true to proceed.
     * <p>
     * [Required]
     * </p>
     */
    @SerializedName("accept_terms_of_service")
    private Boolean acceptTermsOfService;

}
