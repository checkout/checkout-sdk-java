package com.checkout.issuing.cards.requests.create;

import com.checkout.common.Address;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ShippingInstruction {

    /**
     * The name of the person the card will be shipped to.
     * [Optional]
     * @deprecated No longer supported.
     */
    @Deprecated
    private String shippingRecipient;

    /**
     * The address to ship the physical card to.
     * [Optional]
     */
    private Address shippingAddress;

    /**
     * Any additional comment on shipping.
     * [Optional]
     * @deprecated No longer supported.
     */
    @Deprecated
    private String additionalComment;
}
