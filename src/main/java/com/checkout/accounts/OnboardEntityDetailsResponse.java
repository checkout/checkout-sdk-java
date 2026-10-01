package com.checkout.accounts;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * The details of a sub-entity, as returned by GET /accounts/entities/{id}.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class OnboardEntityDetailsResponse extends Resource {

    /**
     * The ID of the sub-entity.
     */
    private String id;

    /**
     * A unique reference you can later use to identify the sub-entity.
     */
    private String reference;

    /**
     * The onboarding status of the sub-entity.
     */
    private OnboardingStatus status;

    /**
     * The capabilities of the entity.
     */
    private Capabilities capabilities;

    /**
     * List of requirements due in order to be onboarded.
     */
    private List<RequirementsDue> requirementsDue;

    /**
     * Contact details of this sub-entity.
     */
    private ContactDetails contactDetails;

    /**
     * Information about the profile of the sub-entity, primarily regarding the products and services
     * offered.
     */
    private Profile profile;

    /**
     * Information about the company represented by the sub-entity (company and v3.0 sole trader
     * variants).
     */
    private Company company;

    /**
     * Information about the individual represented by the sub-entity (v2.0 sole trader variants).
     */
    private Individual individual;

    /**
     * The sub-entity's payment instruments.
     */
    private List<Instrument> instruments;

    /**
     * The sub-entity's expected processing (Accounts API v3.0). Amounts are {@code Long}; see
     * {@link EntityProcessingDetails}.
     */
    private EntityProcessingDetails processingDetails;

    /**
     * The top-level documents used to support the verification of the sub-entity's details.
     * Representative documents are on {@link Representative}, under {@code company}.
     */
    private OnboardSubEntityDocuments documents;

}
