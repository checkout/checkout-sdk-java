package com.checkout.sessions;

import lombok.Data;

@Data
public final class CardInfo {

    private String instrumentId;

    private String fingerprint;

    private SessionsCardMetadataResponse metadata;

}
