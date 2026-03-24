package com.checkout.identities.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Face image details
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Face {
    /**
     * The URL to the face image.
     */
    private String imageSignedUrl;
}