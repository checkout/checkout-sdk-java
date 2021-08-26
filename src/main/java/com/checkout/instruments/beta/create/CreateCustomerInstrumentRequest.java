package com.checkout.instruments.beta.create;

import com.checkout.common.beta.CustomerRequest;
import com.checkout.common.beta.Phone;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CreateCustomerInstrumentRequest extends CustomerRequest {

    private final Phone phone;

    @SerializedName("default")
    private final boolean defaultInstrument;

    @Builder
    public CreateCustomerInstrumentRequest(final String id,
                                           final String email,
                                           final String name,
                                           final Phone phone,
                                           final boolean defaultInstrument) {
        super(id, email, name);
        this.phone = phone;
        this.defaultInstrument = defaultInstrument;
    }

}
