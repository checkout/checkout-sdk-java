package com.checkout.issuing.controls.requests.query;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class CardControlsQuery {

    private String targetId;
}
