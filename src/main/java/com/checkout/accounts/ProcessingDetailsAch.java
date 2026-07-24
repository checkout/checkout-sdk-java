package com.checkout.accounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ProcessingDetailsAch {

    private Integer annualAchVolume;

    private Integer averageAchTransactionSize;

    private Integer estimatedMonthlyCreditVolume;

    private Integer averageCreditAmount;

}
