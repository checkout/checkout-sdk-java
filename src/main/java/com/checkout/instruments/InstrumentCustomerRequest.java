package com.checkout.instruments;

import com.checkout.common.CustomerRequest;
import com.checkout.common.Phone;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public final class InstrumentCustomerRequest extends CustomerRequest {

    @SerializedName("default")
    private boolean isDefault;

    private Phone phone;

    @Builder
    public InstrumentCustomerRequest(final String id,
                                     final String email,
                                     final String name,
                                     final boolean isDefault,
                                     final Phone phone) {
        super(id, email, name);
        this.isDefault = isDefault;
        this.phone = phone;
    }

}
