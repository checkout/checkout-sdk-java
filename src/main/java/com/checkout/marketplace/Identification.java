package com.checkout.marketplace;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Identification {

    @SerializedName("national_id_number")
    private String nationalIdNumber;

    private Document document;

}
