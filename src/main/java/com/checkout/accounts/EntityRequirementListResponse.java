package com.checkout.accounts;

import com.checkout.common.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * The list of pending requirements for a sub-entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class EntityRequirementListResponse extends Resource {

    /**
     * The list of pending requirements for the sub-entity. Empty when no requirements are outstanding.
     * [Optional]
     */
    private List<EntityRequirementListItem> data;
}
