package com.misim.service;

import com.misim.controller.model.Response.ReactionResponse;
import com.misim.entity.Reaction;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.repository.ReactionRepository;
import com.misim.repository.UserRepository;
import com.misim.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReactionService {

    private final ReactionRepository reactionRepository;
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;

    public ReactionResponse getReaction(Long userId, Long videoId) {

        if (!userRepository.existsById(userId)) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_USER);
        }

        if (!videoRepository.existsById(videoId)) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_VIDEO);
        }

        Optional<Reaction> reaction = reactionRepository.findById(userId);

        return reaction.map(r -> ReactionResponse.builder()
                .type(r.getType())
                .build())
            .orElse(null);
    }

    public void checking(String type, Long userId, Long videoId) {

        if (!userRepository.existsById(userId)) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_USER);
        }

        if (!videoRepository.existsById(videoId)) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_VIDEO);
        }

        Reaction reaction = reactionRepository.findByUserIdAndVideoId(userId, videoId)
            .orElse(null);

        if (reaction != null) {

            reaction.setType(type);
            reaction.setIsActive(true);

        } else {

            reaction = Reaction.builder()
                .type(type)
                .isActive(true)
                .user(userRepository.findById(userId)
                    .orElseThrow(() -> new MitubeException(MitubeErrorCode.NOT_FOUND_USER)))
                .video(videoRepository.findById(videoId)
                    .orElseThrow(() -> new MitubeException(MitubeErrorCode.NOT_FOUND_VIDEO)))
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

        Reaction reaction = reactionRepository.findByUserIdAndVideoId(userId, videoId)
            .orElseThrow(() -> new MitubeException(MitubeErrorCode.NOT_FOUND_REACTION));

        reaction.setType(type);
        reaction.setIsActive(false);

        reactionRepository.save(reaction);
    }
}
