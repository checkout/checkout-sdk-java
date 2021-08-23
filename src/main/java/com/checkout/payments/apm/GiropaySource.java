package com.checkout.payments.apm;

import com.checkout.payments.RequestSource;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class GiropaySource implements RequestSource {

    private final String type = "giropay";

    private final String bic;

    private final String purpose;

    @Override
    public String getType() {
        return type;
    }

}