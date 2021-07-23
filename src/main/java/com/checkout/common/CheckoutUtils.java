package com.checkout.common;

import com.checkout.CheckoutArgumentException;
import org.apache.commons.lang3.StringUtils;

public class CheckoutUtils {

    /**
     * @deprecated Will be removed in a future version
     */
    @Deprecated
    public static boolean isNullOrEmpty(final String string) {
        return string == null || string.isEmpty();
    }

    /**
     * @deprecated Will be removed in a future version
     */
    @Deprecated
    public static boolean isNullOrWhitespace(final String string) {
        return string == null || string.trim().isEmpty();
    }

    public static String getVersionFromManifest() {
        return CheckoutUtils.class.getPackage().getImplementationVersion();
    }

    public static boolean isSuccessHttpStatusCode(final int httpStatusCode) {
        return httpStatusCode >= 200 && httpStatusCode <= 299;
    }

    public static void requiresNonBlank(final String property, final String content) {
        if (StringUtils.isBlank(content)) {
            throw new CheckoutArgumentException(property + " must be not be blank");
        }
    }

    public static void requiresNonNull(final String property, final Object obj) {
        if (obj == null) {
            throw new CheckoutArgumentException(property + " must be not be null");
        }
    }

}