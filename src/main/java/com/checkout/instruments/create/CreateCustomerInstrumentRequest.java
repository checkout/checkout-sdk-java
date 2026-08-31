package com.checkout.instruments.create;

import com.checkout.common.CustomerRequest;
import com.checkout.common.Phone;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The customer's details. Associates the instrument with an existing or new customer.
 *
 * <p>The email, name and phone number are inherited from {@link CustomerRequest}. The email is
 * limited to max 255 characters and must be a valid email address, and the name to max 255
 * characters; the name and phone number are only applied when a new customer is created.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public final class CreateCustomerInstrumentRequest extends CustomerRequest {

    /**
     * The identifier of an existing customer.
     * [Optional]
     * ^(cus)_(\w{26})$
     */
    private String id;

    /**
     * If true, this instrument will become the default for the customer. If a new customer is
     * created as a result of this request, the instrument will automatically be the default.
     * [Optional]
     */
    @SerializedName("default")
    private Boolean defaultInstrument;

    /**
     * @deprecated Use {@link #getDefaultInstrument()}.
     */
    @Deprecated
    public Boolean isDefaultInstrument() {
        return defaultInstrument;
    }

    @Builder
    private CreateCustomerInstrumentRequest(final String email,
                                           final String name,
                                           final Phone phone,
                                           final String id,
                                           final Boolean defaultInstrument) {
        super(email, name, phone);
        this.id = id;
        this.defaultInstrument = defaultInstrument;
    }
}
