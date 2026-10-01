package com.checkout.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Not defined by any Accounts API onboarding schema. Referenced only by {@link Company} {@code document}
 * and {@link EntityFinancialDocuments}, both deprecated; retained so existing code keeps compiling.
 *
 * @deprecated Not part of any Accounts API onboarding schema. Will be removed in a future major
 * version.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Deprecated
public final class EntityDocument {

    /**
     * Not defined by any Accounts API onboarding schema.
     */
    private String type;

    /**
     * Not defined by any Accounts API onboarding schema.
     */
    private String fileId;

}
