package com.checkout.instruments;

import com.checkout.common.CustomerRequest;
import com.checkout.common.Phone;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class InstrumentCustomerRequest extends CustomerRequest {

    @SerializedName("default")
    private final boolean isDefault;

    private final Phone phone;

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
