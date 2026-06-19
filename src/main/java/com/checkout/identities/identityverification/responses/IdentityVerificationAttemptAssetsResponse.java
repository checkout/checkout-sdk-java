package com.checkout.identities.identityverification.responses;

import com.checkout.common.Resource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The paginated assets captured during an identity verification attempt.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class IdentityVerificationAttemptAssetsResponse extends Resource {

    /**
     * The total number of assets.
     * [Required]
     */
    private Integer totalCount;

    /**
     * The number of assets skipped.
     * [Required]
     */
    private Integer skip;

    /**
     * The maximum number of assets returned.
     * [Required]
     */
    private Integer limit;

    /**
     * The list of assets for the current page.
     * [Required]
     */
    private List<IdentityVerificationAttemptAsset> data;
}
