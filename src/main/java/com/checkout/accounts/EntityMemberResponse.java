package com.checkout.accounts;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.checkout.common.Resource;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class EntityMemberResponse extends Resource {

    private String userId;

}