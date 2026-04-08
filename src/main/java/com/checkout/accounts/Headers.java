package com.checkout.accounts;

import com.checkout.IHeaders;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Headers implements IHeaders {

    @SerializedName("if-match")
    private String ifMatch;

    @Override
    public Map<String, String> getHeaders() {
        final Map<String, String> headers = new LinkedHashMap<>();
        if (ifMatch != null) {
            headers.put("if-match", ifMatch);
        }
        return headers;
    }
}
