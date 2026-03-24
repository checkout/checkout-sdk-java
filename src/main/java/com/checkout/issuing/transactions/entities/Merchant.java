package com.checkout.issuing.transactions.entities;

import com.checkout.common.CountryCode;
import lombok.Data;

@Data
public final class Merchant {

    private String id;

    private String name;

    private String city;

    private String state;

    private CountryCode countryCode;

    private String categoryCode;
}