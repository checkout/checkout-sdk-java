package com.checkout.apm.ideal;

import lombok.Data;

import java.util.List;

@Data
public final class IdealCountry {

    private String name;

    private List<Issuer> issuers;

}
