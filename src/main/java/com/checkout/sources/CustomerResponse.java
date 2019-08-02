package com.checkout.sources;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CustomerResponse {
    private String id;
    private String email;
    private String name;
}