package com.checkout.instruments.create;

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
public final class CreateCustomerInstrumentRequest extends CustomerRequest {

    @SerializedName("default")
    private boolean defaultInstrument;

    @Builder
    private CreateCustomerInstrumentRequest(final String id,
                                           final String email,
                                           final String name,
                                           final Phone phone,
                                           final boolean defaultInstrument) {
        super(id, email, name, phone);
        this.defaultInstrument = defaultInstrument;
    }
}
