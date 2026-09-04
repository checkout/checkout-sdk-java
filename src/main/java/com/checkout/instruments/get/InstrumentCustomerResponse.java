package com.checkout.instruments.get;

import com.checkout.common.CustomerResponse;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * The customer that a retrieved payment instrument is associated with.
 *
 * <p>The id, email and name are inherited from {@link CustomerResponse}. That base class also
 * carries a phone number, which the retrieve instrument customer schema does not declare, so it is
 * always null here.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class InstrumentCustomerResponse extends CustomerResponse {

    /**
     * This will be true if this instrument is set as the default for the customer.
     * [Optional]
     */
    @SerializedName("default")
    private Boolean isDefault;

    public Boolean isDefault() {
        return isDefault;
    }

}
