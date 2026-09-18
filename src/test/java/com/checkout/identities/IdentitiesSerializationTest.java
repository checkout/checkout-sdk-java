package com.checkout.identities;

import com.checkout.GsonSerializer;
import com.checkout.common.CountryCode;
import com.checkout.identities.addressdocumentverification.responses.AddressDocumentVerificationReportResponse;
import com.checkout.identities.addressdocumentverification.responses.AddressDocumentVerificationResponse;
import com.checkout.identities.entities.ApplicantSessionInformation;
import com.checkout.identities.entities.Certification;
import com.checkout.identities.entities.CertificationType;
import com.checkout.identities.entities.ClientInformation;
import com.checkout.identities.entities.DeclaredData;
import com.checkout.identities.entities.DiatfCertificationData;
import com.checkout.identities.entities.DocumentDetails;
import com.checkout.identities.entities.DocumentType;
import com.checkout.identities.entities.Gpg45Profile;
import com.checkout.identities.entities.IdentityDeclaredData;
import com.checkout.identities.entities.IdentityVerificationClientInformation;
import com.checkout.identities.entities.IdvAddress;
import com.checkout.identities.entities.InitialDevice;
import com.checkout.identities.entities.LevelOfConfidence;
import com.checkout.identities.entities.PhoneNumber;
import com.checkout.identities.entities.RiskLabel;
import com.checkout.identities.entities.SelectedDocument;
import com.checkout.identities.faceauthentications.requests.FaceAuthenticationAttemptRequest;
import com.checkout.identities.faceauthentications.responses.FaceAuthenticationAttemptResponse;
import com.checkout.identities.faceauthentications.responses.FaceAuthenticationResponse;
import com.checkout.identities.iddocumentverification.responses.IdDocumentVerificationReportResponse;
import com.checkout.identities.identityverification.requests.IdentityVerificationAttemptRequest;
import com.checkout.identities.identityverification.responses.IdentityVerificationAttemptResponse;
import com.checkout.identities.identityverification.responses.IdentityVerificationAttemptStatus;
import com.checkout.identities.identityverification.responses.IdentityVerificationReportResponse;
import com.checkout.identities.identityverification.responses.IdentityVerificationResponse;
import com.checkout.identities.identityverification.responses.IdentityVerificationStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * GSON serialization tests for the Identities domain types changed by swagger 2026-09-02.
 *
 * Covers the IdvPdf signed_url to pdf_report rename, the additive IDV and FAV verification fields,
 * the typed country codes, and the enum and IdvDocument additions that the swagger changelog does
 * not report.
 */
class IdentitiesSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    // ------------------------------------------------------------------------
    // Part C: IdvPdf signed_url replaced by pdf_report.
    // The previous spec declared both keys and the SDK modelled only signed_url,
    // so pdf_report never populated. signed_url is now gone from the spec.
    // ------------------------------------------------------------------------

    @Test
    void shouldDeserializePdfReportOnIdentityVerificationReportResponse() {
        final IdentityVerificationReportResponse response = serializer.fromJson(
                "{\"pdf_report\":\"https://example.com/report.pdf\"}",
                IdentityVerificationReportResponse.class);

        assertNotNull(response);
        assertEquals("https://example.com/report.pdf", response.getPdfReport());
    }

    @Test
    void shouldDeserializePdfReportOnAddressDocumentVerificationReportResponse() {
        final AddressDocumentVerificationReportResponse response = serializer.fromJson(
                "{\"pdf_report\":\"https://example.com/adv-report.pdf\"}",
                AddressDocumentVerificationReportResponse.class);

        assertNotNull(response);
        assertEquals("https://example.com/adv-report.pdf", response.getPdfReport());
    }

    @Test
    void shouldDeserializePdfReportOnIdDocumentVerificationReportResponse() {
        final IdDocumentVerificationReportResponse response = serializer.fromJson(
                "{\"pdf_report\":\"https://example.com/iddv-report.pdf\"}",
                IdDocumentVerificationReportResponse.class);

        assertNotNull(response);
        assertEquals("https://example.com/iddv-report.pdf", response.getPdfReport());
    }

    @Test
    void shouldNotMapTheRemovedSignedUrlKeyOnReportResponses() {
        final IdentityVerificationReportResponse response = serializer.fromJson(
                "{\"signed_url\":\"https://example.com/report.pdf\"}",
                IdentityVerificationReportResponse.class);

        assertNotNull(response);
        assertNull(response.getPdfReport());
    }

    @Test
    void shouldSerializePdfReportUsingTheSwaggerKey() {
        final IdentityVerificationReportResponse response = IdentityVerificationReportResponse.builder()
                .pdfReport("https://example.com/report.pdf")
                .build();

        final String json = serializer.toJson(response);

        assertTrue(json.contains("\"pdf_report\""));
        assertFalse(json.contains("\"signed_url\""));
    }

    // ------------------------------------------------------------------------
    // Part E1: risk_labels is now a typed enum, not a bare string list, and is
    // declared per response class rather than on a shared base.
    // ------------------------------------------------------------------------

    @ParameterizedTest
    @CsvSource({
            "MULTIPLE_FACES_DETECTED,multiple_faces_detected",
            "MCC_NOT_CONFIDENT,mcc_not_confident",
            "RISKY_DOCUMENT_FORMAT,risky_document_format"
    })
    void shouldRoundTripEachRiskLabelValue(final RiskLabel label, final String expected) {
        final IdentityVerificationResponse response = IdentityVerificationResponse.builder()
                .riskLabels(Collections.singletonList(label))
                .build();

        final String json = serializer.toJson(response);
        final IdentityVerificationResponse deserialized =
                serializer.fromJson(json, IdentityVerificationResponse.class);

        assertTrue(json.contains("\"" + expected + "\""));
        assertNotNull(deserialized.getRiskLabels());
        assertEquals(1, deserialized.getRiskLabels().size());
        assertEquals(label, deserialized.getRiskLabels().get(0));
    }

    @Test
    void shouldDeserializeRiskLabelsOnAddressDocumentVerificationResponse() {
        final AddressDocumentVerificationResponse response = serializer.fromJson(
                "{\"risk_labels\":[\"mcc_not_confident\",\"risky_document_format\"]}",
                AddressDocumentVerificationResponse.class);

        assertNotNull(response);
        assertEquals(2, response.getRiskLabels().size());
        assertEquals(RiskLabel.MCC_NOT_CONFIDENT, response.getRiskLabels().get(0));
        assertEquals(RiskLabel.RISKY_DOCUMENT_FORMAT, response.getRiskLabels().get(1));
    }

    @Test
    void shouldDeserializeRiskLabelsOnFaceAuthenticationResponse() {
        final FaceAuthenticationResponse response = serializer.fromJson(
                "{\"risk_labels\":[\"multiple_faces_detected\"]}",
                FaceAuthenticationResponse.class);

        assertNotNull(response);
        assertEquals(1, response.getRiskLabels().size());
        assertEquals(RiskLabel.MULTIPLE_FACES_DETECTED, response.getRiskLabels().get(0));
    }

    // ------------------------------------------------------------------------
    // Part E2: certifications and verification_policy_version, nested two deep.
    // ------------------------------------------------------------------------

    @Test
    void shouldDeserializeCertificationsAndVerificationPolicyVersion() {
        final String json = "{"
                + "\"verification_policy_version\":\"2.1\","
                + "\"certifications\":[{\"type\":\"diatf\",\"data\":{"
                + "  \"gpg45_profile\":\"M1C\","
                + "  \"level_of_confidence\":\"high\","
                + "  \"right_to_work\":\"GRANTED\"}}]"
                + "}";

        final IdentityVerificationResponse response =
                serializer.fromJson(json, IdentityVerificationResponse.class);

        assertNotNull(response);
        assertEquals("2.1", response.getVerificationPolicyVersion());
        assertNotNull(response.getCertifications());
        assertEquals(1, response.getCertifications().size());
        assertEquals(CertificationType.DIATF, response.getCertifications().get(0).getType());
        assertNotNull(response.getCertifications().get(0).getData());
        assertEquals(Gpg45Profile.M1C, response.getCertifications().get(0).getData().getGpg45Profile());
        assertEquals(LevelOfConfidence.HIGH, response.getCertifications().get(0).getData().getLevelOfConfidence());
        assertEquals("GRANTED", response.getCertifications().get(0).getData().getRightToWork());
    }

    @Test
    void shouldRoundTripCertifications() {
        final IdentityVerificationResponse original = IdentityVerificationResponse.builder()
                .verificationPolicyVersion("1.0")
                .certifications(Collections.singletonList(Certification.builder()
                        .type(CertificationType.DIATF)
                        .data(DiatfCertificationData.builder()
                                .gpg45Profile(Gpg45Profile.H1A)
                                .levelOfConfidence(LevelOfConfidence.MEDIUM)
                                .rightToWork("GRANTED")
                                .build())
                        .build()))
                .build();

        final String json = serializer.toJson(original);
        final IdentityVerificationResponse deserialized =
                serializer.fromJson(json, IdentityVerificationResponse.class);

        assertTrue(json.contains("\"verification_policy_version\":\"1.0\""));
        assertTrue(json.contains("\"gpg45_profile\":\"H1A\""));
        assertTrue(json.contains("\"level_of_confidence\":\"medium\""));
        assertEquals(Gpg45Profile.H1A, deserialized.getCertifications().get(0).getData().getGpg45Profile());
        assertEquals(LevelOfConfidence.MEDIUM, deserialized.getCertifications().get(0).getData().getLevelOfConfidence());
    }

    // ------------------------------------------------------------------------
    // Part E3: declared_data gains birth_date everywhere, plus phone_number,
    // email and address on identity verifications only.
    // ------------------------------------------------------------------------

    @Test
    void shouldRoundTripBirthDateOnDeclaredData() {
        final DeclaredData original = DeclaredData.builder()
                .name("Hannah Bret")
                .birthDate("1994-10-15")
                .build();

        final String json = serializer.toJson(original);
        final DeclaredData deserialized = serializer.fromJson(json, DeclaredData.class);

        assertTrue(json.contains("\"birth_date\":\"1994-10-15\""));
        assertEquals("Hannah Bret", deserialized.getName());
        assertEquals("1994-10-15", deserialized.getBirthDate());
    }

    @Test
    void shouldRoundTripAllIdentityDeclaredDataFields() {
        final IdentityDeclaredData original = IdentityDeclaredData.builder()
                .name("Hannah Bret")
                .birthDate("1994-10-15")
                .email("hannah.bret@example.com")
                .phoneNumber(PhoneNumber.builder().countryCode("+33").number("5555550102").build())
                .address(IdvAddress.builder()
                        .addressLine1("123 Main Street")
                        .addressLine2("Apt 4B")
                        .city("London")
                        .state("Greater London")
                        .zip("SW1A 1AA")
                        .country(CountryCode.GB)
                        .build())
                .build();

        final String json = serializer.toJson(original);
        final IdentityDeclaredData deserialized = serializer.fromJson(json, IdentityDeclaredData.class);

        assertTrue(json.contains("\"country_code\":\"+33\""));
        assertTrue(json.contains("\"address_line1\":\"123 Main Street\""));
        assertEquals("Hannah Bret", deserialized.getName());
        assertEquals("1994-10-15", deserialized.getBirthDate());
        assertEquals("hannah.bret@example.com", deserialized.getEmail());
        assertEquals("+33", deserialized.getPhoneNumber().getCountryCode());
        assertEquals("5555550102", deserialized.getPhoneNumber().getNumber());
        assertEquals("123 Main Street", deserialized.getAddress().getAddressLine1());
        assertEquals("Apt 4B", deserialized.getAddress().getAddressLine2());
        assertEquals("London", deserialized.getAddress().getCity());
        assertEquals("Greater London", deserialized.getAddress().getState());
        assertEquals("SW1A 1AA", deserialized.getAddress().getZip());
        assertEquals(CountryCode.GB, deserialized.getAddress().getCountry());
    }

    // ------------------------------------------------------------------------
    // Part E5: phone_number on the IDV and FAV attempt request and response.
    // ------------------------------------------------------------------------

    @Test
    void shouldRoundTripPhoneNumberOnIdentityVerificationAttemptRequest() {
        final IdentityVerificationAttemptRequest original = IdentityVerificationAttemptRequest.builder()
                .redirectUrl("https://example.com/redirect")
                .phoneNumber(PhoneNumber.builder().countryCode("+33").number("5555550102").build())
                .build();

        final String json = serializer.toJson(original);
        final IdentityVerificationAttemptRequest deserialized =
                serializer.fromJson(json, IdentityVerificationAttemptRequest.class);

        assertTrue(json.contains("\"phone_number\""));
        assertTrue(json.contains("\"number\":\"5555550102\""));
        assertEquals("+33", deserialized.getPhoneNumber().getCountryCode());
    }

    @Test
    void shouldRoundTripPhoneNumberOnFaceAuthenticationAttemptRequest() {
        final FaceAuthenticationAttemptRequest original = FaceAuthenticationAttemptRequest.builder()
                .redirectUrl("https://example.com/redirect")
                .phoneNumber(PhoneNumber.builder().countryCode("+44").number("7700900000").build())
                .build();

        final String json = serializer.toJson(original);
        final FaceAuthenticationAttemptRequest deserialized =
                serializer.fromJson(json, FaceAuthenticationAttemptRequest.class);

        assertTrue(json.contains("\"phone_number\""));
        assertEquals("7700900000", deserialized.getPhoneNumber().getNumber());
    }

    @Test
    void shouldDeserializePhoneNumberOnIdentityVerificationAttemptResponse() {
        final IdentityVerificationAttemptResponse response = serializer.fromJson(
                "{\"phone_number\":{\"country_code\":\"+33\",\"number\":\"5555550102\"}}",
                IdentityVerificationAttemptResponse.class);

        assertNotNull(response);
        assertEquals("+33", response.getPhoneNumber().getCountryCode());
        assertEquals("5555550102", response.getPhoneNumber().getNumber());
    }

    @Test
    void shouldDeserializePhoneNumberOnFaceAuthenticationAttemptResponse() {
        final FaceAuthenticationAttemptResponse response = serializer.fromJson(
                "{\"phone_number\":{\"country_code\":\"+44\",\"number\":\"7700900000\"}}",
                FaceAuthenticationAttemptResponse.class);

        assertNotNull(response);
        assertEquals("+44", response.getPhoneNumber().getCountryCode());
    }

    // ------------------------------------------------------------------------
    // Part E6: applicant_session_information gains number_of_sessions,
    // user_agent and initial_device.
    // ------------------------------------------------------------------------

    @Test
    void shouldRoundTripAllApplicantSessionInformationFields() {
        final ApplicantSessionInformation original = ApplicantSessionInformation.builder()
                .ipAddress("123.4.5.6")
                .numberOfSessions(3)
                .userAgent("Mozilla/5.0 (Linux; Android 8.0.0; SM-G960F Build/R16NW)")
                .initialDevice(InitialDevice.MOBILE)
                .selectedDocuments(Collections.singletonList(SelectedDocument.builder()
                        .country(CountryCode.GB)
                        .documentType(DocumentType.PASSPORT)
                        .build()))
                .build();

        final String json = serializer.toJson(original);
        final ApplicantSessionInformation deserialized =
                serializer.fromJson(json, ApplicantSessionInformation.class);

        assertTrue(json.contains("\"number_of_sessions\":3"));
        assertTrue(json.contains("\"user_agent\""));
        assertTrue(json.contains("\"initial_device\":\"mobile\""));
        assertEquals("123.4.5.6", deserialized.getIpAddress());
        assertEquals(3, deserialized.getNumberOfSessions());
        assertTrue(deserialized.getUserAgent().startsWith("Mozilla/5.0"));
        assertEquals(InitialDevice.MOBILE, deserialized.getInitialDevice());
        assertEquals(1, deserialized.getSelectedDocuments().size());
        assertEquals(CountryCode.GB, deserialized.getSelectedDocuments().get(0).getCountry());
    }

    @ParameterizedTest
    @CsvSource({"DESKTOP,desktop", "MOBILE,mobile"})
    void shouldSerializeEachInitialDeviceValue(final InitialDevice device, final String expected) {
        final ApplicantSessionInformation session =
                ApplicantSessionInformation.builder().initialDevice(device).build();

        assertTrue(serializer.toJson(session).contains("\"initial_device\":\"" + expected + "\""));
    }

    // ------------------------------------------------------------------------
    // Part E7: client_information forked. The identity verification variant
    // gains two fields; the face authentication variant does not.
    // ------------------------------------------------------------------------

    @Test
    void shouldRoundTripAllIdentityVerificationClientInformationFields() {
        final IdentityVerificationClientInformation original = IdentityVerificationClientInformation.builder()
                .preSelectedResidenceCountry(CountryCode.FR)
                .preSelectedLanguage("en-US")
                .preSelectedDocumentIssuingCountry(CountryCode.FR)
                .preSelectedDocumentType(DocumentType.PASSPORT)
                .build();

        final String json = serializer.toJson(original);
        final IdentityVerificationClientInformation deserialized =
                serializer.fromJson(json, IdentityVerificationClientInformation.class);

        assertTrue(json.contains("\"pre_selected_document_issuing_country\":\"FR\""));
        assertTrue(json.contains("\"pre_selected_document_type\":\"Passport\""));
        assertEquals(CountryCode.FR, deserialized.getPreSelectedResidenceCountry());
        assertEquals("en-US", deserialized.getPreSelectedLanguage());
        assertEquals(CountryCode.FR, deserialized.getPreSelectedDocumentIssuingCountry());
        assertEquals(DocumentType.PASSPORT, deserialized.getPreSelectedDocumentType());
    }

    @Test
    void shouldNotCarryIdentityOnlyFieldsOnTheFaceAuthenticationClientInformation() {
        final ClientInformation faceAuthenticationClientInformation = ClientInformation.builder()
                .preSelectedResidenceCountry(CountryCode.FR)
                .preSelectedLanguage("en-US")
                .build();

        final String json = serializer.toJson(faceAuthenticationClientInformation);

        assertFalse(json.contains("pre_selected_document_issuing_country"));
        assertFalse(json.contains("pre_selected_document_type"));
    }

    // ------------------------------------------------------------------------
    // Part F M2: document_type gained Other, Travel Document and Visa.
    // ------------------------------------------------------------------------

    @ParameterizedTest
    @CsvSource({
            "DRIVING_LICENCE,Driving licence",
            "ID,ID",
            "OTHER,Other",
            "PASSPORT,Passport",
            "RESIDENCE_PERMIT,Residence Permit",
            "TRAVEL_DOCUMENT,Travel Document",
            "VISA,Visa"
    })
    void shouldRoundTripEachDocumentTypeValue(final DocumentType type, final String expected) {
        final SelectedDocument original = SelectedDocument.builder().documentType(type).build();

        final String json = serializer.toJson(original);
        final SelectedDocument deserialized = serializer.fromJson(json, SelectedDocument.class);

        assertTrue(json.contains("\"document_type\":\"" + expected + "\""));
        assertEquals(type, deserialized.getDocumentType());
    }

    // ------------------------------------------------------------------------
    // Part F M3 and M4: new status values. Java keeps two copies of the shared
    // IdvAttemptStatuses enum, so terminated lands on both.
    // ------------------------------------------------------------------------

    @Test
    void shouldRoundTripTheTerminatedAttemptStatus() {
        final IdentityVerificationAttemptResponse response = serializer.fromJson(
                "{\"status\":\"terminated\"}", IdentityVerificationAttemptResponse.class);

        assertNotNull(response);
        assertEquals(IdentityVerificationAttemptStatus.TERMINATED, response.getStatus());
        assertTrue(serializer.toJson(response).contains("\"status\":\"terminated\""));
    }

    @Test
    void shouldRoundTripTheCreatedVerificationStatus() {
        final IdentityVerificationResponse response = serializer.fromJson(
                "{\"status\":\"created\"}", IdentityVerificationResponse.class);

        assertNotNull(response);
        assertEquals(IdentityVerificationStatus.CREATED, response.getStatus());
        assertTrue(serializer.toJson(response).contains("\"status\":\"created\""));
    }

    // ------------------------------------------------------------------------
    // Part F M5: IdvDocument gained address and four permit fields.
    // ------------------------------------------------------------------------

    @Test
    void shouldRoundTripTheNewDocumentDetailsFields() {
        final DocumentDetails original = DocumentDetails.builder()
                .documentType(DocumentType.RESIDENCE_PERMIT)
                .documentIssuingCountry(CountryCode.GB)
                .frontImageSignedUrl("https://example.com/front.png")
                .address("123 Main Street, London, SW1A 1AA")
                .permitObtainingDate("2020-01-15")
                .permitExpiryDate("2030-01-14")
                .permitTypeDetailed("Indefinite leave to remain")
                .permitTypeRemarks("No work restrictions")
                .build();

        final String json = serializer.toJson(original);
        final DocumentDetails deserialized = serializer.fromJson(json, DocumentDetails.class);

        assertTrue(json.contains("\"address\":\"123 Main Street, London, SW1A 1AA\""));
        assertTrue(json.contains("\"permit_obtaining_date\":\"2020-01-15\""));
        assertTrue(json.contains("\"permit_expiry_date\":\"2030-01-14\""));
        assertTrue(json.contains("\"permit_type_detailed\":\"Indefinite leave to remain\""));
        assertTrue(json.contains("\"permit_type_remarks\":\"No work restrictions\""));
        assertEquals("123 Main Street, London, SW1A 1AA", deserialized.getAddress());
        assertEquals("2020-01-15", deserialized.getPermitObtainingDate());
        assertEquals("2030-01-14", deserialized.getPermitExpiryDate());
        assertEquals("Indefinite leave to remain", deserialized.getPermitTypeDetailed());
        assertEquals("No work restrictions", deserialized.getPermitTypeRemarks());
        assertEquals(DocumentType.RESIDENCE_PERMIT, deserialized.getDocumentType());
    }

    // ------------------------------------------------------------------------
    // D3: country codes are typed. The phone prefix is not a country code.
    // ------------------------------------------------------------------------

    @Test
    void shouldSerializeCountryCodesToBareIsoAlpha2Values() {
        final DocumentDetails doc = DocumentDetails.builder()
                .documentIssuingCountry(CountryCode.GB)
                .nationality(CountryCode.FR)
                .build();

        final String json = serializer.toJson(doc);

        assertTrue(json.contains("\"document_issuing_country\":\"GB\""));
        assertTrue(json.contains("\"nationality\":\"FR\""));
    }

    @Test
    void shouldRoundTripTypedCountryCodes() {
        final DocumentDetails doc = serializer.fromJson(
                "{\"document_issuing_country\":\"GB\",\"nationality\":\"FR\"}", DocumentDetails.class);

        assertEquals(CountryCode.GB, doc.getDocumentIssuingCountry());
        assertEquals(CountryCode.FR, doc.getNationality());
    }

    /**
     * The tolerant behaviour that makes typing these fields safe: an unrecognised code
     * deserializes to null rather than throwing or silently becoming the first enum constant, and
     * it does not disturb sibling fields.
     */
    @Test
    void shouldReadAnUnknownCountryAsNullWithoutDisturbingSiblings() {
        final DocumentDetails doc = serializer.fromJson(
                "{\"nationality\":\"ZZ\",\"document_issuing_country\":\"GB\"}", DocumentDetails.class);

        assertNotNull(doc);
        assertNull(doc.getNationality());
        assertEquals(CountryCode.GB, doc.getDocumentIssuingCountry());
    }

    /**
     * CountryCode declares an alpha-3 alternate for every value, so a three-letter code resolves.
     * This is a Java-only capability; the .NET enum accepts alpha-2 only.
     */
    @Test
    void shouldResolveTheAlphaThreeAlternate() {
        final DocumentDetails doc = serializer.fromJson("{\"nationality\":\"GBR\"}", DocumentDetails.class);

        assertEquals(CountryCode.GB, doc.getNationality());
    }

    /**
     * Pins a known limitation rather than asserting desired behaviour. The spec pattern for
     * IdvDocument.nationality and document_issuing_country is ^[A-Za-z]{2}$, so a lowercase code is
     * valid per the specification, but CountryCode declares only uppercase values and uppercase
     * alpha-3 alternates, so a lowercase code reads as null. Before this row these two fields were
     * plain Strings and would have carried the raw value through. Reported internally; if the API
     * is confirmed to emit lowercase codes, CountryCode needs lowercase alternates, which is a
     * change to a type shared across the whole SDK.
     */
    @Test
    void shouldCurrentlyReadALowercaseCountryAsNull() {
        final DocumentDetails doc = serializer.fromJson("{\"nationality\":\"gb\"}", DocumentDetails.class);

        assertNull(doc.getNationality());
    }

    @Test
    void shouldKeepThePhonePrefixAsAStringNotACountryCode() {
        // IdvPhoneNumber.country_code is a dialling prefix (pattern ^\+(\d+)$, for example +33),
        // not an ISO country code, so it stays a String.
        final PhoneNumber phone = PhoneNumber.builder().countryCode("+33").number("5555550102").build();

        assertTrue(serializer.toJson(phone).contains("\"country_code\":\"+33\""));
    }

    // ------------------------------------------------------------------------
    // The attempts pagination query serializes to the exact skip and limit keys.
    // ------------------------------------------------------------------------

    @Test
    void shouldSerializeAttemptsQueryFilterToTheSwaggerQueryKeys() {
        final com.checkout.identities.entities.AttemptsQueryFilter query =
                com.checkout.identities.entities.AttemptsQueryFilter.builder().skip(6).limit(5).build();

        final String json = serializer.toJson(query);

        assertTrue(json.contains("\"skip\":6"));
        assertTrue(json.contains("\"limit\":5"));
    }
}
