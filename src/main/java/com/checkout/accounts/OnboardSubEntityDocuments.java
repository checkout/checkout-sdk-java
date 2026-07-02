package com.checkout.accounts;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class OnboardSubEntityDocuments {

    private Document identityVerification;

    private CompanyVerification companyVerification;

    private ArticlesOfAssociationType articlesOfAssociation;

    private BankVerification bankVerification;

    private ShareholderStructure shareholderStructure;

    private ProofOfLegality proofOfLegality;

    private ProofOfPrincipalAddress proofOfPrincipalAddress;

    @SerializedName("additional_document_1")
    private AdditionalDocument additionalDocument1;

    @SerializedName("additional_document_2")
    private AdditionalDocument additionalDocument2;

    @SerializedName("additional_document_3")
    private AdditionalDocument additionalDocument3;

    private TaxVerification taxVerification;

    private FinancialVerification financialVerification;

}
