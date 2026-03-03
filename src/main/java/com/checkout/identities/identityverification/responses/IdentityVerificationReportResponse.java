package com.checkout.identities.identityverification.responses;

import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

/**
 * Response for identity verification report download operations
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IdentityVerificationReportResponse extends Resource {

    @SerializedName("id")
    private String id;

    @SerializedName("url")
    private String url;

    @SerializedName("created_on")
    private Instant createdOn;

    @SerializedName("expires_on")
    private Instant expiresOn;

    @SerializedName("is_downloadable")
    private Boolean isDownloadable;
}