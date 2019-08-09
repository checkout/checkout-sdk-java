package com.checkout.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ErrorResponse extends Resource {
    private final String errorType;
    private List<String> errorCodes = new ArrayList<>();
}