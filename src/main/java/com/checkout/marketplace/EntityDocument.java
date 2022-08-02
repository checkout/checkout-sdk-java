package com.checkout.marketplace;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class EntityDocument {

    private String type;

    @SerializedName("file_id")
    private String fileId;

}
