package com.checkout.disputes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class HistoricalTransactions {

    private String historicalArn;

    private String merchandiseOrServiceDesc;

}
