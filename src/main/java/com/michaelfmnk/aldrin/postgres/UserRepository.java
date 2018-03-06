package com.michaelfmnk.aldrin.postgres;

import com.michaelfmnk.aldrin.postgres.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Selects user by username
     * @param username user's username
     * @return User with a specified username
     */
    User findUserByUsername(String username);


    /**
     * Selects user by id
     * @param id user's id
     * @return User with a specified id
     */
    User findUserById(Long id);
}
