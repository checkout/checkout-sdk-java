package com.checkout.issuing.controls.requests.controlprofile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UpdateControlProfileRequest {

    private String name;
}