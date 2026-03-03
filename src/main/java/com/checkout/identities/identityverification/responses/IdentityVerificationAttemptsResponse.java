package com.checkout.identities.identityverification.responses;

import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Response for identity verification attempts listing operations
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IdentityVerificationAttemptsResponse extends Resource {

    @SerializedName("data")
    private List<IdentityVerificationAttemptResponse> data;
}