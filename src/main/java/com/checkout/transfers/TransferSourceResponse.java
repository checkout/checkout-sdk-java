package com.checkout.transfers;

import com.checkout.common.Currency;
import lombok.Data;

@Data
public final class TransferSourceResponse {

    private String entityId;

    private Long amount;

    private Currency currency;
}
