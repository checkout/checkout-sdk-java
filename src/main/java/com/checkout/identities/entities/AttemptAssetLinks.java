package com.checkout.identities.entities;

import com.checkout.common.Link;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The links related to an attempt asset.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AttemptAssetLinks {

    /**
     * The URL to download the asset.
     * [Required]
     * Format: uri
     */
    private Link assetUrl;

}
