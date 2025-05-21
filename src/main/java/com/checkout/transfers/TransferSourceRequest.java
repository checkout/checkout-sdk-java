package com.checkout.transfers;

import com.checkout.common.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class TransferSourceRequest {

    private String id;

    private Long amount;

    private Currency currency;

}
