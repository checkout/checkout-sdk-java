package com.checkout.issuing.cards.requests.update;

import com.checkout.IHeaders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The return-encrypted-cvv and Encryption-Key HTTP headers for updating a card's details
 * (PATCH /issuing/cards/{cardId}). They are still sent when set.
 *
 * @deprecated These headers are no longer documented by the API and are still sent when set;
 * use the method without headers. Will be removed in the next major version.
 */
@Deprecated
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardUpdateHeaders implements IHeaders {

    /**
     * The value sent in the return-encrypted-cvv HTTP header. This header is no longer
     * documented by the API. Still sent when set.
     * [Optional]
     */
    private Boolean returnEncryptedCvv;

    /**
     * The value sent in the Encryption-Key HTTP header. This header is no longer documented
     * by the API. Still sent when set.
     * [Optional]
     */
    private String encryptionKey;

    @Override
    public Map<String, String> getHeaders() {
        final Map<String, String> headers = new LinkedHashMap<>();
        if (returnEncryptedCvv != null) {
            headers.put("return-encrypted-cvv", String.valueOf(returnEncryptedCvv));
        }
        if (encryptionKey != null) {
            headers.put("Encryption-Key", encryptionKey);
        }
        return headers;
    }
}
