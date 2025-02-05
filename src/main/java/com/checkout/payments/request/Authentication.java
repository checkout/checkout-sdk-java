package com.checkout.payments.request;

import com.checkout.payments.PreferredExperiences;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Authentication {

    @SerializedName("preferred_experiences")
    private List<PreferredExperiences> preferredExperiences;

}