package com.checkout.identities.amlscreening.requests;

import com.checkout.common.Resource;
import com.checkout.identities.entities.SearchParameters;

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

    private String applicantId;

    private SearchParameters searchParameters;

    private Boolean monitored;

}
