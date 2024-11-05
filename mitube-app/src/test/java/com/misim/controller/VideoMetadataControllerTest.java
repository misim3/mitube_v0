package com.misim.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.misim.controller.model.Response.MetadataResponse;
import com.misim.entity.VideoMetadata;
import com.misim.exception.CommonResponse;
import com.misim.service.VideoMetadataService;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class VideoMetadataControllerTest {

    @Autowired
    private VideoMetadataService videoMetadataService;

    @Test
    void getVideoMetadata() {

        VideoMetadata metadata = videoMetadataService.create();

        ExtractableResponse<Response> response = RestAssured
            .given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .pathParam("videoMetadataId", metadata.getId())
            .when()
                .get("/videoMetadata/{videoMetadataId}")
            .then()
                .log()
                .all()
                .statusCode(200)
                .extract();

        CommonResponse<MetadataResponse> commonResponse = response.as(
            new TypeRef<CommonResponse<MetadataResponse>>() {});

        assertThat(commonResponse.getBody().viewCount()).isEqualTo(metadata.getViewCount());
        assertThat(commonResponse.getBody().likeCount()).isEqualTo(metadata.getLikeCount());
        assertThat(commonResponse.getBody().dislikeCount()).isEqualTo(metadata.getDislikeCount());

    }

    @Test
    void addVideoMetadataViewCount() {

        VideoMetadata metadata1 = videoMetadataService.create();

        RestAssured
            .given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .pathParam("videoMetadataId", metadata1.getId())
            .when()
                .post("/videoMetadata/{videoMetadataId}/view")
            .then()
                .log()
                .all()
                .statusCode(200)
                .extract();

        VideoMetadata metadata2 = videoMetadataService.read(metadata1.getId());

        assertThat(metadata2.getViewCount()).isGreaterThan(metadata1.getViewCount());
        assertThat(metadata2.getLikeCount()).isEqualTo(metadata1.getLikeCount());
        assertThat(metadata2.getDislikeCount()).isEqualTo(metadata1.getDislikeCount());

    }

    @Test
    void addVideoMetadataLikeCount() {

        VideoMetadata metadata1 = videoMetadataService.create();

        RestAssured
            .given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .pathParam("videoMetadataId", metadata1.getId())
                .param("isChecked", true)
            .when()
                .post("/videoMetadata/{videoMetadataId}/like")
            .then()
                .log()
                .all()
                .statusCode(200)
                .extract();

        VideoMetadata metadata2 = videoMetadataService.read(metadata1.getId());

        assertThat(metadata2.getViewCount()).isEqualTo(metadata1.getViewCount());
        assertThat(metadata2.getLikeCount()).isGreaterThan(metadata1.getLikeCount());
        assertThat(metadata2.getDislikeCount()).isEqualTo(metadata1.getDislikeCount());
    }

    @Test
    void addVideoMetadataDislikeCount() {

        VideoMetadata metadata1 = videoMetadataService.create();

        RestAssured
            .given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .pathParam("videoMetadataId", metadata1.getId())
                .param("isChecked", true)
            .when()
                .post("/videoMetadata/{videoMetadataId}/dislike")
            .then()
                .log()
                .all()
                .statusCode(200)
                .extract();

        VideoMetadata metadata2 = videoMetadataService.read(metadata1.getId());

        assertThat(metadata2.getViewCount()).isEqualTo(metadata1.getViewCount());
        assertThat(metadata2.getLikeCount()).isEqualTo(metadata1.getLikeCount());
        assertThat(metadata2.getDislikeCount()).isGreaterThan(metadata1.getDislikeCount());

    }

    @Test
    void deleteVideoMetadata() {

        VideoMetadata metadata1 = videoMetadataService.create();

        RestAssured
            .given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .pathParam("videoMetadataId", metadata1.getId())
            .when()
                .delete("/videoMetadata/{videoMetadataId}")
            .then()
                .log()
                .all()
                .statusCode(200)
                .extract();

        assertThatThrownBy(() -> videoMetadataService.read(metadata1.getId()))
            .isInstanceOf(NoSuchElementException.class);

    }
}
