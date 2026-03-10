package com.checkout.accounts.files.entities;

import com.google.gson.annotations.SerializedName;

public enum FilePurpose {
    @SerializedName("additional_document")
    ADDITIONAL_DOCUMENT,
    @SerializedName("articles_of_association")
    ARTICLES_OF_ASSOCIATION,
    @SerializedName("bank_verification")
    BANK_VERIFICATION,
    @SerializedName("certified_authorised_signatory")
    CERTIFIED_AUTHORISED_SIGNATORY,
    @SerializedName("company_ownership")
    COMPANY_OWNERSHIP,
    @SerializedName("company_verification")
    COMPANY_VERIFICATION,
    @SerializedName("financial_verification")
    FINANCIAL_VERIFICATION,
    @SerializedName("identity_verification")
    IDENTITY_VERIFICATION,
    @SerializedName("proof_of_legality")
    PROOF_OF_LEGALITY,
    @SerializedName("proof_of_principal_address")
    PROOF_OF_PRINCIPAL_ADDRESS,
    @SerializedName("shareholder_structure")
    SHAREHOLDER_STRUCTURE,
    @SerializedName("tax_verification") 
    TAX_VERIFICATION,
    @SerializedName("proof_of_residential_address")
    PROOF_OF_RESIDENTIAL_ADDRESS,
    @SerializedName("proof_of_registration")
    PROOF_OF_REGISTRATION,
    @SerializedName("dispute_evidence")
    DISPUTE_EVIDENCE
}