package com.checkout.apm.sepa;

import com.checkout.common.Link;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public final class SepaResource {

    @SerializedName("_links")
    private Map<String, Link> links = new HashMap<>();

}
