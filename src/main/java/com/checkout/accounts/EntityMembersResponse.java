package com.checkout.accounts;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.checkout.common.Resource;
import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class EntityMembersResponse extends Resource {

    private List<EntityMemberResponse> data;

}