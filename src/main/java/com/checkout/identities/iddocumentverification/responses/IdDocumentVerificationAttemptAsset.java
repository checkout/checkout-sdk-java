package com.checkout.identities.iddocumentverification.responses;

import com.checkout.identities.entities.AttemptAssetLinks;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * An asset (the front or back image of the document) uploaded for an ID document verification
 * attempt.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class IdDocumentVerificationAttemptAsset {

    /**
     * The type of asset.
     * [Required]
     */
    private IdDocumentVerificationAttemptAssetType type;

    /**
     * The links related to the asset.
     * [Required]
     */
    @SerializedName("_links")
    private AttemptAssetLinks links;

}
