package com.checkout.identities.faceauthentications.responses;

import com.checkout.identities.entities.AttemptAssetLinks;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * An asset (face image or video) captured during a face authentication attempt.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class FaceAuthenticationAttemptAsset {

    /**
     * The type of asset.
     * [Required]
     */
    private FaceAuthenticationAttemptAssetType type;

    /**
     * The links related to the asset.
     * [Required]
     */
    @SerializedName("_links")
    private AttemptAssetLinks links;

}
