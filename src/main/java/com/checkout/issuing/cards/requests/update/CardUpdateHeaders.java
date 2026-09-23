package com.checkout.issuing.cards.requests.update;

import com.checkout.IHeaders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The optional HTTP headers accepted when updating a card's details.
 *
 * @see <a href="https://api-reference.checkout.com/">PATCH /issuing/cards/{cardId}</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardUpdateHeaders implements IHeaders {

    /**
     * The return-encrypted-cvv HTTP header. Set to true to retrieve the card's encrypted
     * credentials in the response. Requires an RSA public key to be provided in the
     * Encryption-Key header.
     * [Optional]
     */
    private Boolean returnEncryptedCvv;

    /**
     * The Encryption-Key HTTP header. The RSA public key used to encrypt returned credentials.
     * Required when the return-encrypted-cvv header is set to true. Provide the public key with
     * the BEGIN PUBLIC KEY and END PUBLIC KEY headers and any newline characters removed,
     * encoded as Base64.
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
