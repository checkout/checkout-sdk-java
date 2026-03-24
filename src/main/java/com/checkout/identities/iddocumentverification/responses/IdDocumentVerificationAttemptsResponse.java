package com.checkout.identities.iddocumentverification.responses;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Response for ID document verification attempts listing operations
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class IdDocumentVerificationAttemptsResponse extends Resource {

    private Integer totalCount;

    private Integer skip;

    private Integer limit;

    private List<IdDocumentVerificationAttemptResponse> data;
}