package com.checkout.issuing.cards.responses;

import com.checkout.common.Resource;
import com.checkout.issuing.cards.IssuingDevice;
import com.checkout.issuing.cards.IssuingRequestor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Response returned when retrieving digital card details.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class GetDigitalCardResponse extends Resource {

    private String id;

    private String cardId;

    private String clientId;

    private String entityId;

    private String lastFour;

    /**
     * The digital card's current status: active, inactive, or deleted.
     */
    private String status;

    /**
     * The type of digital card: secure_element, host_card_emulation, card_on_file, e_commerce, or qr_code.
     */
    private String type;

    private String schemeCardId;

    private IssuingRequestor requestor;

    private IssuingDevice device;

    private String provisionedOn;

}
