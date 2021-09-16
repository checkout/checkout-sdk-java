package com.checkout.apm.ideal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class Issuer {

    private String bic;

    private String name;

}
