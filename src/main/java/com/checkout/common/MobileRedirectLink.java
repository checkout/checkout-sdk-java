package com.checkout.common;

import lombok.Data;

/**
 * Mobile deep-link redirect URLs returned in 202 payment response _links.redirect.
 */
@Data
public final class MobileRedirectLink {

    /**
     * The Android deep-link redirect URL.
     * [Optional]
     */
    private PlatformLink android;

    /**
     * The iOS deep-link redirect URL.
     * [Optional]
     */
    private PlatformLink ios;

    @Data
    public static final class PlatformLink {

        /**
         * The deep-link URL.
         * [Optional]
         */
        private String href;
    }

}
