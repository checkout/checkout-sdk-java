package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class Optimization {

    private Boolean optimized;

    private String framework;

    @SerializedName("optimized_properties")
    private List<OptimizedProperties> optimizedProperties;

}
