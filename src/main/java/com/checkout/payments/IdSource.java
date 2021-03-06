package com.checkout.payments;

import com.checkout.common.CheckoutUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdSource implements RequestSource {
    public static final String TYPE_NAME = "id";
    private String id;
    private String cvv;
    private String firstName;
    private String lastName;

    private final String type = TYPE_NAME;

    public IdSource(String id) {
        if (CheckoutUtils.isNullOrWhitespace(id)) {
            throw new IllegalArgumentException("The source ID is required");
        }
        this.id = id;
    }

    @Override
    public String getType() {
        return TYPE_NAME;
    }
}