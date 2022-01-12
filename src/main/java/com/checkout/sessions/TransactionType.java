package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;

public enum TransactionType {

    @SerializedName("goods_service")
    GOODS_SERVICE,
    @SerializedName("check_acceptance")
    CHECK_ACCEPTANCE,
    @SerializedName("account_funding")
    ACCOUNT_FUNDING,
    @SerializedName("quashi_card_transaction")
    QUASHI_CARD_TRANSACTION,
    @SerializedName("prepaid_activation_and_load")
    PREPAID_ACTIVATION_AND_LOAD

}
