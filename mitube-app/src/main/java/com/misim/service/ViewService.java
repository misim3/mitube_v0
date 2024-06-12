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

        if (viewRepository.exists(videoId.toString())) {
            viewRepository.incrementViewCount(videoId.toString());
            return viewRepository.findViewByVideoId(videoId.toString());

        } else {
            View view = View.builder()
                .videoId(videoId)
                .views(videoRepository.findViewsByVideoId(videoId)
                    .orElseThrow(() -> new MitubeException(MitubeErrorCode.NOT_FOUND_VIDEO)) + 1)
                .build();

            viewRepository.save(view);

            return view;
        }
    }
}
