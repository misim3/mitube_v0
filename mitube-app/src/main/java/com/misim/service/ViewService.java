package com.misim.service;

import com.misim.entity.View;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.repository.VideoRepository;
import com.misim.repository.ViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViewService {

    private final ViewRepository viewRepository;
    private final VideoRepository videoRepository;

    public View getIncrementView(Long videoId) {

        View view;

        if (viewRepository.exists(videoId)) {
            view = viewRepository.incrementViewCount(videoId);

        } else {
            view = View.builder()
                .videoId(videoId)
                .count(videoRepository.findViewsByVideoId(videoId)
                    .orElseThrow(() -> new MitubeException(MitubeErrorCode.NOT_FOUND_VIDEO)) + 1)
                .build();

            viewRepository.save(view);
        }

        return view;
    }
}
