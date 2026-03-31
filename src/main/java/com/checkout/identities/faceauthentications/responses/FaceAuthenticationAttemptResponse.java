package com.checkout.identities.faceauthentications.responses;

import com.checkout.identities.entities.BaseAttemptResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Face authentication attempt response
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
public final class FaceAuthenticationAttemptResponse extends BaseAttemptResponse<FaceAuthenticationAttemptStatus> {

    // This class inherits all necessary fields from BaseAttemptResponse
    // No additional fields are currently needed for face authentication attempts

}