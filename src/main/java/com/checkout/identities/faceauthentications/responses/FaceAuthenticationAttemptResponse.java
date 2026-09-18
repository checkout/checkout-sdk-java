package com.checkout.identities.faceauthentications.responses;

import com.checkout.identities.entities.BaseAttemptResponse;
import com.checkout.identities.entities.ClientInformation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Face authentication attempt response
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class FaceAuthenticationAttemptResponse extends BaseAttemptResponse<FaceAuthenticationAttemptStatus> {

    /**
     * The applicant's details.
     * [Optional]
     */
    private ClientInformation clientInformation;
}
