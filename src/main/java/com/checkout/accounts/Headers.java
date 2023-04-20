package com.checkout.accounts;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Headers {

    @SerializedName("if-match")
    private String ifMatch;
}
