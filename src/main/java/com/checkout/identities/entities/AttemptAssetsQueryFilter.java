package com.checkout.identities.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Query parameters for retrieving the assets captured during an attempt.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class AttemptAssetsQueryFilter {

    /**
     * The number of assets to skip.
     * [Optional]
     * Default: 0
     */
    private Integer skip;

    /**
     * The maximum number of assets to return.
     * [Optional]
     * Default: 10
     */
    private Integer limit;

}
