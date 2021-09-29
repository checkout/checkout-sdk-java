package com.checkout.sessions;

import com.checkout.sessions.channel.ThreeDsMethodCompletion;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public final class ThreeDsMethodCompletionRequest {

    @SerializedName("three_ds_method_completion")
    private ThreeDsMethodCompletion threeDsMethodCompletion;

}
