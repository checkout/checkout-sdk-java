package com.checkout.apm.previous.sepa;

import com.checkout.HttpMetadata;
import com.checkout.common.Link;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public final class SepaResource extends HttpMetadata {

    @SerializedName("_links")
    private Map<String, Link> links = new HashMap<>();

}
