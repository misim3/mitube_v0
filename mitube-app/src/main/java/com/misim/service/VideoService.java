package com.misim.service;

import com.misim.controller.model.Request.CreateVideoRequest;
import com.misim.controller.model.Response.VideoResponse;
import com.misim.controller.model.Response.WatchingVideoResponse;
import com.misim.entity.Subscription;
import com.misim.entity.User;
import com.misim.entity.VideoCatalog;
import com.misim.entity.VideoCategory;
import com.misim.entity.VideoFile;
import com.misim.entity.WatchingInfo;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.repository.SubscriptionRepository;
import com.misim.repository.UserRepository;
import com.misim.repository.VideoFileRepository;
import com.misim.repository.VideoRepository;
import com.misim.repository.WatchingInfoRepository;
import com.misim.util.Base64Convertor;
import com.misim.util.TimeUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class VideoService {

    @Value("upload.path")
    private String UPLOAD_PATH;

    private final VideoRepository videoRepository;
    private final VideoFileRepository videoFileRepository;
    private final UserRepository userRepository;
    private final WatchingInfoRepository watchingInfoRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final AsyncService asyncService;
    private final ReactionService reactionService;
    private final WatchingInfoService watchingInfoService;
    private final ViewIncreaseRequestService viewIncreaseRequestService;

    public String uploadVideos(MultipartFile file) {

        String uploadDir = makeFolder();

        String saveFileName = createFileName(file.getOriginalFilename());

        String saveFile = uploadDir + File.separator + saveFileName;

        Path saveFilePath = Paths.get(saveFile);

        try {
            file.transferTo(saveFilePath);
        } catch (IOException e) {
            e.fillInStackTrace();
            throw new MitubeException(MitubeErrorCode.NOT_CREATED_FILE);
        }

        VideoFile videoFile = videoFileRepository.save(VideoFile.builder().path(saveFile).build());

        return Base64Convertor.encode(videoFile.getId());
    }

    private String makeFolder() {

        String folderStr = UPLOAD_PATH + File.separator + TimeUtil.getNow()
            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd"));

        Path folder = Paths.get(folderStr);

        if (!Files.exists(folder)) {
            try {
                Files.createDirectories(folder);
            } catch (IOException e) {
                throw new MitubeException(MitubeErrorCode.NOT_CREATED_DIR);
            }
        }

        return folderStr;
    }

    private String createFileName(String originalFilename) {
        return UUID.randomUUID() + "_" + originalFilename;
    }

    public void createVideos(CreateVideoRequest createVideoRequest) {

        Long videoFileId = Base64Convertor.decode(createVideoRequest.getVideo_token());

        // 비디오 파일 확인
        if (!videoFileRepository.existsById(videoFileId)) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_VIDEO_FILE);
        }

        // 유저 확인
        if (!userRepository.existsByNickname(createVideoRequest.getNickname())) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_USER);
        }

        // 비디오 카테고리 확인
        if (!VideoCategory.existByCode(createVideoRequest.getCategoryId())) {
            throw new MitubeException(MitubeErrorCode.INVALID_CATEGORY);
        }

        VideoFile videoFile = videoFileRepository.findById(videoFileId)
            .orElseThrow(() -> new MitubeException(MitubeErrorCode.NOT_FOUND_VIDEO_FILE));

        User user = userRepository.findByNickname(createVideoRequest.getNickname());

        VideoCatalog videoCatalog = VideoCatalog.builder()
            .title(createVideoRequest.getTitle())
            .description(createVideoRequest.getDescription())
            .videoFile(videoFile)
            .categoryId(createVideoRequest.getCategoryId())
            .build();

        // 비디오 저장
        videoRepository.save(videoCatalog);
    }

    public WatchingVideoResponse startWatchingVideo(Long videoId) {

        VideoCatalog videoCatalog = videoRepository.findById(videoId)
            .orElseThrow(() -> new MitubeException(MitubeErrorCode.NOT_FOUND_VIDEO));

        return WatchingVideoResponse.builder()
            .title(videoCatalog.getTitle())
            .description(videoCatalog.getDescription())
            .createdDate(videoCatalog.getCreatedDate())
            .videoLink(null)
            .build();
    }

    //추후 수정
    @Async
    public void incrementView(Long videoId) {

//        VideoCatalog videoCatalog = videoRepository.findById(videoId)
//            .orElseThrow(() -> new MitubeException(MitubeErrorCode.NOT_FOUND_VIDEO));
//
//        videoCatalog.incrementViewCount();
//
//        videoRepository.save(videoCatalog);
    }

    public void updateWatchingVideo(Long videoId, Long userId, Long watchingTime) {

        watchingInfoService.updateWatchingInfo(videoId, userId, watchingTime);
    }

    public void completeWatchingVideo(Long videoId, Long userId, Long watchingTime) {

        watchingInfoService.completeWatchingInfo(videoId, userId, watchingTime);
    }

    public List<VideoResponse> getNewVideos() {

        List<VideoCatalog> videoCatalogs = videoRepository.findTopTen();

        // 유료 회원 여부 판단해서 광고

        return VideoResponse.convertVideos(videoCatalogs);
    }

    public List<VideoResponse> getHotVideos() {

        List<Long> videoIds = viewIncreaseRequestService.getTopIncreasesForLastWeek();

        List<VideoCatalog> videoCatalogs = videoRepository.findAllById(videoIds);

        return VideoResponse.convertVideos(videoCatalogs);
    }

    public List<VideoResponse> getWatchingVideos(Long userId) {

        // 로그인하지 않은 사용자가 요청한 경우, 빈 리스트 반환
        if (userId == null) {
            return null;
        }

        if (!userRepository.existsById(userId)) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_USER);
        }

        List<WatchingInfo> watchingInfos = watchingInfoRepository.findLastTopTenByUserId(userId);

        List<VideoCatalog> videoCatalogs = videoRepository.findAllById(
            watchingInfos.stream()
                .map(WatchingInfo::getVideoId)
                .toList()
        );

        return VideoResponse.convertVideos(videoCatalogs);
    }

    // 추후 수정
    public List<VideoResponse> getSubscribingChannelNewVideos(Long userId) {

        // 로그인하지 않은 사용자가 요청한 경우, 빈 리스트 반환
        if (userId == null) {
            return null;
        }

        if (!userRepository.existsById(userId)) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_USER);
        }

        List<Subscription> subscriptions = subscriptionRepository.findSubscriptionsBySubscriberId(
            userId);

//        List<VideoCatalog> videoCatalogs = subscriptions.stream()
//            .limit(10)
//            .map(s -> videoRepository.findTopByUserId(s.getChannel().getOwner().getId()))
//            .toList();
//
//        return VideoResponse.convertVideos(videoCatalogs);
        return null;
    }
}
