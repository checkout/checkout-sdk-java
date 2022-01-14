package com.checkout.common;

import com.checkout.CheckoutArgumentException;
import org.apache.commons.lang3.StringUtils;

public final class CheckoutUtils {

    public static String TYPE = "type";

    private CheckoutUtils() {
    }

    public static String getVersionFromManifest() {
        return CheckoutUtils.class.getPackage().getImplementationVersion();
    }

    public static boolean isSuccessHttpStatusCode(final int httpStatusCode) {
        return httpStatusCode >= 200 && httpStatusCode <= 299;
    }

    public static void validateParams(final String p1, final Object o1) {
        validateMultipleRequires(new Object[][]{{p1, o1}});
    }

    public static void validateParams(final String p1, final Object o1,
                                      final String p2, final Object o2) {
        validateMultipleRequires(new Object[][]{{p1, o1}, {p2, o2}});
    }

    public static void validateParams(final String p1, final Object o1,
                                      final String p2, final Object o2,
                                      final String p3, final Object o3) {
        validateMultipleRequires(new Object[][]{{p1, o1}, {p2, o2}, {p3, o3}});
    }

    public static void validateParams(final String p1, final Object o1,
                                      final String p2, final Object o2,
                                      final String p3, final Object o3,
                                      final String p4, final Object o4) {
        validateMultipleRequires(new Object[][]{{p1, o1}, {p2, o2}, {p3, o3}, {p4, o4}});
    }

    public static void validateParams(final String p1, final Object o1,
                                      final String p2, final Object o2,
                                      final String p3, final Object o3,
                                      final String p4, final Object o4,
                                      final String p5, final Object o5) {
        validateMultipleRequires(new Object[][]{{p1, o1}, {p2, o2}, {p3, o3}, {p4, o4}, {p5, o5}});
    }

    private static void validateMultipleRequires(final Object[][] params) {
        if (params.length == 0) {
            return;
        }
        for (final Object[] param : params) {
            final Object property = param[0];
            if (!(property instanceof String) || StringUtils.isBlank((CharSequence) property)) {
                throw new IllegalStateException("invalid validation key");
            }
            final Object value = param[1];
            if (value instanceof String) {
                requiresNonBlank((String) property, (String) value);
                continue;
            }
            requiresNonNull((String) property, value);
        }
    }

    private static void requiresNonBlank(final String property, final String content) {
        if (StringUtils.isBlank(content)) {
            throw new CheckoutArgumentException(property + " cannot be blank");
        }
    }

    private static void requiresNonNull(final String property, final Object obj) {
        if (obj == null) {
            throw new CheckoutArgumentException(property + " cannot be null");
        }
    }

}