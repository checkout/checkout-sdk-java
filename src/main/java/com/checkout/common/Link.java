package com.checkout.common;

import lombok.Data;

@Data
public final class Link {

    /**
     * The link URL.
     * [Optional]
     */
    private final String href;

    /**
     * The link title.
     * [Optional]
     */
    private final String title;

    /**
     * The link relation type.
     * [Optional]
     */
    private final String link;

    /**
     * Mobile deep-link redirect URLs for Android and iOS redirect payments.
     * [Optional]
     */
    private MobileRedirectLink mobile;

    /**
     * QR code data for redirect payments.
     * [Optional]
     */
    private QrCode qrCode;

}
