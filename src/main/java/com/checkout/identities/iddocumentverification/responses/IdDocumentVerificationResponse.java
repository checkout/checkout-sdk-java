package com.checkout.identities.iddocumentverification.responses;

import com.checkout.identities.entities.BaseIdentityResponseStatus;
import com.checkout.identities.entities.DeclaredData;
import com.checkout.identities.entities.DocumentDetails;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Response for ID document verification operations
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class IdDocumentVerificationResponse extends BaseIdentityResponseStatus<IdDocumentVerificationStatus> {

    private String userJourneyId;

    private String applicantId;

    private DeclaredData declaredData;

    private DocumentDetails document;
}