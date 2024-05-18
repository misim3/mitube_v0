package com.misim.repository;

import com.misim.entity.SmsVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsVerificationRepository extends JpaRepository<SmsVerification, Long> {

    SmsVerification findTopByPhoneNumberOrderByExpiryDateDesc(String phoneNumber);
}
