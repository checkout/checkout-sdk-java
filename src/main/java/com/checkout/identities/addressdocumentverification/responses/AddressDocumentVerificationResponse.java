package com.checkout.identities.addressdocumentverification.responses;

import com.checkout.identities.entities.BaseIdentityResponseStatus;
import com.checkout.identities.entities.DeclaredData;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Response for address document verification operations
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class AddressDocumentVerificationResponse extends BaseIdentityResponseStatus<AddressDocumentVerificationStatus> {

    private String userJourneyId;

    private String applicantId;

    private DeclaredData declaredData;

    private AddressDocumentResult addressDocument;
}
