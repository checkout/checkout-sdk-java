package com.checkout.instruments.get;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public final class BankAccountFieldDependency {

    @SerializedName("field_id")
    private String fieldId;

    private String value;

}
