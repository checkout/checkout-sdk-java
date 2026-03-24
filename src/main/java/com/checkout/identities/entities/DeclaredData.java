package com.checkout.identities.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The personal details provided by the applicant
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class DeclaredData {

    private String name;
}