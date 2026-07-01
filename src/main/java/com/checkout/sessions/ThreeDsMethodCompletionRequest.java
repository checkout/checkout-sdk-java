package com.checkout.sessions;

import com.checkout.sessions.channel.ThreeDsMethodCompletion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public final class ThreeDsMethodCompletionRequest {

    private ThreeDsMethodCompletion threeDsMethodCompletion;

}
