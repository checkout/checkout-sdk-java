package com.checkout.accounts;

import com.checkout.GsonSerializer;
import org.junit.jupiter.api.Test;

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
}
