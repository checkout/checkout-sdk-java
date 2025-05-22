package com.checkout.forward.requests.sources;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class IdSource extends AbstractSource {

    /**
     * The unique identifier of the payment instrument (Required, pattern ^(src)_(\w{26})$)
     */
    private String id;

    /**
     * The unique token for the card's security code. Checkout.com does not store a card's Card Verification Value (CVV)
     * with its associated payment instrument. To pass a CVV with your forward request, use the Frames SDK for Android
     * or iOS to collect and tokenize the CVV and pass the value in this field. The token will replace the placeholder
     * {{card_cvv}} value in destination_request.body (Optional, pattern ^(tok)_(\w{26})$)
     */
    @SerializedName("cvv_token")
    private String cvvToken;

    /**
     * Initializes a new instance of the IdSource class.
     */
    @Builder
    private IdSource(String id, String cvvToken) {
        super(SourceType.ID);
        this.id = id;
        this.cvvToken = cvvToken;
    }

    public IdSource() {
        super(SourceType.ID);
    }

}
