package com.checkout.issuing.controls.requests.query;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardControlsQuery {

    @SerializedName("target_id")
    private String targetId;
}
