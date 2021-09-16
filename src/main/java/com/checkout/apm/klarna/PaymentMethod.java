package com.checkout.apm.klarna;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public final class PaymentMethod {

    private String identifier;

    private String name;

    @SerializedName("asset_urls")
    private List<AssetUrl> assetUrls;

    @Data
    public static class AssetUrl {

        private String descriptive;

        private String standard;

    }

}
