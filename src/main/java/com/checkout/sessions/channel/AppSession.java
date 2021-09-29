package com.checkout.sessions.channel;

import com.checkout.sessions.UIElements;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class AppSession extends ChannelData {

    @SerializedName("sdk_app_id")
    private String sdkAppId;

    @SerializedName("sdk_max_timeout")
    private Long sdkMaxTimeout;

    @SerializedName("sdk_ephem_pub_key")
    private SdkEphemeralPublicKey sdkEphemeralPublicKey;

    @SerializedName("sdk_reference_number")
    private String sdkReferenceNumber;

    @SerializedName("sdk_encrypted_data")
    private String sdkEncryptedData;

    @SerializedName("sdk_transaction_id")
    private String sdkTransactionId;

    @SerializedName("sdk_interface_type")
    private SdkInterfaceType sdkInterfaceType;

    @SerializedName("sdk_ui_elements")
    private List<UIElements> sdkUIElements;

    @Builder
    private AppSession(final String sdkAppId,
                       final Long sdkMaxTimeout,
                       final SdkEphemeralPublicKey sdkEphemeralPublicKey,
                       final String sdkReferenceNumber,
                       final String sdkEncryptedData,
                       final String sdkTransactionId,
                       final SdkInterfaceType sdkInterfaceType,
                       final List<UIElements> sdkUIElements) {
        super(ChannelType.APP);
        this.sdkAppId = sdkAppId;
        this.sdkMaxTimeout = sdkMaxTimeout;
        this.sdkEphemeralPublicKey = sdkEphemeralPublicKey;
        this.sdkReferenceNumber = sdkReferenceNumber;
        this.sdkEncryptedData = sdkEncryptedData;
        this.sdkTransactionId = sdkTransactionId;
        this.sdkInterfaceType = sdkInterfaceType;
        this.sdkUIElements = sdkUIElements;
    }

    public AppSession() {
        super(ChannelType.APP);
    }

}

