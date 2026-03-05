package com.checkout.networkTokens.responses;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CryptogramResponse extends Resource {

    private String cryptogram;
    
    private String eci;

}