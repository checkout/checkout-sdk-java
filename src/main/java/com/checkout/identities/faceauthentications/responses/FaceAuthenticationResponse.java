package com.checkout.identities.faceauthentications.responses;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Face authentication response
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
public class FaceAuthenticationResponse extends BaseVerificationResponse<FaceAuthenticationStatus> {

    /**
     * The details of the image of the applicant's face extracted from the video.
     */
    private Face face;

    /**
     * Face image details
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Face {
        /**
         * The URL to the face image.
         */
        @SerializedName("image_signed_url")
        private String imageSignedUrl;
    }
}