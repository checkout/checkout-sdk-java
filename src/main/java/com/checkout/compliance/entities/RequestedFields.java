package com.checkout.compliance.entities;

import lombok.Data;

import java.util.List;

/**
 * Groups the requested fields by party (sender/recipient).
 */
@Data
public final class RequestedFields {

    private List<RequestedField> sender;

    private List<RequestedField> recipient;

}
