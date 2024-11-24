package com.misim.integration.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.misim.controller.model.Response.MetadataResponse;
import com.misim.entity.VideoMetadata;
import com.misim.exception.CommonResponse;
import com.misim.service.VideoMetadataService;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class VideoMetadataControllerTest {

    @Autowired
    private VideoMetadataService videoMetadataService;

    private static final Long NON_EXISTENT_VIDEO_METADATA_ID = 99999L;

    @Test
    void getVideoMetadata_shouldReturnResponse_whenIdExists() {

        VideoMetadata metadata = videoMetadataService.createNewVideoMetadata();

        ExtractableResponse<Response> response = RestAssured
            .given()
                .log()
                .all()
                .pathParam("videoMetadataId", metadata.getId())
            .when()
                .get("/videoMetadata/{videoMetadataId}")
            .then()
                .log()
                .all()
                .statusCode(200)
                .extract();

        CommonResponse<MetadataResponse> commonResponse = response.as(
            new TypeRef<>() {
            });

        assertThat(commonResponse.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(commonResponse.getBody().viewCount()).isEqualTo(metadata.getViewCount());
        assertThat(commonResponse.getBody().likeCount()).isEqualTo(metadata.getLikeCount());
        assertThat(commonResponse.getBody().dislikeCount()).isEqualTo(metadata.getDislikeCount());

    }

    @Test
    void getVideoMetadata_shouldThrowException_whenIdDoesNotExist() {

        CommonResponse<?> response = RestAssured
            .given()
                .log()
                .all()
                .pathParam("videoMetadataId", NON_EXISTENT_VIDEO_METADATA_ID)
            .when()
                .get("/videoMetadata/{videoMetadataId}")
            .then()
                .log()
                .all()
                .statusCode(200)
                .extract()
                .as(CommonResponse.class);

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getMessage()).isEqualTo("해당 ID 값을 가지는 엔티티를 찾을 수 없습니다.");
        assertThat(response.getBody()).isEqualTo(88888);

    }

    @Test
    void addVideoMetadataViewCount_shouldReturnResponse_whenIdExists() {

        VideoMetadata metadata1 = videoMetadataService.createNewVideoMetadata();

        ExtractableResponse<Response> response = RestAssured
            .given()
                .log()
                .all()
                .pathParam("videoMetadataId", metadata1.getId())
            .when()
                .post("/videoMetadata/{videoMetadataId}/view")
            .then()
                .log()
                .all()
                .statusCode(200)
                .extract();

        CommonResponse<?> commonResponse = response.as(
            new TypeRef<>() {
            });

        assertThat(commonResponse.getHttpStatus()).isEqualTo(HttpStatus.CREATED);

        VideoMetadata metadata2 = videoMetadataService.readById(metadata1.getId());

        assertThat(metadata2.getViewCount()).isGreaterThan(metadata1.getViewCount());
        assertThat(metadata2.getLikeCount()).isEqualTo(metadata1.getLikeCount());
        assertThat(metadata2.getDislikeCount()).isEqualTo(metadata1.getDislikeCount());

    }

    @Test
    void addVideoMetadataViewCount_shouldThrowException_whenIdDoesNotExist() {

        CommonResponse<?> response = RestAssured
            .given()
                .log()
                .all()
                .pathParam("videoMetadataId", NON_EXISTENT_VIDEO_METADATA_ID)
            .when()
                .post("/videoMetadata/{videoMetadataId}/view")
            .then()
                .log()
                .all()
                .statusCode(200)
                .extract()
                .as(CommonResponse.class);

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getMessage()).isEqualTo("해당 ID 값을 가지는 엔티티를 찾을 수 없습니다.");
        assertThat(response.getBody()).isEqualTo(88888);

    }

    @Test
    void addVideoMetadataLikeCountUp_shouldReturnResponse_whenIdExists() {

        VideoMetadata metadata1 = videoMetadataService.createNewVideoMetadata();

        ExtractableResponse<Response> response = RestAssured
            .given()
                .log()
                .all()
                .pathParam("videoMetadataId", metadata1.getId())
                .queryParam("isChecked", true)
            .when()
                .post("/videoMetadata/{videoMetadataId}/like")
            .then()
                .log()
                .all()
                .statusCode(200)
                .extract();

        CommonResponse<?> commonResponse = response.as(
            new TypeRef<>() {
            });

        assertThat(commonResponse.getHttpStatus()).isEqualTo(HttpStatus.CREATED);

        VideoMetadata metadata2 = videoMetadataService.readById(metadata1.getId());

        assertThat(metadata2.getViewCount()).isEqualTo(metadata1.getViewCount());
        assertThat(metadata2.getLikeCount()).isGreaterThan(metadata1.getLikeCount());
        assertThat(metadata2.getDislikeCount()).isEqualTo(metadata1.getDislikeCount());

    }

    @Test
    void addVideoMetadataLikeCountDown_shouldReturnResponse_whenIdExists() {

        VideoMetadata metadata1 = videoMetadataService.createNewVideoMetadata();

        ExtractableResponse<Response> response = RestAssured
            .given()
                .log()
                .all()
                .pathParam("videoMetadataId", metadata1.getId())
                .queryParam("isChecked", false)
            .when()
                .post("/videoMetadata/{videoMetadataId}/like")
            .then()
                .log()
                .all()
                .statusCode(200)
                .extract();

        CommonResponse<?> commonResponse = response.as(
            new TypeRef<>() {
            });

        assertThat(commonResponse.getHttpStatus()).isEqualTo(HttpStatus.CREATED);

        VideoMetadata metadata2 = videoMetadataService.readById(metadata1.getId());

        assertThat(metadata2.getViewCount()).isEqualTo(metadata1.getViewCount());
        assertThat(metadata2.getLikeCount()).isLessThanOrEqualTo(metadata1.getLikeCount());
        assertThat(metadata2.getDislikeCount()).isEqualTo(metadata1.getDislikeCount());

    }

    @Test
    void addVideoMetadataLikeCountUp_shouldThrowException_whenIdDoesNotExist() {

        CommonResponse<?> response = RestAssured
            .given()
                .log()
                .all()
                .pathParam("videoMetadataId", NON_EXISTENT_VIDEO_METADATA_ID)
                .queryParam("isChecked", true)
            .when()
                .post("/videoMetadata/{videoMetadataId}/like")
            .then()
                .log()
                .all()
                .statusCode(200)
                .extract()
                .as(CommonResponse.class);

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getMessage()).isEqualTo("해당 ID 값을 가지는 엔티티를 찾을 수 없습니다.");
        assertThat(response.getBody()).isEqualTo(88888);

    }

    @Test
    void addVideoMetadataLikeCountDown_shouldThrowException_whenIdDoesNotExist() {

        CommonResponse<?> response = RestAssured
            .given()
                .log()
                .all()
                .pathParam("videoMetadataId", NON_EXISTENT_VIDEO_METADATA_ID)
                .queryParam("isChecked", false)
            .when()
                .post("/videoMetadata/{videoMetadataId}/like")
            .then()
                .log()
                .all()
                .statusCode(200)
                .extract()
                .as(CommonResponse.class);

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getMessage()).isEqualTo("해당 ID 값을 가지는 엔티티를 찾을 수 없습니다.");
        assertThat(response.getBody()).isEqualTo(88888);

    }

    @Test
    void addVideoMetadataDislikeCountUp_shouldReturnResponse_whenIdExists() {

        VideoMetadata metadata1 = videoMetadataService.createNewVideoMetadata();

        ExtractableResponse<Response> response = RestAssured
            .given()
                .log()
                .all()
                .pathParam("videoMetadataId", metadata1.getId())
                .queryParam("isChecked", true)
            .when()
                .post("/videoMetadata/{videoMetadataId}/dislike")
            .then()
                .log()
                .all()
                .statusCode(200)
                .extract();

        CommonResponse<?> commonResponse = response.as(
            new TypeRef<>() {
            });

        assertThat(commonResponse.getHttpStatus()).isEqualTo(HttpStatus.CREATED);

        VideoMetadata metadata2 = videoMetadataService.readById(metadata1.getId());

        assertThat(metadata2.getViewCount()).isEqualTo(metadata1.getViewCount());
        assertThat(metadata2.getLikeCount()).isEqualTo(metadata1.getLikeCount());
        assertThat(metadata2.getDislikeCount()).isGreaterThan(metadata1.getDislikeCount());

    }

    @Test
    void addVideoMetadataDislikeCountDown_shouldReturnResponse_whenIdExists() {

        VideoMetadata metadata1 = videoMetadataService.createNewVideoMetadata();

        ExtractableResponse<Response> response = RestAssured
            .given()
                .log()
                .all()
                .pathParam("videoMetadataId", metadata1.getId())
                .queryParam("isChecked", false)
            .when()
                .post("/videoMetadata/{videoMetadataId}/dislike")
            .then()
                .log()
                .all()
                .statusCode(200)
                .extract();

        CommonResponse<?> commonResponse = response.as(
            new TypeRef<>() {
            });

        assertThat(commonResponse.getHttpStatus()).isEqualTo(HttpStatus.CREATED);

        VideoMetadata metadata2 = videoMetadataService.readById(metadata1.getId());

        assertThat(metadata2.getViewCount()).isEqualTo(metadata1.getViewCount());
        assertThat(metadata2.getLikeCount()).isEqualTo(metadata1.getLikeCount());
        assertThat(metadata2.getDislikeCount()).isLessThanOrEqualTo(metadata1.getDislikeCount());

    }

    @Test
    void addVideoMetadataDislikeCountUp_shouldThrowException_whenIdDoesNotExist() {

        CommonResponse<?> response = RestAssured
            .given()
                .log()
                .all()
                .pathParam("videoMetadataId", NON_EXISTENT_VIDEO_METADATA_ID)
                .queryParam("isChecked", true)
            .when()
                .post("/videoMetadata/{videoMetadataId}/dislike")
            .then()
                .log()
                .all()
                .statusCode(200)
                .extract()
                .as(CommonResponse.class);

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getMessage()).isEqualTo("해당 ID 값을 가지는 엔티티를 찾을 수 없습니다.");
        assertThat(response.getBody()).isEqualTo(88888);

    }

    @Test
    void addVideoMetadataDislikeCountDown_shouldThrowException_whenIdDoesNotExist() {

        CommonResponse<?> response = RestAssured
            .given()
                .log()
                .all()
                .pathParam("videoMetadataId", NON_EXISTENT_VIDEO_METADATA_ID)
                .queryParam("isChecked", false)
            .when()
                .post("/videoMetadata/{videoMetadataId}/dislike")
            .then()
                .log()
                .all()
                .statusCode(200)
                .extract()
                .as(CommonResponse.class);

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getMessage()).isEqualTo("해당 ID 값을 가지는 엔티티를 찾을 수 없습니다.");
        assertThat(response.getBody()).isEqualTo(88888);

    }

    @Test
    void deleteVideoMetadata_shouldReturnResponse_whenIdExists() {

        VideoMetadata metadata1 = videoMetadataService.createNewVideoMetadata();

        ExtractableResponse<Response> response = RestAssured
            .given()
                .log()
                .all()
                .pathParam("videoMetadataId", metadata1.getId())
            .when()
                .delete("/videoMetadata/{videoMetadataId}")
            .then()
                .log()
                .all()
                .statusCode(200)
                .extract();

        CommonResponse<?> commonResponse = response.as(
            new TypeRef<>() {
            });

        assertThat(commonResponse.getHttpStatus()).isEqualTo(HttpStatus.NO_CONTENT);

        assertThatThrownBy(() -> videoMetadataService.readById(metadata1.getId()))
            .isInstanceOf(EntityNotFoundException.class);

    }


    @Test
    void deleteVideoMetadata_shouldReturnResponse_whenIdDoesNotExist() {

        ExtractableResponse<Response> response = RestAssured
            .given()
                .log()
                .all()
                .pathParam("videoMetadataId", NON_EXISTENT_VIDEO_METADATA_ID)
            .when()
                .delete("/videoMetadata/{videoMetadataId}")
            .then()
                .log()
                .all()
                .statusCode(200)
                .extract();

        CommonResponse<?> commonResponse = response.as(
            new TypeRef<>() {
            });

        assertThat(commonResponse.getHttpStatus()).isEqualTo(HttpStatus.NO_CONTENT);

        assertThatThrownBy(() -> videoMetadataService.readById(NON_EXISTENT_VIDEO_METADATA_ID))
            .isInstanceOf(EntityNotFoundException.class);

    }
}
