package com.checkout.accounts;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Verification documents for a sub-entity. This one type serves two different objects on the
 * Accounts API, which accept different keys:
 * <ul>
 * <li>The top-level request {@code documents}, on {@link OnboardEntityRequest}. The API
 * ignores keys it does not recognise here rather than rejecting them, so a misplaced document is
 * dropped silently.</li>
 * <li>A representative's {@code documents}, on {@link Representative}. This object is
 * strict: it accepts only {@code identity_verification}, {@code certified_authorised_signatory},
 * {@code proof_of_residential_address} and {@code proof_of_registration}, and rejects any other
 * key.</li>
 * </ul>
 * Each field below says which of the two it belongs to.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class OnboardSubEntityDocuments {

    // Both

    /**
     * The document to use to confirm the individual's identity. Valid in both objects:
     * <ul>
     * <li>Representative: [Required] for the EEA, GB and US Sole Trader Full (3.0) variants;
     * [Optional] for the company variants.</li>
     * <li>Top level: [Required] for the six sole trader variants of Accounts API v2.0, the only
     * variants that take it there.</li>
     * </ul>
     */
    private Document identityVerification;

    // Top level

    /**
     * The document to use to confirm the company's identity (certified by a power of attorney
     * within the last 3 months). Top level only.
     * [Required] for EEA Company Full (2.0 and 3.0) and GB Company Full (2.0); [Optional] for the
     * other company variants and the US ISV Seller variants.
     */
    private CompanyVerification companyVerification;

    /**
     * Memorandum or Articles of Association document. Top level only.
     * [Required] for EEA and GB Company Full (3.0); [Optional] for US Company Full (3.0) and the US
     * ISV Seller variants.
     */
    private ArticlesOfAssociation articlesOfAssociation;

    /**
     * A document showing transactions from the last 3 months. Top level only.
     * [Required] for EEA Company Full (3.0) and the EEA, GB and US Sole Trader Full (3.0) variants;
     * [Optional] for GB and US Company Full (3.0) and EEA Company Full and Lite (2.0).
     */
    private BankVerification bankVerification;

    /**
     * Shareholder structure chart (including % of shares) certified by a competent authority
     * individual and dated within the last 3 months. Top level only.
     * [Required] for EEA and GB Company Full (3.0); [Optional] for US Company Full (3.0) and US ISV
     * Seller Company (3.0).
     */
    private ShareholderStructure shareholderStructure;

    /**
     * A regulatory licence document required for the company to operate (when applicable). Top
     * level only.
     * [Optional] (EEA, GB and US Company Full (3.0) and the US ISV Seller variants)
     */
    private ProofOfLegality proofOfLegality;

    /**
     * Proof of the company's principal place of business. Top level only.
     * [Optional] (EEA, GB and US Company Full (3.0) and the US ISV Seller variants)
     */
    private ProofOfPrincipalAddress proofOfPrincipalAddress;

    /**
     * Additional space for documents to be provided when requested. Top level only.
     * [Optional] (EEA, GB and US Company and Sole Trader Full (3.0); not the US ISV Seller variants)
     */
    @SerializedName("additional_document1")
    private AdditionalDocument additionalDocument1;

    /**
     * Additional space for documents to be provided when requested. Top level only.
     * [Optional] (EEA, GB and US Company and Sole Trader Full (3.0); not the US ISV Seller variants)
     */
    @SerializedName("additional_document2")
    private AdditionalDocument additionalDocument2;

    /**
     * Additional space for documents to be provided when requested. Top level only.
     * [Optional] (EEA, GB and US Company and Sole Trader Full (3.0); not the US ISV Seller variants)
     */
    @SerializedName("additional_document3")
    private AdditionalDocument additionalDocument3;

    /**
     * IRS-issued Employer Identification Number document used to verify the entity's tax
     * identification. Top level only.
     * [Optional] (US Company variants and the US ISV Seller variants only)
     */
    private TaxVerification taxVerification;

    /**
     * Financial statement document. Becomes mandatory depending on the answer provided for
     * {@code annual_processing_volume}; the sub-entity's status changes to {@code requirements_due}
     * when it is needed. Top level only.
     * [Optional] (EEA Company Full and Lite (2.0) only)
     */
    private FinancialVerification financialVerification;

    /**
     * Audited or management-prepared financial statements (when applicable). Top level only.
     * [Optional] (US ISV Seller variants only)
     */
    private FinancialStatements financialStatements;

    // Representative only

    /**
     * Certified authorised signatory document. Required when the legal representative or other
     * role owner is not registered on the certificate of incorporation. Representative only
     * ({@code company.representatives[].documents}); not accepted at the top level.
     * [Optional] (EEA, GB and US Company Full (3.0) and US ISV Seller Company (3.0))
     */
    private CertifiedAuthorisedSignatory certifiedAuthorisedSignatory;

    /**
     * Proof of residential address of the representative. Representative only
     * ({@code company.representatives[].documents}); not accepted at the top level.
     * [Required] for EEA Sole Trader Full (3.0), and only valid there.
     */
    private ProofOfResidentialAddress proofOfResidentialAddress;

    /**
     * Proof of the sole trader's registration, for example an extract from a trade register.
     * Representative only ({@code company.representatives[].documents}); not accepted at the top
     * level.
     * [Required] for EEA Sole Trader Full (3.0), and only valid there.
     */
    private ProofOfRegistration proofOfRegistration;

}
