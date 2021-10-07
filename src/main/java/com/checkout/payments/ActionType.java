package com.checkout.payments;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ActionType {

    public static final String AUTHORIZATION = "Authorization";
    public static final String CARD_VERIFICATION = "Card Verification";
    public static final String VOID = "Void";
    public static final String CAPTURE = "Capture";
    public static final String REFUND = "Refund";

}
