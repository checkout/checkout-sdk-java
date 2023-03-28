package com.checkout.sessions.channel;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class BrowserSession extends ChannelData {

    @SerializedName("three_ds_method_completion")
    private ThreeDsMethodCompletion threeDsMethodCompletion;

    @SerializedName("accept_header")
    private String acceptHeader;

    @SerializedName("java_enabled")
    private Boolean javaEnabled;

    @SerializedName("javascript_enabled")
    private Boolean javascriptEnabled;

    @SerializedName("language")
    private String language;

    @SerializedName("color_depth")
    private String colorDepth;

    @SerializedName("screen_height")
    private String screenHeight;

    @SerializedName("screen_width")
    private String screenWidth;

    private String timezone;

    @SerializedName("user_agent")
    private String userAgent;

    @SerializedName("ip_address")
    private String ipAddress;

    @Builder
    private BrowserSession(final ThreeDsMethodCompletion threeDsMethodCompletion,
                           final String acceptHeader,
                           final Boolean javaEnabled,
                           final Boolean javascriptEnabled,
                           final String language,
                           final String colorDepth,
                           final String screenHeight,
                           final String screenWidth,
                           final String timezone,
                           final String userAgent,
                           final String ipAddress) {
        super(ChannelType.BROWSER);
        this.threeDsMethodCompletion = threeDsMethodCompletion;
        this.acceptHeader = acceptHeader;
        this.javaEnabled = javaEnabled;
        this.javascriptEnabled = javascriptEnabled;
        this.language = language;
        this.colorDepth = colorDepth;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.timezone = timezone;
        this.userAgent = userAgent;
        this.ipAddress = ipAddress;
    }

    public BrowserSession() {
        super(ChannelType.BROWSER);
    }

}
