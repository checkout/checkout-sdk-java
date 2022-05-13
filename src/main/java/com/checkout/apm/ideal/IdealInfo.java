package com.checkout.apm.ideal;

import com.checkout.HttpMetadata;
import com.checkout.common.Link;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class IdealInfo extends HttpMetadata {

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
