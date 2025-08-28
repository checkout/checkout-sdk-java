package com.checkout.payments.request;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * device
 * Details of the device from which the payment originated.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Device {

    /**
     * The contents of the HTTP User-Agent request header.
     * This field is required to process the device with the risk engine.
     * [Optional]
     * <= 2048
     */
    @SerializedName("user_agent")
    private String userAgent;

    /**
     * Details of the device network.
     * Either ipv4 or ipv6 is required field.
     * [Optional]
     */
    private Network network;

    /**
     * Details of the device ID provider.
     * [Optional]
     */
    private Provider provider;

    /**
     * The UTC date and time the payment was performed as reported by the device.
     * Format – ISO 8601 code
     * This field is required to process the device with the risk engine.
     * [Optional]
     */
    private String timestamp;

    /**
     * The time difference between UTC time and the local time reported by the browser, in minutes.
     * This field is required to process the device with the risk engine.
     * [Optional]
     * [ 1 .. 5 ] characters
     */
    private String timezone;

    /**
     * Specifies if the device is running in a virtual machine.
     * [Optional]
     */
    @SerializedName("virtual_machine")
    private Boolean virtualMachine;

    /**
     * Specifies if the browser is in incognito mode.
     * [Optional]
     */
    private Boolean incognito;

    /**
     * Specifies if the device is jailbroken.
     * [Optional]
     */
    private Boolean jailbroken;

    /**
     * Specifies if the device is rooted.
     * [Optional]
     */
    private Boolean rooted;

    /**
     * Specifies if the browser has the ability to execute Java, as reported by the browser's navigator.javaEnabled
     * property.
     * [Optional]
     */
    @SerializedName("java_enabled")
    private Boolean javaEnabled;

    /**
     * Specifies if the browser has the ability to execute Javascript, as reported by the browser's
     * navigator.javascriptEnabled property.
     * Only required for 3D Secure authentications processed with 3DS 2.2. If the payment is processed with an older 3DS
     * version, this field is ignored.
     * [Optional]
     */
    @SerializedName("javascript_enabled")
    private Boolean javascriptEnabled;

    /**
     * The browser language, as reported by the browser's navigator.language property.
     * Format – IETF BCP47 language tag
     * [Optional]
     * [ 1 .. 12 ] characters
     */
    private String language;

    /**
     * The bit depth of the color palette for displaying images in bits per pixel, as reported by the browser's
     * screen.colorDepth property.
     * [Optional]
     * [ 1 .. 2 ] characters
     */
    @SerializedName("color_depth")
    private String colorDepth;

    /**
     * The total height of the device screen in pixels, as reported by the browser's screen.height property.
     * [Optional]
     * [ 1 .. 6 ] characters
     */
    @SerializedName("screen_height")
    private String screenHeight;

    /**
     * The total width of the device screen in pixels, as reported by the browser's screen.width property.
     * [Optional]
     * [ 1 .. 6 ] characters
     */
    @SerializedName("screen_width")
    private String screenWidth;

    /**
     * The raw Sec-CH-UA header value. This field improves Google Single-Page Application (SPA) support, as it provides
     * more accurate information about the customer's browser.
     * [Optional]
     */
    @SerializedName("user_agent_client_hint")
    private String userAgentClientHint;

    /**
     * Indicates whether the Payments API is enabled for all parent frames.
     * [Optional]
     */
    @SerializedName("iframe_payment_allowed")
    private Boolean iframePaymentAllowed;

    /**
     * The exact content of the HTTP Accept headers as sent to the 3DS Requestor from the cardholder’s browser.
     * [Optional]
     * <= 2048
     */
    @SerializedName("accept_header")
    private String acceptHeader;

}
