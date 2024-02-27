package com.danisanga.infrastructure.repositories;

import com.danisanga.domain.models.UserModel;
import com.danisanga.domain.persistence.repositories.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultUserRepository implements UserRepository {

    /**
     * {@inheritDoc}
     */
    public UserModel findUserByEmail(String email) {
        return new UserModel(email,"123456");
    }
}
