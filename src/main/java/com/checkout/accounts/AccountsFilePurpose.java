package com.checkout.accounts;

import lombok.Getter;

public enum AccountsFilePurpose {

    BANK_VERIFICATION("bank_verification"),
    IDENTIFICATION("identification"),
    IDENTITY_VERIFICATION("identity_verification"),
    COMPANY_VERIFICATION("company_verification"),
    FINANCIAL_VERIFICATION("financial_verification"),
    TAX_VERIFICATION("tax_verification");

    @Getter
    private final String purpose;

    AccountsFilePurpose(final String purpose) {
        this.purpose = purpose;
    }

}
