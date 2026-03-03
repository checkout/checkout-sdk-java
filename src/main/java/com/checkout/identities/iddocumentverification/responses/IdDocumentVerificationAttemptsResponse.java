package com.checkout.identities.iddocumentverification.responses;

import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Response for ID document verification attempts listing operations
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IdDocumentVerificationAttemptsResponse extends Resource {

    @SerializedName("total_count")
    private Integer totalCount;

    @SerializedName("skip")
    private Integer skip;

    @SerializedName("limit")
    private Integer limit;

    @SerializedName("data")
    private List<IdDocumentVerificationAttemptResponse> data;
}