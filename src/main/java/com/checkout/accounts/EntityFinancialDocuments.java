package com.checkout.accounts;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EntityFinancialDocuments {

    @SerializedName("bank_statement")
    private EntityDocument bankStatement;

    @SerializedName("financial_statement")
    private EntityDocument financialStatement;
}
