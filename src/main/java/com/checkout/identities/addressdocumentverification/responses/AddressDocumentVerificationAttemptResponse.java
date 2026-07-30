package com.checkout.identities.addressdocumentverification.responses;

import com.checkout.identities.entities.BaseIdentityResponseStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Response for address document verification attempt operations
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class AddressDocumentVerificationAttemptResponse extends BaseIdentityResponseStatus<AddressDocumentVerificationAttemptStatus> {
    // id, created_on, modified_on, status and response_codes are inherited
}
