package com.checkout.issuing.cards.responses.credentials;

import com.checkout.HttpMetadata;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CardCredentialsResponse extends HttpMetadata {

    private String number;

    private String cvc2;
}
