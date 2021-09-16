package com.checkout.apm.ideal;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class IssuerResponse extends Resource {

    private List<IdealCountry> countries;

}
