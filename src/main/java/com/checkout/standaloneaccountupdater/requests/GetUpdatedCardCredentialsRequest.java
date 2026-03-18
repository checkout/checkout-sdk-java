package com.checkout.standaloneaccountupdater.requests;

import com.checkout.standaloneaccountupdater.entities.SourceOptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUpdatedCardCredentialsRequest {

    /**
     * The source to update. You must provide either card or instrument object, but not both.
     */
    private SourceOptions sourceOptions;
}