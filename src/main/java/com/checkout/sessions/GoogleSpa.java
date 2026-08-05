package com.checkout.sessions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This object contains the Google SPA properties (non-hosted only).
 * <p>
 * Used by {@link SessionRequest#getGoogleSpa()}.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class GoogleSpa {

    /**
     * Fully qualified URL for redirecting the user's browser session after authentication. For
     * example, this field may be the merchant's website for purchase confirmation once payment is
     * complete. Required if in full redirect (not iframe) mode.
     * [Optional]
     */
    private String continueUrl;

}
