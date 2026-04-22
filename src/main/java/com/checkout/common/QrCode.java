package com.checkout.common;

import lombok.Data;

/**
 * QR code data returned for payment methods that require a QR code scan, in 202 responses.
 */
@Data
public final class QrCode {

    /**
     * The QR code as a base64-encoded image.
     * [Optional]
     */
    private String image;

    /**
     * The raw text encoded in the QR code.
     * [Optional]
     */
    private String text;

}
