package com.checkout.identities.addressdocumentverification.responses;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Response for address document verification attempts listing operations
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class AddressDocumentVerificationAttemptsResponse extends Resource {

    private Integer totalCount;

    private Integer skip;

    private Integer limit;

    private List<AddressDocumentVerificationAttemptResponse> data;
}
