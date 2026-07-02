package com.checkout.instruments.get;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public final class BankAccountField {

    private String id;

    private String section;

    private String display;

    private String helpText;

    private String type;

    private boolean required;

    private String validationRegex;

    private int minLength;

    @SerializedName("max_length")
    private int maxlength;

    private List<BankAccountFieldAllowedOption> allowedOptions;

    private List<BankAccountFieldDependency> dependencies;

}
