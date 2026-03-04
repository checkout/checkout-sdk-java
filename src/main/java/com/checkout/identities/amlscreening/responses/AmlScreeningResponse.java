package com.checkout.identities.amlscreening.responses;

import com.checkout.identities.entities.BaseIdentityResponseStatus;
import com.checkout.identities.entities.SearchParameters;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Response for AML screening operations
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AmlScreeningResponse extends BaseIdentityResponseStatus<AmlScreeningStatus> {
    private String applicantId;

    private SearchParameters searchParameters;

    private Boolean monitored;
}