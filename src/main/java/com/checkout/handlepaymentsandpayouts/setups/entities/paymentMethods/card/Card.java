package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.card;

import com.checkout.common.Phone;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.CardAccountHolder;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentSetupScheme;
import com.checkout.payments.PreferredSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.List;

/**
 * The Card payment method's details and configuration.
 *
 * <p>The card data lives under {@link #getDetails()}, which maps {@code card.details}. The flat
 * accessors such as {@link #setNumber(String)} are deprecated: the API ignores those keys at the card
 * level, so they now read and write the same values inside {@code details}.</p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class Card extends PaymentMethodBase {

    /**
     * The Checkout.com card token to use instead of the raw card details.
     * [Optional]
     */
    private CardToken token;

    /**
     * The card details.
     * [Optional]
     */
    private CardDetails details;

    /**
     * Set to true if you intend to reuse the payment credentials in subsequent payments. If set to
     * false, a payment instrument will not be included in the payment response.
     * [Optional] writeOnly
     */
    private Boolean storeForFutureUse;

    /**
     * The customer's phone number.
     * [Optional]
     */
    private Phone phone;

    /**
     * The card account holder details.
     * [Optional]
     */
    private CardAccountHolder accountHolder;

    /**
     * The list of supported card scheme names.
     * [Optional] readOnly
     */
    private List<PaymentSetupScheme> schemes;

    /**
     * The preferred card scheme to process the payment.
     * [Optional]
     */
    private PreferredSchema preferredScheme;

    private CardDetails detailsOrCreate() {
        if (details == null) {
            details = new CardDetails();
        }
        return details;
    }

    /**
     * @deprecated Use {@link CardDetails#getNumber()} through {@link #getDetails()}. The API
     * expects this value under {@code card.details} and drops it at the card level.
     */
    @Deprecated
    public String getNumber() {
        return details == null ? null : details.getNumber();
    }

    /**
     * @deprecated Use {@link CardDetails#setNumber(String)} through {@link #setDetails(CardDetails)}.
     * The API expects this value under {@code card.details}; this setter now writes it there.
     */
    @Deprecated
    public void setNumber(final String number) {
        detailsOrCreate().setNumber(number);
    }

    /**
     * @deprecated Use {@link CardDetails#getLast4()} through {@link #getDetails()}. The API
     * expects this value under {@code card.details} and drops it at the card level.
     */
    @Deprecated
    public String getLast4() {
        return details == null ? null : details.getLast4();
    }

    /**
     * @deprecated Use {@link CardDetails#setLast4(String)} through {@link #setDetails(CardDetails)}.
     * The API expects this value under {@code card.details}; this setter now writes it there.
     */
    @Deprecated
    public void setLast4(final String last4) {
        detailsOrCreate().setLast4(last4);
    }

    /**
     * @deprecated Use {@link CardDetails#getBin()} through {@link #getDetails()}. The API
     * expects this value under {@code card.details} and drops it at the card level.
     */
    @Deprecated
    public String getBin() {
        return details == null ? null : details.getBin();
    }

    /**
     * @deprecated Use {@link CardDetails#setBin(String)} through {@link #setDetails(CardDetails)}.
     * The API expects this value under {@code card.details}; this setter now writes it there.
     */
    @Deprecated
    public void setBin(final String bin) {
        detailsOrCreate().setBin(bin);
    }

    /**
     * @deprecated Use {@link CardDetails#getScheme()} through {@link #getDetails()}. The API
     * expects this value under {@code card.details} and drops it at the card level.
     */
    @Deprecated
    public String getScheme() {
        return details == null ? null : details.getScheme();
    }

    /**
     * @deprecated Use {@link CardDetails#setScheme(String)} through {@link #setDetails(CardDetails)}.
     * The API expects this value under {@code card.details}; this setter now writes it there.
     */
    @Deprecated
    public void setScheme(final String scheme) {
        detailsOrCreate().setScheme(scheme);
    }

    /**
     * @deprecated Use {@link CardDetails#getExpiryMonth()} through {@link #getDetails()}. The API
     * expects this value under {@code card.details} and drops it at the card level.
     */
    @Deprecated
    public Integer getExpiryMonth() {
        return details == null ? null : details.getExpiryMonth();
    }

    /**
     * @deprecated Use {@link CardDetails#setExpiryMonth(Integer)} through {@link #setDetails(CardDetails)}.
     * The API expects this value under {@code card.details}; this setter now writes it there.
     */
    @Deprecated
    public void setExpiryMonth(final Integer expiryMonth) {
        detailsOrCreate().setExpiryMonth(expiryMonth);
    }

    /**
     * @deprecated Use {@link CardDetails#getExpiryYear()} through {@link #getDetails()}. The API
     * expects this value under {@code card.details} and drops it at the card level.
     */
    @Deprecated
    public Integer getExpiryYear() {
        return details == null ? null : details.getExpiryYear();
    }

    /**
     * @deprecated Use {@link CardDetails#setExpiryYear(Integer)} through {@link #setDetails(CardDetails)}.
     * The API expects this value under {@code card.details}; this setter now writes it there.
     */
    @Deprecated
    public void setExpiryYear(final Integer expiryYear) {
        detailsOrCreate().setExpiryYear(expiryYear);
    }

    /**
     * @deprecated Use {@link CardDetails#getName()} through {@link #getDetails()}. The API
     * expects this value under {@code card.details} and drops it at the card level.
     */
    @Deprecated
    public String getName() {
        return details == null ? null : details.getName();
    }

    /**
     * @deprecated Use {@link CardDetails#setName(String)} through {@link #setDetails(CardDetails)}.
     * The API expects this value under {@code card.details}; this setter now writes it there.
     */
    @Deprecated
    public void setName(final String name) {
        detailsOrCreate().setName(name);
    }

    /**
     * @deprecated Use {@link CardDetails#getCvv()} through {@link #getDetails()}. The API
     * expects this value under {@code card.details} and drops it at the card level.
     */
    @Deprecated
    public String getCvv() {
        return details == null ? null : details.getCvv();
    }

    /**
     * @deprecated Use {@link CardDetails#setCvv(String)} through {@link #setDetails(CardDetails)}.
     * The API expects this value under {@code card.details}; this setter now writes it there.
     */
    @Deprecated
    public void setCvv(final String cvv) {
        detailsOrCreate().setCvv(cvv);
    }

    /**
     * @deprecated Use {@link CardDetails#getStored()} through {@link #getDetails()}. The API
     * expects this value under {@code card.details} and drops it at the card level.
     */
    @Deprecated
    public Boolean getStored() {
        return details == null ? null : details.getStored();
    }

    /**
     * @deprecated Use {@link CardDetails#setStored(Boolean)} through {@link #setDetails(CardDetails)}.
     * The API expects this value under {@code card.details}; this setter now writes it there.
     */
    @Deprecated
    public void setStored(final Boolean stored) {
        detailsOrCreate().setStored(stored);
    }

    /**
     * @deprecated Use {@link CardDetails#getExpiresOn()} through {@link #getDetails()}. The API
     * expects this value under {@code card.details} and drops it at the card level.
     */
    @Deprecated
    public Instant getExpiresOn() {
        return details == null ? null : details.getExpiresOn();
    }

    /**
     * @deprecated Use {@link CardDetails#setExpiresOn(Instant)} through {@link #setDetails(CardDetails)}.
     * The API expects this value under {@code card.details}; this setter now writes it there.
     */
    @Deprecated
    public void setExpiresOn(final Instant expiresOn) {
        detailsOrCreate().setExpiresOn(expiresOn);
    }

    /**
     * @deprecated Use {@link CardDetails#getAllowUpdate()} through {@link #getDetails()}. The API
     * expects this value under {@code card.details} and drops it at the card level.
     */
    @Deprecated
    public Boolean getAllowUpdate() {
        return details == null ? null : details.getAllowUpdate();
    }

    /**
     * @deprecated Use {@link CardDetails#setAllowUpdate(Boolean)} through {@link #setDetails(CardDetails)}.
     * The API expects this value under {@code card.details}; this setter now writes it there.
     */
    @Deprecated
    public void setAllowUpdate(final Boolean allowUpdate) {
        detailsOrCreate().setAllowUpdate(allowUpdate);
    }
}
