package com.checkout.issuing.transactions.entities;

import com.google.gson.annotations.SerializedName;

public enum TransactionType {
    @SerializedName("account_funding")
    ACCOUNT_FUNDING,

    @SerializedName("account_transfer")
    ACCOUNT_TRANSFER,

    @SerializedName("atm_installment")
    ATM_INSTALLMENT,

    @SerializedName("balance_inquiry")
    BALANCE_INQUIRY,

    @SerializedName("bill_payment")
    BILL_PAYMENT,

    @SerializedName("cash_advance")
    CASH_ADVANCE,

    @SerializedName("cashback")
    CASHBACK,

    @SerializedName("credit_adjustment")
    CREDIT_ADJUSTMENT,

    @SerializedName("debit_adjustment")
    DEBIT_ADJUSTMENT,

    @SerializedName("original_credit")
    ORIGINAL_CREDIT,

    @SerializedName("payment_account_inquiry")
    PAYMENT_ACCOUNT_INQUIRY,

    @SerializedName("payment")
    PAYMENT,
    
    @SerializedName("pin_change")
    PIN_CHANGE,

    @SerializedName("pin_unblock")
    PIN_UNBLOCK,

    @SerializedName("purchase_account_inquiry")
    PURCHASE_ACCOUNT_INQUIRY,

    @SerializedName("purchase")
    PURCHASE,

    @SerializedName("quasi_cash")
    QUASI_CASH,

    @SerializedName("remittance_funding")
    REMITTANCE_FUNDING,

    @SerializedName("remittance_payment")
    REMITTANCE_PAYMENT,

    @SerializedName("unknown")
    UNKNOWN,

    @SerializedName("withdrawal")
    WITHDRAWAL,
    
    @SerializedName("refund")
    REFUND
}