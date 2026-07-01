package com.checkout.accounts;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class InstrumentDetailsSepa implements InstrumentDetails{

    private String iban;

    private String swiftBic;

}
