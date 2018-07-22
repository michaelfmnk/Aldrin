package com.michaelfmnk.aldrin.repositories;

import com.michaelfmnk.aldrin.entities.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Integer> {

}
