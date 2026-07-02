package com.checkout.payments;

import com.checkout.sessions.AuthenticationMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class AccountInfo {

    private Long purchaseCount;

    private String accountAge;

    private Long addCardAttempts;

    private String shippingAddressAge;

    private Boolean accountNameMatchesShippingName;

    private Boolean suspiciousAccountActivity;

    private Long transactionsToday;

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
}
