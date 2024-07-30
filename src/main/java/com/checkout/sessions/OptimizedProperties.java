package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class OptimizedProperties {

    private String field;

    @SerializedName("original_value")
    private String originalValue;

    @SerializedName("optimized_value")
    private String optimizedValue;

}
