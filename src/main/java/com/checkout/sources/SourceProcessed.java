package com.checkout.sources;

import com.checkout.common.CustomerResponse;
import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SourceProcessed extends Resource {
    private String id;
    private String type;
    private String responseCode;
    private CustomerResponse customer;
    private ResponseData responseData;
}