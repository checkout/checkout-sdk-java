package com.checkout.instruments;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateInstrumentRequest {

    private String type;

    private String token;

    @SerializedName("account_holder")
    private AccountHolder accountHolder;

}
