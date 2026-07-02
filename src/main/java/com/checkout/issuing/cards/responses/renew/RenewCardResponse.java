package com.checkout.issuing.cards.responses.renew;

import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.Resource;
import com.checkout.issuing.cards.CardStatus;
import com.checkout.issuing.cards.CardType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RenewCardResponse extends Resource {

    private String parentCardId;

    private String cardholderId;

    private CardStatus status;

    private CardType type;

    private String id;

    private String clientId;

    private String entityId;

    private String lastFour;

    private Integer expiryYear;

    private Integer expiryMonth;

    private String displayName;

    private String reference;

    private Instant createdDate;

    private Currency billingCurrency;

    private CountryCode issuingCountry;
}