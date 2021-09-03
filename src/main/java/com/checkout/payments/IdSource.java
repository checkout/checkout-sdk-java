package com.checkout.payments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.checkout.common.CheckoutUtils.validateParams;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdSource implements RequestSource {

    private final String type = "id";
    private String id;
    private String cvv;
    private String firstName;
    private String lastName;

    public IdSource(final String id) {
        validateParams("id", id);
        this.id = id;
    }

    @Override
    public String getType() {
        return type;
    }

}
