package com.checkout.sessions.channel;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class BrowserSession extends ChannelData {

    @Builder.Default
    private ThreeDsMethodCompletion threeDsMethodCompletion = ThreeDsMethodCompletion.U;

    private String acceptHeader;

    private Boolean javaEnabled;

    private Boolean javascriptEnabled;

    private String language;

    private String colorDepth;

    private String screenHeight;

    private String screenWidth;

    private String timezone;

    private String userAgent;

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
