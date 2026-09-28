package com.checkout.issuing.cards.responses;

import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.Resource;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public final class CardResponse extends Resource {

    protected String id;

    protected String displayName;

    protected String lastFour;

    protected Integer expiryMonth;

    protected Integer expiryYear;

    protected Currency billingCurrency;

    protected CountryCode issuingCountry;

    protected String reference;

    protected Instant createdDate;

    /**
     * The date and time the card was last activated.
     * <p>
     * [Optional, nullable, read only]
     * </p>
     * Returns {@code null} if the card has never been activated.
     */
    protected Instant lastActivatedOn;
}
