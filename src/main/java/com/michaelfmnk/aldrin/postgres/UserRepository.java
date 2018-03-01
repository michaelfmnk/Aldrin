package com.michaelfmnk.aldrin.postgres;

import com.michaelfmnk.aldrin.postgres.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
}
