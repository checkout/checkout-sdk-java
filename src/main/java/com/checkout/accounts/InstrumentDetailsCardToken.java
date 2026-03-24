package com.checkout.accounts;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class InstrumentDetailsCardToken implements InstrumentDetails {

    private String token;
}
