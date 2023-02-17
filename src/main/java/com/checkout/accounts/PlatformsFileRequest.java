package com.checkout.accounts;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class PlatformsFileRequest {

    private String purpose;

    @SerializedName("entity_id")
    private String entityId;
}
