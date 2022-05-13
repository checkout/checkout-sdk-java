package com.checkout;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ItemsResponse<T> extends HttpMetadata {

    private List<T> items = new ArrayList<>();

}
