package com.checkout.customers.previous;

import com.checkout.HttpMetadata;
import com.checkout.common.Phone;
import com.checkout.instruments.previous.InstrumentDetails;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public final class CustomerDetailsResponse extends HttpMetadata {

    private String id;

    private String email;

    @SerializedName("default")
    private String defaultId;

    private String name;

    private Phone phone;

    private Map<String, Object> metadata;

    private List<InstrumentDetails> instruments;

}
