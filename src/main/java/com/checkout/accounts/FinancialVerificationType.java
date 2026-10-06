package com.checkout.accounts;

import com.google.gson.annotations.SerializedName;

/**
 * The document type accepted as financial verification. Note the singular
 * {@code financial_statement}; {@link FinancialStatementsType} is a different enum.
 */
public enum FinancialVerificationType {

    @SerializedName("financial_statement")
    FINANCIAL_STATEMENT

}
