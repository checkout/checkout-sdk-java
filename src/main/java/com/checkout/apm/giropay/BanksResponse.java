package com.checkout.apm.giropay;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class BanksResponse extends Resource {

    private final Map<String, String> banks;

}
