package com.checkout.sources.previous;

import com.checkout.common.CustomerResponse;
import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class SourceResponse extends Resource {

    private String id;

    private SourceType type;

    @SerializedName("response_code")
    private String responseCode;

    private CustomerResponse customer;

}