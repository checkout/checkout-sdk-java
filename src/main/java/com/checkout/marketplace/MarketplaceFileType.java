package com.checkout.marketplace;

import lombok.Getter;

public enum MarketplaceFileType {

    PASSPORT("passport"),
    NATIONAL_IDENTITY_CARD("national_identity_card"),
    DRIVING_LICENCE("driving_licence"),
    CITIZEN_CARD("citizen_card"),
    RESIDENCE_PERMIT("residence_permit"),
    ELECTORAL_ID("electoral_id"),
    BANK_STATEMENT("bank_statement");

    @Getter
    private final String type;

    MarketplaceFileType(final String type) {
        this.type = type;
    }

}
