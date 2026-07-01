package com.checkout.accounts;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class InstrumentDetailsFasterPayments implements InstrumentDetails{

    private String accountNumber;

    private String bankCode;
}
