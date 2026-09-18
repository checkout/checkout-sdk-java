package com.checkout.identities.faceauthentications.requests;

import com.checkout.identities.entities.ClientInformation;
import com.checkout.identities.entities.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Face authentication attempt creation request
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class FaceAuthenticationAttemptRequest {

    /**
     * The URL to redirect the applicant to after the attempt.
     * [Required]
     */
    private String redirectUrl;

    /**
     * The applicant's mobile phone number, if sharing the attempt URL via SMS.
     * [Optional]
     */
    private PhoneNumber phoneNumber;

    /**
     * The applicant's details.
     * [Optional]
     */
    private ClientInformation clientInformation;
}