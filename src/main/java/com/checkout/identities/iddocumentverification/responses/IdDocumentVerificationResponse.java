package com.checkout.identities.iddocumentverification.responses;

import com.checkout.common.Resource;
import com.checkout.identities.faceauthentications.responses.BaseIdentityResponse;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Response for ID document verification operations
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IdDocumentVerificationResponse extends BaseIdentityResponse<IdDocumentVerificationStatus> {

    @SerializedName("user_journey_id")
    private String userJourneyId;

    @SerializedName("applicant_id")
    private String applicantId;

    @SerializedName("declared_data")
    private DeclaredData declaredData;

    @SerializedName("document")
    private Document document;

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class DeclaredData extends Resource {

        @SerializedName("name")
        private String name;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class Document extends Resource {

        @SerializedName("document_type")
        private String documentType;

        @SerializedName("document_issuing_country")
        private String documentIssuingCountry;

        @SerializedName("front_image_signed_url")
        private String frontImageSignedUrl;

        @SerializedName("full_name")
        private String fullName;

        @SerializedName("birth_date")
        private String birthDate;

        @SerializedName("first_names")
        private String firstNames;

        @SerializedName("last_name")
        private String lastName;

        @SerializedName("last_name_at_birth")
        private String lastNameAtBirth;

        @SerializedName("birth_place")
        private String birthPlace;

        @SerializedName("nationality")
        private String nationality;

        @SerializedName("gender")
        private String gender;

        @SerializedName("personal_number")
        private String personalNumber;

        @SerializedName("tax_identification_number")
        private String taxIdentificationNumber;

        @SerializedName("document_number")
        private String documentNumber;

        @SerializedName("document_expiry_date")
        private String documentExpiryDate;

        @SerializedName("document_issue_date")
        private String documentIssueDate;

        @SerializedName("document_issue_place")
        private String documentIssuePlace;

        @SerializedName("document_mrz")
        private String documentMrz;

        @SerializedName("back_image_signed_url")
        private String backImageSignedUrl;

        @SerializedName("signature_image_signed_url")
        private String signatureImageSignedUrl;
    }
}