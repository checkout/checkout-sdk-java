package com.checkout.identities.amlscreening.responses;

import com.checkout.common.Resource;
import com.checkout.identities.amlscreening.AmlScreeningStatus;
import com.checkout.identities.faceauthentications.responses.BaseIdentityResponse;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Response for AML screening operations
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AmlScreeningResponse extends BaseIdentityResponse<AmlScreeningStatus> {

    @SerializedName("applicant_id")
    private String applicantId;

    @SerializedName("search_parameters")
    private SearchParameters searchParameters;

    @SerializedName("monitored")
    private Boolean monitored;

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class SearchParameters extends Resource {

        @SerializedName("configuration_identifier")
        private String configurationIdentifier;
    }
}