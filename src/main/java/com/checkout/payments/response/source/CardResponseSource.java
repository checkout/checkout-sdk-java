package com.checkout.payments.response.source;

import com.checkout.common.AccountHolder;
import com.checkout.common.AccountHolderResponse;
import com.checkout.common.Address;
import com.checkout.common.CardCategory;
import com.checkout.common.CardType;
import com.checkout.common.CardWalletType;
import com.checkout.common.CountryCode;
import com.checkout.common.Phone;
import com.checkout.payments.AccountUpdateStatusType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CardResponseSource extends AbstractResponseSource implements ResponseSource {

    private Address billingAddress;

    private Phone phone;

    // This is set explicitly to String because the API mask the response with "****" and this will cause deserialization 
    // issues if it is set to Instant
    private String expiryMonth;

    // This is set explicitly to String because the API mask the response with "****" and this will cause deserialization 
    // issues if it is set to Instant
    private String expiryYear;

    private String name;

    private String scheme;

    /**
     * @deprecated This property will be removed in the future, and should be used
     * {@link CardResponseSource#localSchemes} instead
     */
    @Deprecated
    private String schemeLocal;

    private List<String> localSchemes;

    private String last4;

    private String fingerprint;

    private String bin;

    private CardType cardType;

    private CardCategory cardCategory;

    private CardWalletType cardWalletType;

    private String issuer;

    private CountryCode issuerCountry;

    private String productId;

    private String productType;

    private String avsCheck;

    private String cvvCheck;

    private String paymentAccountReference;

    private String encryptedCardNumber;

    private AccountUpdateStatusType accountUpdateStatus;

    private AccountHolderResponse accountHolder;

}
