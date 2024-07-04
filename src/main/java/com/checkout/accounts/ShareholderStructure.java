package com.checkout.accounts;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ShareholderStructure {

    private ShareholderStructureType type;

    private String front;

}
