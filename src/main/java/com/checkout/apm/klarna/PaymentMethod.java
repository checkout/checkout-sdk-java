package com.checkout.apm.klarna;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public final class PaymentMethod {

    private String identifier;

    private String name;

    @SerializedName("asset_urls")
    private AssetUrl assetUrls;

    @Data
    public static final class AssetUrl {

        private String descriptive;

        private String standard;

    }

}
