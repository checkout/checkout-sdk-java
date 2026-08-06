package com.checkout.sessions;

import lombok.Data;

/**
 * Details of the challenge iframe displayed in the cardholder browser window.
 * <p>
 * Used by {@link GoogleSpaInfo#getIframe()}.
 */
@Data
public final class GoogleSpaIframe {

    /**
     * Height of the challenge iframe displayed in the cardholder browser window.
     * [Optional]
     */
    private String height;

    /**
     * Width of the challenge iframe displayed in the cardholder browser window.
     * [Optional]
     */
    private String width;

}
