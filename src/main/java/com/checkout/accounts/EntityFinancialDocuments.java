package com.checkout.accounts;

import lombok.Builder;
import lombok.Data;

/**
 * Not defined by any Accounts API schema: {@code financial_details} carries the three amounts and
 * the currency only. Retained so existing code keeps compiling.
 *
 * @deprecated Not part of any Accounts API schema. Will be removed in a future major version.
 */
@Data
@Builder
@Deprecated
public final class EntityFinancialDocuments {

    /**
     * Not defined by any Accounts API schema.
     */
    private EntityDocument bankStatement;

    /**
     * Not defined by any Accounts API schema.
     */
    private EntityDocument financialStatement;
}
