package com.misim.service;

import com.misim.controller.model.UserDto;
import com.misim.entity.Term;
import com.misim.entity.TermAgreement;
import com.misim.entity.User;
import com.misim.exception.MitubeException;
import com.misim.exception.MitubeErrorCode;
import com.misim.repository.TermAgreementRepository;
import com.misim.repository.TermRepository;
import com.misim.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TermAgreementRepository termAgreementRepository;
    private final TermRepository termRepository;

    private BCryptPasswordEncoder passwordEncoder;

    public void registerUser(UserDto userDto) {

        // 닉네임 중복 확인
        if (userRepository.existsByNickname(userDto.getNickname())) {
            throw new MitubeException(MitubeErrorCode.EXIST_NICKNAME);
        }

        // 이메일 인증

        // userDto -> user로 변환하여 db에 저장 (비밀번호 암호화)
        User user = User.builder()
                .nickname(userDto.getNickname())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .build();

        userRepository.save(user);

        // 약관 동의
        List<Boolean> agreeList = new ArrayList<>(Arrays.asList(userDto.isAgreeMandatoryTerm1(), userDto.isAgreeMandatoryTerm2(), userDto.isAgreeOptionalTerm1(), userDto.isAgreeOptionalTerm2()));
        List<Term> termList = termRepository.findAll(Sort.by("id"));

        if (agreeList.size() != termList.size()) {
            throw new MitubeException(MitubeErrorCode.CHECK_UPDATED_TERMS);
        }

        setTermAgreement(agreeList, termList, user);

    }

    private void setTermAgreement(List<Boolean> agreements, List<Term> terms, User user) {
        TermAgreement termAgreement;
        for (int i=0; i<agreements.size(); i++) {
            termAgreement = TermAgreement.builder()
                    .user(user)
                    .term(terms.get(i))
                    .isAgree(agreements.get(i))
                    .build();

            termAgreementRepository.save(termAgreement);
        }
    }
}
