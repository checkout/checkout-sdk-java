package com.checkout.accounts;

import com.checkout.GsonSerializer;
import com.checkout.common.DocumentType;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Covers the onboarding documents that carry a type plus a file ID.
 *
 * <p>articlesOfAssociation was typed as the ArticlesOfAssociationType enum and there was no
 * ArticlesOfAssociation class, so the SDK serialized {@code "articles_of_association":
 * "articles_of_association"} where the API requires an object. That document, which is required
 * on the company full variants, could not be sent from Java at all.</p>
 */
class OnboardSubEntityDocumentsSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeArticlesOfAssociationAsAnObject() {
        final OnboardSubEntityDocuments documents = OnboardSubEntityDocuments.builder()
                .articlesOfAssociation(ArticlesOfAssociation.builder()
                        .type(ArticlesOfAssociationType.ARTICLES_OF_ASSOCIATION)
                        .front("file_6lbss42ezvoufcb2beo76rvwly")
                        .build())
                .build();

        final String json = serializer.toJson(documents);

        assertTrue(json.contains("\"articles_of_association\":{"), json);
        assertTrue(json.contains("\"type\":\"articles_of_association\""), json);
        assertTrue(json.contains("\"front\":\"file_6lbss42ezvoufcb2beo76rvwly\""), json);
        // The old shape. If this ever comes back, the API rejects the request.
        assertFalse(json.contains("\"articles_of_association\":\"articles_of_association\""), json);
    }

    @Test
    void shouldSerializeMemorandumOfAssociation() {
        final OnboardSubEntityDocuments documents = OnboardSubEntityDocuments.builder()
                .articlesOfAssociation(ArticlesOfAssociation.builder()
                        .type(ArticlesOfAssociationType.MEMORANDUM_OF_ASSOCIATION)
                        .front("file_6lbss42ezvoufcb2beo76rvwly")
                        .build())
                .build();

        assertTrue(serializer.toJson(documents).contains("\"type\":\"memorandum_of_association\""));
    }

    /**
     * The sibling documents were already objects. Asserted here so the three stay consistent:
     * they are the same shape in the API and a future edit should not split them apart again.
     */
    @Test
    void shouldSerializeBankVerificationAndShareholderStructureAsObjects() {
        final OnboardSubEntityDocuments documents = OnboardSubEntityDocuments.builder()
                .bankVerification(BankVerification.builder()
                        .type(BankVerificationType.BANK_STATEMENT)
                        .front("file_bank")
                        .build())
                .shareholderStructure(ShareholderStructure.builder()
                        .type(ShareholderStructureType.CERTIFIED_SHAREHOLDER_STRUCTURE)
                        .front("file_shareholder")
                        .build())
                .build();

        final String json = serializer.toJson(documents);

        assertTrue(json.contains("\"bank_verification\":{\"type\":\"bank_statement\""), json);
        assertTrue(json.contains("\"shareholder_structure\":{\"type\":\"certified_shareholder_structure\""), json);
    }

    @Test
    void shouldDeserializeArticlesOfAssociation() {
        final String json = "{\"articles_of_association\":{\"type\":\"articles_of_association\","
                + "\"front\":\"file_6lbss42ezvoufcb2beo76rvwly\"}}";

        final OnboardSubEntityDocuments documents = serializer.fromJson(json, OnboardSubEntityDocuments.class);

        assertEquals(ArticlesOfAssociationType.ARTICLES_OF_ASSOCIATION, documents.getArticlesOfAssociation().getType());
        assertEquals("file_6lbss42ezvoufcb2beo76rvwly", documents.getArticlesOfAssociation().getFront());
    }

    // ------------------------------------------------------------------------
    // Representative documents (company.representatives[].documents)
    // The EEA Sole Trader (3.0) keys and the company-variant certified authorised
    // signatory. The representative object is strict on the API, so the exact key
    // set matters.
    // ------------------------------------------------------------------------

    // Regression: EEA Sole Trader (3.0) needs proof_of_residential_address and proof_of_registration
    // on the representative, with bank_verification alone at the top level. Neither could be
    // expressed on the representative before.
    @Test
    void shouldSerializeEeaSoleTraderRepresentativeDocuments() {
        final OnboardEntityRequest request = OnboardEntityRequest.builder()
                .reference("ref_sole_trader")
                .company(Company.builder()
                        .businessType(BusinessType.INDIVIDUAL_OR_SOLE_PROPRIETORSHIP)
                        .representatives(Collections.singletonList(Representative.builder()
                                .individual(RepresentativeIndividual.builder().firstName("Jane").lastName("Doe").build())
                                .roles(Collections.singletonList(EntityRoles.UBO))
                                .documents(eeaSoleTraderRepresentativeDocuments())
                                .build()))
                        .build())
                .documents(OnboardSubEntityDocuments.builder()
                        .bankVerification(BankVerification.builder()
                                .type(BankVerificationType.BANK_STATEMENT)
                                .front("file_bankverificationaaaaaaaaaa")
                                .build())
                        .build())
                .build();

        final String json = serializer.toJson(request);
        final JsonObject body = JsonParser.parseString(json).getAsJsonObject();

        assertEquals(JsonParser.parseString("{"
                        + "\"identity_verification\":{\"type\":\"passport\",\"front\":\"file_identityverificationaaaaaa\"},"
                        + "\"proof_of_residential_address\":{\"type\":\"proof_of_address\",\"front\":\"file_proofofresidentialaddressa\"},"
                        + "\"proof_of_registration\":{\"type\":\"extract_from_trade_register\",\"front\":\"file_proofofregistrationaaaaaaa\"}}"),
                body.getAsJsonObject("company").getAsJsonArray("representatives").get(0)
                        .getAsJsonObject().get("documents"), json);
        assertEquals(Collections.singleton("bank_verification"), body.getAsJsonObject("documents").keySet(), json);
        // Key-level check on the raw body, so a naming-policy change cannot pass silently.
        assertTrue(json.contains("\"proof_of_residential_address\":{"), json);
        assertTrue(json.contains("\"proof_of_registration\":{"), json);
    }

    @Test
    void shouldRoundTripRepresentativeDocuments() {
        final OnboardSubEntityDocuments original = eeaSoleTraderRepresentativeDocuments();

        final OnboardSubEntityDocuments roundTripped =
                serializer.fromJson(serializer.toJson(original), OnboardSubEntityDocuments.class);

        assertEquals(original, roundTripped);
    }

    @Test
    void shouldDeserializeProofOfRegistrationOtherType() {
        final OnboardSubEntityDocuments documents = serializer.fromJson(
                "{\"proof_of_registration\":{\"type\":\"other\",\"front\":\"file_proofofregistrationaaaaaaa\"}}",
                OnboardSubEntityDocuments.class);

        assertEquals(ProofOfRegistrationType.OTHER, documents.getProofOfRegistration().getType());
    }

    @Test
    void shouldSerializeCertifiedAuthorisedSignatoryWithTypeAndFrontOnly() {
        final OnboardSubEntityDocuments documents = OnboardSubEntityDocuments.builder()
                .certifiedAuthorisedSignatory(CertifiedAuthorisedSignatory.builder()
                        .type(CertifiedAuthorisedSignatoryType.POWER_OF_ATTORNEY)
                        .front("file_signatoryaaaaaaaaaaaaaaaaa")
                        .build())
                .build();

        assertEquals(JsonParser.parseString("{\"certified_authorised_signatory\":"
                        + "{\"type\":\"power_of_attorney\",\"front\":\"file_signatoryaaaaaaaaaaaaaaaaa\"}}"),
                JsonParser.parseString(serializer.toJson(documents)));
    }

    // ------------------------------------------------------------------------
    // Company and tax verification
    // Regression: CompanyVerification carried TaxVerificationType and TaxVerification
    // carried CompanyVerificationType, so incorporation_document (required on the
    // EEA and GB Company Full variants) and ein_letter could not be sent.
    // ------------------------------------------------------------------------

    @Test
    void shouldSendCompanyAndTaxVerificationTypesUnderTheirOwnKeys() {
        final OnboardSubEntityDocuments documents = OnboardSubEntityDocuments.builder()
                .companyVerification(CompanyVerification.builder()
                        .type(CompanyVerificationType.INCORPORATION_DOCUMENT)
                        .front("file_aaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .build())
                .taxVerification(TaxVerification.builder()
                        .type(TaxVerificationType.EIN_LETTER)
                        .front("file_aaaaaaaaaaaaaaaaaaaaaaaaaa")
                        .build())
                .build();

        final JsonObject json = JsonParser.parseString(serializer.toJson(documents)).getAsJsonObject();

        assertEquals("incorporation_document",
                json.getAsJsonObject("company_verification").get("type").getAsString());
        assertEquals("ein_letter", json.getAsJsonObject("tax_verification").get("type").getAsString());
    }

    // ------------------------------------------------------------------------
    // Every field of OnboardSubEntityDocuments
    // Exact JSON for all 16 fields, so a naming-policy change on any key cannot pass
    // silently, then a full round trip.
    // ------------------------------------------------------------------------

    @Test
    void shouldSerializeAndRoundTripEveryDocumentsField() {
        final String file = "file_aaaaaaaaaaaaaaaaaaaaaaaaaa";
        final OnboardSubEntityDocuments documents = OnboardSubEntityDocuments.builder()
                .identityVerification(Document.builder().type(DocumentType.PASSPORT).front(file).back(file).build())
                .companyVerification(CompanyVerification.builder()
                        .type(CompanyVerificationType.INCORPORATION_DOCUMENT).front(file).build())
                .articlesOfAssociation(ArticlesOfAssociation.builder()
                        .type(ArticlesOfAssociationType.ARTICLES_OF_ASSOCIATION).front(file).build())
                .bankVerification(BankVerification.builder().type(BankVerificationType.BANK_STATEMENT).front(file).build())
                .shareholderStructure(ShareholderStructure.builder()
                        .type(ShareholderStructureType.CERTIFIED_SHAREHOLDER_STRUCTURE).front(file).build())
                .proofOfLegality(ProofOfLegality.builder().type(ProofOfLegalityType.PROOF_OF_LEGALITY).front(file).build())
                .proofOfPrincipalAddress(ProofOfPrincipalAddress.builder()
                        .type(ProofOfPrincipalAddressType.PROOF_OF_ADDRESS).front(file).build())
                .additionalDocument1(AdditionalDocument.builder().front(file).build())
                .additionalDocument2(AdditionalDocument.builder().front(file).build())
                .additionalDocument3(AdditionalDocument.builder().front(file).build())
                .taxVerification(TaxVerification.builder().type(TaxVerificationType.EIN_LETTER).front(file).build())
                .financialVerification(FinancialVerification.builder()
                        .type(FinancialVerificationType.FINANCIAL_STATEMENT).front(file).build())
                .financialStatements(FinancialStatements.builder()
                        .type(FinancialStatementsType.FINANCIAL_STATEMENTS).front(file).build())
                .certifiedAuthorisedSignatory(CertifiedAuthorisedSignatory.builder()
                        .type(CertifiedAuthorisedSignatoryType.POWER_OF_ATTORNEY).front(file).build())
                .proofOfResidentialAddress(ProofOfResidentialAddress.builder()
                        .type(ProofOfResidentialAddressType.PROOF_OF_ADDRESS).front(file).build())
                .proofOfRegistration(ProofOfRegistration.builder()
                        .type(ProofOfRegistrationType.EXTRACT_FROM_TRADE_REGISTER).front(file).build())
                .build();

        final String json = serializer.toJson(documents);

        assertEquals(JsonParser.parseString("{"
                + "\"identity_verification\":{\"type\":\"passport\",\"front\":\"" + file + "\",\"back\":\"" + file + "\"},"
                + "\"company_verification\":{\"type\":\"incorporation_document\",\"front\":\"" + file + "\"},"
                + "\"articles_of_association\":{\"type\":\"articles_of_association\",\"front\":\"" + file + "\"},"
                + "\"bank_verification\":{\"type\":\"bank_statement\",\"front\":\"" + file + "\"},"
                + "\"shareholder_structure\":{\"type\":\"certified_shareholder_structure\",\"front\":\"" + file + "\"},"
                + "\"proof_of_legality\":{\"type\":\"proof_of_legality\",\"front\":\"" + file + "\"},"
                + "\"proof_of_principal_address\":{\"type\":\"proof_of_address\",\"front\":\"" + file + "\"},"
                + "\"additional_document1\":{\"front\":\"" + file + "\"},"
                + "\"additional_document2\":{\"front\":\"" + file + "\"},"
                + "\"additional_document3\":{\"front\":\"" + file + "\"},"
                + "\"tax_verification\":{\"type\":\"ein_letter\",\"front\":\"" + file + "\"},"
                + "\"financial_verification\":{\"type\":\"financial_statement\",\"front\":\"" + file + "\"},"
                + "\"financial_statements\":{\"type\":\"financial_statements\",\"front\":\"" + file + "\"},"
                + "\"certified_authorised_signatory\":{\"type\":\"power_of_attorney\",\"front\":\"" + file + "\"},"
                + "\"proof_of_residential_address\":{\"type\":\"proof_of_address\",\"front\":\"" + file + "\"},"
                + "\"proof_of_registration\":{\"type\":\"extract_from_trade_register\",\"front\":\"" + file + "\"}}"),
                JsonParser.parseString(json), json);
        assertEquals(documents, serializer.fromJson(json, OnboardSubEntityDocuments.class));
    }

    private static OnboardSubEntityDocuments eeaSoleTraderRepresentativeDocuments() {
        return OnboardSubEntityDocuments.builder()
                .identityVerification(Document.builder()
                        .type(DocumentType.PASSPORT)
                        .front("file_identityverificationaaaaaa")
                        .build())
                .proofOfResidentialAddress(ProofOfResidentialAddress.builder()
                        .type(ProofOfResidentialAddressType.PROOF_OF_ADDRESS)
                        .front("file_proofofresidentialaddressa")
                        .build())
                .proofOfRegistration(ProofOfRegistration.builder()
                        .type(ProofOfRegistrationType.EXTRACT_FROM_TRADE_REGISTER)
                        .front("file_proofofregistrationaaaaaaa")
                        .build())
                .build();
    }
}
