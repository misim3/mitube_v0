package com.misim.service;

import com.misim.entity.Reaction;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.repository.ReactionRepository;
import com.misim.repository.UserRepository;
import com.misim.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReactionService {

    private final ReactionRepository reactionRepository;
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;

    public void checking(String type, Long userId, Long videoId) {

        if (!userRepository.existsById(userId)) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_USER);
        }

        if (!videoRepository.existsById(videoId)) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_VIDEO);
        }

        Reaction reaction;

        if (reactionRepository.existsReactionByUserIdAndVideoId(userId, videoId)) {

            reaction = reactionRepository.findByUserIdAndVideoId(userId, videoId);

            reaction.setType(type);
            reaction.setIsActive(true);

        } else {

            reaction = Reaction.builder()
                    .type(type)
                    .isActive(true)
                    .userId(userId)
                    .videoId(videoId)
                    .build();
        }

        reactionRepository.save(reaction);
    }

    public void unchecking(String type, Long userId, Long videoId) {

        if (!userRepository.existsById(userId)) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_USER);
        }

        if (!videoRepository.existsById(videoId)) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_VIDEO);
        }

        if (!reactionRepository.existsReactionByUserIdAndVideoId(userId, videoId)) {
            throw new MitubeException(MitubeErrorCode.INVALID_REACTION_UNCHECK);
        }

        Reaction reaction = reactionRepository.findByUserIdAndVideoId(userId, videoId);

        reaction.setType(type);
        reaction.setIsActive(false);

        reactionRepository.save(reaction);
    }
}
