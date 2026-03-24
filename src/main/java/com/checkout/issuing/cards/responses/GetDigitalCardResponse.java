package com.checkout.issuing.cards.responses;

import com.checkout.common.Resource;
import com.checkout.issuing.cards.IssuingDevice;
import com.checkout.issuing.cards.IssuingRequestor;
import com.google.gson.annotations.SerializedName;
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

    @SerializedName("card_id")
    private String cardId;

    @SerializedName("client_id")
    private String clientId;

    @SerializedName("entity_id")
    private String entityId;

    @SerializedName("last_four")
    private String lastFour;

    /**
     * The digital card's current status: active, inactive, or deleted.
     */
    private String status;

    /**
     * The type of digital card: secure_element, host_card_emulation, card_on_file, e_commerce, or qr_code.
     */
    private String type;

    @SerializedName("scheme_card_id")
    private String schemeCardId;

    private IssuingRequestor requestor;

    private IssuingDevice device;

    @SerializedName("provisioned_on")
    private String provisionedOn;

}
