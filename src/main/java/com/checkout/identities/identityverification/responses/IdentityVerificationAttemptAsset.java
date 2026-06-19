package com.checkout.identities.identityverification.responses;

import com.checkout.identities.entities.AttemptAssetLinks;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * An asset (face image, video, or document image) captured during an identity verification attempt.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class IdentityVerificationAttemptAsset {

    /**
     * The type of asset.
     * [Required]
     */
    private IdentityVerificationAttemptAssetType type;

    /**
     * The links related to the asset.
     * [Required]
     */
    @SerializedName("_links")
    private AttemptAssetLinks links;

}
