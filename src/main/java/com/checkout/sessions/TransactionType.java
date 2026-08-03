package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;

/**
 * Identifies the type of transaction being authenticated.
 * <p>
 * Used by {@link SessionRequest#getTransactionType()} and
 * {@link GetSessionResponse#getTransactionType()}.
 * <p>
 * [Optional]
 * <p>
 * Default: {@link #GOODS_SERVICE}
 * <p>
 * max 50 characters
 */
public enum TransactionType {

    /**
     * A transaction that funds an account.
     */
    @SerializedName("account_funding")
    ACCOUNT_FUNDING,

    /**
     * A transaction that accepts a check.
     */
    @SerializedName("check_acceptance")
    CHECK_ACCEPTANCE,

    /**
     * A transaction for goods or a service. This is the default.
     */
    @SerializedName("goods_service")
    GOODS_SERVICE,

    /**
     * A transaction that activates or loads a prepaid card.
     */
    @SerializedName("prepaid_activation_and_load")
    PREPAID_ACTIVATION_AND_LOAD,

    /**
     * A quasi-cash transaction, for example the purchase of casino chips, money orders or
     * traveller's cheques.
     */
    @SerializedName("quasi_card_transaction")
    QUASI_CARD_TRANSACTION,

}
