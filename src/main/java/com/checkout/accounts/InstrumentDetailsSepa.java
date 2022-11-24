package com.checkout.accounts;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InstrumentDetailsSepa implements InstrumentDetails{

    private String iban;

    @SerializedName("swift_bic")
    private String swiftBic;

}
