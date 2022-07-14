package com.checkout.instruments.get;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public final class BankAccountField {

    private String id;

    private String section;

    private String display;

    @SerializedName("help_text")
    private String helpText;

    private String type;

    private boolean required;

    @SerializedName("validation_regex")
    private String validationRegex;

    @SerializedName("min_length")
    private int minLength;

    @SerializedName("max_length")
    private int maxlength;

    @SerializedName("allowed_options")
    private List<BankAccountFieldAllowedOption> allowedOptions;

    private List<BankAccountFieldDependency> dependencies;

}
