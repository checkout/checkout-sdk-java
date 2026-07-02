package com.checkout.issuing.controls.responses.controlprofile;

import com.checkout.HttpMetadata;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class ControlProfilesQueryResponse extends HttpMetadata {

    private List<ControlProfileResponse> controlProfiles;
}