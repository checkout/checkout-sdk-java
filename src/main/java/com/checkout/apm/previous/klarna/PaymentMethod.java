package com.checkout.apm.previous.klarna;

import lombok.Data;

@Data
public final class PaymentMethod {

    private String identifier;

    private String name;

    private AssetUrl assetUrls;

    @Data
    public static final class AssetUrl {

        private String descriptive;

        private String standard;

    }

}
