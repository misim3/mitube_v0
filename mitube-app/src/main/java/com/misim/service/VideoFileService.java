package com.misim.service;

import com.misim.entity.VideoFile;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.repository.VideoFileRepository;
import com.misim.util.Base64Convertor;
import com.misim.util.TimeUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class VideoFileService {

    @Value("upload.path")
    private String UPLOAD_PATH;

    private final VideoFileRepository videoFileRepository;

    public VideoFileService(VideoFileRepository videoFileRepository) {
        this.videoFileRepository = videoFileRepository;
    }

    public Resource getFileResource(String path) {
        return new FileSystemResource(path);
    }


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
}
