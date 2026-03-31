package com.checkout.networktokens.entities;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public final class IdNetworkTokenSource extends AbstractNetworkTokenSource {

    private String id;

    @Builder
    public IdNetworkTokenSource(final String id) {
        super(NetworkTokenSourceType.ID);
        this.id = id;
    }

}