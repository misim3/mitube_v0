package com.misim.service;

import com.misim.entity.TermAgreement;
import com.misim.entity.User;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.repository.TermRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TermAgreementService {

    private final TermRepository termRepository;

    public List<TermAgreement> getTermAgreements(User user, List<String> checkedTermTitles) {

        List<TermAgreement> termAgreements = new ArrayList<>();

        for (String title : checkedTermTitles) {
            TermAgreement agreement = TermAgreement.builder()
                .isAgree(true)
                .term(termRepository.findTermByTitleAndMaxVersion(title)
                    .orElseThrow(() -> new MitubeException(MitubeErrorCode.NOT_FOUND_TERM)))
                .build();

            agreement.setUser(user);

            termAgreements.add(agreement);
        }

        return termAgreements;
    }
}
