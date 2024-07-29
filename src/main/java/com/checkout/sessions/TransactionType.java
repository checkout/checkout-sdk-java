package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;

public enum TransactionType {

    @SerializedName("account_funding")
    ACCOUNT_FUNDING,
    @SerializedName("check_acceptance")
    CHECK_ACCEPTANCE,
    @SerializedName("goods_service")
    GOODS_SERVICE,
    @SerializedName("prepaid_activation_and_load")
    PREPAID_ACTIVATION_AND_LOAD,
    @SerializedName("quashi_card_transaction")
    QUASHI_CARD_TRANSACTION,

}
