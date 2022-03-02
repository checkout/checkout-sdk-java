package com.checkout.marketplace;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class TransferSource {

    private String id;

    private Long amount;

}
