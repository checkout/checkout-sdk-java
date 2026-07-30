package com.checkout.identities.addressdocumentverification.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Address document verification attempt request
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AddressDocumentVerificationAttemptRequest {

    /**
     * The address document image to upload.
     * [Required]
     * Format: binary
     */
    private String document;
}
