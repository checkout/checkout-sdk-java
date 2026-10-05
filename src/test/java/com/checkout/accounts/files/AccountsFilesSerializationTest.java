package com.checkout.accounts.files;

import com.checkout.GsonSerializer;
import com.checkout.accounts.files.entities.FilePurpose;
import com.checkout.accounts.files.request.FileUploadRequest;
import com.checkout.accounts.files.response.FileDetailsResponse;
import com.checkout.accounts.files.response.FileUploadResponse;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountsFilesSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    // ------------------------------------------------------------------------
    // FileUploadResponse (PlatformsFileUploadResponse)
    // Built from the spec's per-field example values, including the upload link.
    // ------------------------------------------------------------------------

    @Test
    void shouldDeserializeFileUploadResponseSwaggerExample() {
        final String upload = "https://s3.eu-west-1.amazonaws.com/mp-files-api-staging-prod/ent_ociwguf5a5fe3ndmpnvpnwsi3e/"
                + "file_6lbss42ezvoufcb2beo76rvwly?AWSAccessKeyId=ASIX4BFJOBCQFLAMPKU3&Expires=1661355993"
                + "&x-amz-security-token=some_token";
        final String self = "https://files.checkout.com/files/file_6lbss42ezvoufcb2beo76rvwly";

        final FileUploadResponse response = serializer.fromJson("{"
                        + "\"id\":\"file_6lbss42ezvoufcb2beo76rvwly\","
                        + "\"maximum_size_in_bytes\":4194304,"
                        + "\"document_types_for_purpose\":[\"image/jpeg\",\"image/png\",\"image/jpg\"],"
                        + "\"_links\":{\"upload\":{\"href\":\"" + upload + "\"},\"self\":{\"href\":\"" + self + "\"}}}",
                FileUploadResponse.class);

        assertEquals("file_6lbss42ezvoufcb2beo76rvwly", response.getId());
        assertEquals(Long.valueOf(4194304L), response.getMaximumSizeInBytes());
        assertEquals(Arrays.asList("image/jpeg", "image/png", "image/jpg"), response.getDocumentTypesForPurpose());
        assertEquals(2, response.getLinks().size());
        assertEquals(upload, response.getLink("upload").getHref());
        assertEquals(self, response.getSelfLink().getHref());
    }

    // ------------------------------------------------------------------------
    // FileDetailsResponse (PlatformsFileRetrieveResponse)
    // uploaded_on uses the spec's seven fractional digits with a +00:00 offset.
    // ------------------------------------------------------------------------

    @Test
    void shouldDeserializeFileDetailsResponseSwaggerExample() {
        final String download = "https://s3.eu-west-1.amazonaws.com/mp-files-api-clean-prod/ent_ociwguf5a5fe3ndmpnvpnwsi3e/"
                + "file_6lbss42ezvoufcb2beo76rvwly?X-Amz-Expires=3600&x-amz-security-token=some_token";
        final String self = "https://files.checkout.com/files/file_6lbss42ezvoufcb2beo76rvwly";

        final FileDetailsResponse response = serializer.fromJson("{"
                        + "\"id\":\"file_6lbss42ezvoufcb2beo76rvwly\","
                        + "\"status\":\"invalid\","
                        + "\"status_reasons\":[\"InvalidMimeType\"],"
                        + "\"size\":1024,"
                        + "\"mime_type\":\"application/pdf\","
                        + "\"uploaded_on\":\"2020-12-01T15:01:01.0000000+00:00\","
                        + "\"purpose\":\"identity_verification\","
                        + "\"_links\":{\"download\":{\"href\":\"" + download + "\"},\"self\":{\"href\":\"" + self + "\"}}}",
                FileDetailsResponse.class);

        assertEquals("file_6lbss42ezvoufcb2beo76rvwly", response.getId());
        assertEquals("invalid", response.getStatus());
        assertEquals(Collections.singletonList("InvalidMimeType"), response.getStatusReasons());
        assertEquals(Long.valueOf(1024L), response.getSize());
        assertEquals("application/pdf", response.getMimeType());
        assertEquals(Instant.parse("2020-12-01T15:01:01Z"), response.getUploadedOn());
        assertEquals(FilePurpose.IDENTITY_VERIFICATION, response.getPurpose());
        assertEquals(2, response.getLinks().size());
        assertEquals(download, response.getLink("download").getHref());
        assertEquals(self, response.getSelfLink().getHref());
    }

    // ------------------------------------------------------------------------
    // FileUploadRequest and FilePurpose
    // ------------------------------------------------------------------------

    @Test
    void shouldSerializeFileUploadRequestPurposeOnly() {
        final FileUploadRequest request = FileUploadRequest.builder().purpose(FilePurpose.IDENTITY_VERIFICATION).build();

        assertEquals(JsonParser.parseString("{\"purpose\":\"identity_verification\"}"),
                JsonParser.parseString(serializer.toJson(request)));
    }

    @Test
    void shouldSerializeEveryFilePurposeWireValue() {
        final Map<FilePurpose, String> expected = new EnumMap<>(FilePurpose.class);
        expected.put(FilePurpose.ADDITIONAL_DOCUMENT, "additional_document");
        expected.put(FilePurpose.ARTICLES_OF_ASSOCIATION, "articles_of_association");
        expected.put(FilePurpose.BANK_VERIFICATION, "bank_verification");
        expected.put(FilePurpose.CERTIFIED_AUTHORISED_SIGNATORY, "certified_authorised_signatory");
        expected.put(FilePurpose.COMPANY_OWNERSHIP, "company_ownership");
        expected.put(FilePurpose.COMPANY_VERIFICATION, "company_verification");
        expected.put(FilePurpose.FINANCIAL_VERIFICATION, "financial_verification");
        expected.put(FilePurpose.IDENTITY_VERIFICATION, "identity_verification");
        expected.put(FilePurpose.PROOF_OF_LEGALITY, "proof_of_legality");
        expected.put(FilePurpose.PROOF_OF_PRINCIPAL_ADDRESS, "proof_of_principal_address");
        expected.put(FilePurpose.SHAREHOLDER_STRUCTURE, "shareholder_structure");
        expected.put(FilePurpose.TAX_VERIFICATION, "tax_verification");
        expected.put(FilePurpose.PROOF_OF_RESIDENTIAL_ADDRESS, "proof_of_residential_address");
        expected.put(FilePurpose.PROOF_OF_REGISTRATION, "proof_of_registration");
        expected.put(FilePurpose.DISPUTE_EVIDENCE, "dispute_evidence");

        assertEquals(FilePurpose.values().length, expected.size(), "every value must be asserted");
        expected.forEach((purpose, wire) -> {
            assertEquals("\"" + wire + "\"", serializer.toJson(purpose), purpose.name());
            assertEquals(purpose, serializer.fromJson("\"" + wire + "\"", FilePurpose.class), wire);
        });
    }
}
