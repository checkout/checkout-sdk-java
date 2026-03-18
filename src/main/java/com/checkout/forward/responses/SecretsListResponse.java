package com.checkout.forward.responses;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class SecretsListResponse extends Resource {

    private List<SecretResponse> data;

}