package com.misim.controller.model.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UploadVideosResponse {

    @Schema(name = "id", description = "동영상 업로드 식별 정보", example = "AHKW+==", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;
}
