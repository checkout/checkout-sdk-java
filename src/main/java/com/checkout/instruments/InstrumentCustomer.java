package com.checkout.instruments;

import com.checkout.common.CustomerRequest;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class InstrumentCustomer extends CustomerRequest {

    @SerializedName("default")
    private final boolean isDefault;

    @Builder
    public InstrumentCustomer(final String id,
                              final String email,
                              final String name,
                              final boolean isDefault) {
        super(id, email, name);
        this.isDefault = isDefault;
    }

}
