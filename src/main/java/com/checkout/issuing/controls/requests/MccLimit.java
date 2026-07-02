package com.checkout.issuing.controls.requests;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public final class MccLimit {

    private String type;

    private List<String> mccList;
}
