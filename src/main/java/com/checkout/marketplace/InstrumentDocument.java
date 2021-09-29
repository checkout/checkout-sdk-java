package com.checkout.marketplace;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class InstrumentDocument {

    private String type;

    @SerializedName("file_id")
    private String fileId;

}
