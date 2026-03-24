package com.checkout.identities.faceauthentications.responses;

import com.checkout.common.Resource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * Face authentication attempts list response
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class FaceAuthenticationAttemptsResponse extends Resource {

    /**
     * The total number of attempts.
     */
    private Integer totalCount;

    /**
     * The number of attempts skipped.
     */
    private Integer skip;

    /**
     * The maximum number of attempts returned.
     */
    private Integer limit;

    /**
     * The list of attempts.
     */
    private List<FaceAuthenticationAttemptResponse> data;
}