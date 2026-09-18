package com.checkout.identities.addressdocumentverification.responses;

import com.checkout.identities.entities.AttemptAssetLinks;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * An asset (the document image) uploaded for an address document verification attempt.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AddressDocumentVerificationAttemptAsset {

    /**
     * The type of asset.
     * [Required]
     */
    private AddressDocumentVerificationAttemptAssetType type;

    /**
     * The links related to the asset.
     * [Required]
     */
    @SerializedName("_links")
    private AttemptAssetLinks links;

}
