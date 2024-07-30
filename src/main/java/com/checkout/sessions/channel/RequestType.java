package com.checkout.sessions.channel;

import com.google.gson.annotations.SerializedName;

public enum RequestType {

    @SerializedName("account_verification")
    ACCOUNT_VERIFICATION,
    @SerializedName("add_card")
    ADD_CARD,
    @SerializedName("installment_transaction")
    INSTALLMENT_TRANSACTION,
    @SerializedName("mail_order")
    MAIL_ORDER,
    @SerializedName("maintain_card_information")
    MAINTAIN_CARD_INFORMATION,
    @SerializedName("other_payment")
    OTHER_PAYMENT,
    @SerializedName("recurring_transaction")
    RECURRING_TRANSACTION,
    @SerializedName("split_or_delayed_shipment")
    SPLIT_OR_DELAYED_SHIPMENT,
    @SerializedName("telephone_order")
    TELEPHONE_ORDER,
    @SerializedName("top_up")
    TOP_UP,
    @SerializedName("whitelist_status_check")
    WHITELIST_STATUS_CHECK

}
