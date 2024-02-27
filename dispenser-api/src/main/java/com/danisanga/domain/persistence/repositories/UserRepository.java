package com.danisanga.domain.persistence.repositories;

import com.danisanga.domain.models.UserModel;

public interface UserRepository {

    /**
     * Find user by email.
     *
     * @param email email address
     * @return  User object for requesting email.
     */
    UserModel findUserByEmail(String email);
}
