package com.checkout.identities.amlscreening.requests;

import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Request for creating AML screening
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class AmlScreeningRequest extends Resource {

    @SerializedName("applicant_id")
    private String applicantId;

    @SerializedName("search_parameters")
    private SearchParameters searchParameters;

    @SerializedName("monitored")
    private Boolean monitored;

    @Data
    @Builder
    @EqualsAndHashCode(callSuper = false)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchParameters extends Resource {

        @SerializedName("configuration_identifier")
        private String configurationIdentifier;
    }
}