package com.checkout.apm.ideal;

import com.checkout.common.Link;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public final class IdealInfo {

    @SerializedName("_links")
    private IdealInfoLinks links;

    @Data
    public static final class IdealInfoLinks {

        private Link self;

        private List<CuriesLink> curies;

        @SerializedName("ideal:issuers")
        private Link issuers;

    }

    @Data
    public static final class CuriesLink {

        private String name;
        private String href;
        private Boolean templated;

    }

}
