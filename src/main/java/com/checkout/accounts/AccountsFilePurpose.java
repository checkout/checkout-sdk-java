package com.checkout.accounts;

import lombok.Getter;

/**
 * The purpose of a file uploaded with {@link AccountsClient#submitFile(AccountsFileRequest)}. The
 * values match the purposes the Accounts API accepts for onboarding documents
 * ({@code PlatformsFileUpload}), plus the legacy {@link #IDENTIFICATION}.
 */
public enum AccountsFilePurpose {

    BANK_VERIFICATION("bank_verification"),
    /**
     * Legacy purpose, not among the onboarding upload purposes; use {@link #IDENTITY_VERIFICATION}.
     */
    IDENTIFICATION("identification"),
    IDENTITY_VERIFICATION("identity_verification"),
    COMPANY_VERIFICATION("company_verification"),
    FINANCIAL_VERIFICATION("financial_verification"),
    TAX_VERIFICATION("tax_verification"),
    ADDITIONAL_DOCUMENT("additional_document"),
    ARTICLES_OF_ASSOCIATION("articles_of_association"),
    CERTIFIED_AUTHORISED_SIGNATORY("certified_authorised_signatory"),
    COMPANY_OWNERSHIP("company_ownership"),
    PROOF_OF_LEGALITY("proof_of_legality"),
    PROOF_OF_PRINCIPAL_ADDRESS("proof_of_principal_address"),
    SHAREHOLDER_STRUCTURE("shareholder_structure"),
    PROOF_OF_RESIDENTIAL_ADDRESS("proof_of_residential_address"),
    PROOF_OF_REGISTRATION("proof_of_registration");

    @Getter
    private final String purpose;

    AccountsFilePurpose(final String purpose) {
        this.purpose = purpose;
    }

}
