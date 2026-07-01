package com.checkout.accounts;

import com.checkout.common.Currency;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class EntityFinancialDetails {

    private Long annualProcessingVolume;

    private Long averageTransactionValue;

    private Long highestTransactionValue;

    private EntityFinancialDocuments documents;

    private Currency currency;

}
