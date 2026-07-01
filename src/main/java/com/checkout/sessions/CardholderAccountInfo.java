package com.checkout.sessions;

import com.checkout.payments.AccountChangeIndicator;
import com.checkout.payments.AccountPasswordChangeIndicator;
import com.checkout.payments.AccountTypeCardProduct;
import com.checkout.payments.CardholderAccountAgeIndicator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class CardholderAccountInfo {

    private Long purchaseCount;

    private String accountAge;

    private Long addCardAttempts;

    private String shippingAddressAge;

    private Boolean accountNameMatchesShippingName;

    private Boolean suspiciousAccountActivity;

    private Long transactionsToday;

    /**
     * @deprecated This property will be removed in the future, and should be used
     */
    @Deprecated
    private AuthenticationMethod authenticationMethod;

    private CardholderAccountAgeIndicator cardholderAccountAgeIndicator;

    private Instant accountChange;

    private AccountChangeIndicator accountChangeIndicator;

    private Instant accountDate;

    private String accountPasswordChange;

    private AccountPasswordChangeIndicator accountPasswordChangeIndicator;

    private Integer transactionsPerYear;

    private Instant paymentAccountAge;

    private Instant shippingAddressUsage;

    private AccountTypeCardProduct accountType;

    private String accountId;

    private ThreeDsRequestorAuthenticationInfo threeDsRequestorAuthenticationInfo;

}
