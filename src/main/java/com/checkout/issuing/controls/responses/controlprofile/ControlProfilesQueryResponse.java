package com.checkout.issuing.controls.responses.controlprofile;

import com.checkout.HttpMetadata;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class ControlProfilesQueryResponse extends HttpMetadata {

    @SerializedName("control_profiles")
    private List<ControlProfileResponse> controlProfiles;
}