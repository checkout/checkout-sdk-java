package com.checkout.identities.iddocumentverification.requests;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ID document verification attempt request
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdDocumentVerificationAttemptRequest {

    /**
     * The image of the front of the document to upload.
     * [Required]
     */
    @SerializedName("document_front")
    private String documentFront;

    /**
     * The image of the back of the document to upload.
     */
    @SerializedName("document_back")
    private String documentBack;
}