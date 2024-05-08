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

    public View getView(Long videoId) {

        if (viewRepository.exists(videoId.toString())) {
            // 캐시 힛
            return viewRepository.findViewByVideoId(videoId.toString());

        } else {
            // 캐시 미스
            View view = View.builder()
                    .videoId(videoId)
                    .views(videoRepository.findViewsByVideoId(videoId)
                            .orElseThrow(() -> new MitubeException(MitubeErrorCode.NOT_FOUND_VIDEO)))
                    .build();

            viewRepository.save(view);

            return view;
        }
    }
}
