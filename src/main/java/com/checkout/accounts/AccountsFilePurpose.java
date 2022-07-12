package com.checkout.accounts;

import lombok.Getter;

public enum AccountsFilePurpose {

    BANK_VERIFICATION("bank_verification"),
    IDENTIFICATION("identification"),
    COMPANY_VERIFICATION("company_verification");

    @Getter
    private final String purpose;

    AccountsFilePurpose(final String purpose) {
        this.purpose = purpose;
    }

}
