package com.checkout.payments;

import com.checkout.sessions.AuthenticationMethod;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountInfo {

    @SerializedName("purchase_count")
    private Long purchaseCount;

    @SerializedName("account_age")
    private String accountAge;

    @SerializedName("add_card_attempts")
    private Long addCardAttempts;

    @SerializedName("shipping_address_age")
    private String shippingAddressAge;

    @SerializedName("account_name_matches_shipping_name")
    private Boolean accountNameMatchesShippingName;

    @SerializedName("suspicious_account_activity")
    private Boolean suspiciousAccountActivity;

    @SerializedName("transactions_today")
    private Long transactionsToday;

    @SerializedName("authentication_method")
    private AuthenticationMethod authenticationMethod;
    
    @SerializedName("cardholder_account_age_indicator")
    private CardholderAccountAgeIndicator cardholderAccountAgeIndicator;

    @SerializedName("account_change")
    private Instant accountChange;

    @SerializedName("account_change_indicator")
    private AccountChangeIndicator accountChangeIndicator;
    
    @SerializedName("account_date")
    private Instant accountDate;

    @SerializedName("account_password_change")
    private String accountPasswordChange;

    @SerializedName("account_password_change_indicator")
    private AccountPasswordChangeIndicator accountPasswordChangeIndicator;

    @SerializedName("transactions_per_year")
    private Integer transactionsPerYear;

    @SerializedName("payment_account_age")
    private Instant paymentAccountAge;

    @SerializedName("shipping_address_usage")
    private Instant shippingAddressUsage;

    @SerializedName("account_type")
    private AccountTypeCardProduct accountType;

    @SerializedName("account_id")
    private String accountId;
}
