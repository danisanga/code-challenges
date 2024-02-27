package com.danisanga.infrastructure.token.generator;

import com.danisanga.domain.models.UserModel;

/**
 * Generic authentication token generator interface.
 */
public interface AuthTokenGenerator {

    String generateToken(final UserModel userModel);
}
