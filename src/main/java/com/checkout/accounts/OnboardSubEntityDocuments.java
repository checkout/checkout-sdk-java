package com.checkout.accounts;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class OnboardSubEntityDocuments {

    @SerializedName("identity_verification")
    private Document identityVerification;

    @SerializedName("company_verification")
    private CompanyVerification companyVerification;

    @SerializedName("articles_of_association")
    private ArticlesOfAssociationType articlesOfAssociation;

    @SerializedName("bank_verification")
    private BankVerification bankVerification;
    
    @SerializedName("shareholder_structure")
    private ShareholderStructure shareholderStructure;
    
    @SerializedName("proof_of_legality")
    private ProofOfLegality proofOfLegality;

    @SerializedName("proof_of_principal_address")
    private ProofOfPrincipalAddress proofOfPrincipalAddress;

    @SerializedName("additional_document1")
    private AdditionalDocument additionalDocument1;

    @SerializedName("additional_document2")
    private AdditionalDocument additionalDocument2;

    @SerializedName("additional_document3")
    private AdditionalDocument additionalDocument3;

    @SerializedName("tax_verification")
    private TaxVerification taxVerification;

    @SerializedName("financial_verification")
    private FinancialVerification financialVerification;

}
