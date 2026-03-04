package com.checkout.identities.identityverification.responses;

import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Response for identity verification attempts listing operations
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdentityVerificationAttemptsResponse extends Resource {

    /**
     * The total number of attempts.
     */
    private Integer totalCount;

    /**
     * The number of attempts you want to skip.
     */
    private Integer skip;

    /**
     * The maximum number of attempts you want returned.
     */
    private Integer limit;

    /**
     * The details of the attempts.
     */
    private List<IdentityVerificationAttemptResponse> data;
}