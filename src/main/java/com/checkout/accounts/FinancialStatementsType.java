package com.checkout.accounts;

import com.google.gson.annotations.SerializedName;

/**
 * The document type accepted as financial statements (US ISV Seller variants). Note the plural
 * {@code financial_statements}; {@link FinancialVerificationType} is a different enum.
 */
public enum FinancialStatementsType {

    @SerializedName("financial_statements")
    FINANCIAL_STATEMENTS
}
