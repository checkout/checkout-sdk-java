package com.checkout.identities.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Query parameters for retrieving the attempts made for a verification.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class AttemptsQueryFilter {

    /**
     * The number of attempts to skip.
     * [Optional]
     * Default: 0
     */
    private Integer skip;

    /**
     * The maximum number of attempts to return.
     * [Optional]
     * Default: 10
     */
    private Integer limit;

}
