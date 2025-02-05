package com.misim.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "응답 결과")
public class CommonResponse<T> {

    @Schema(description = "응답 Http 상태 코드")
    private HttpStatus httpStatus;

    @Schema(description = "응답 메시지")
    private String message;

    // 에러시 null
    @Schema(description = "응답 데이터")
    private T body;

}
