package com.misim.entity;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VideoCategory {
    MUSIC("음악", 0),
    GAME("게임", 1),
    SPORT("스포츠", 2),
    TECHNOLOGY("과학기술", 3),
    EDUCATION("교육", 4),
    STYLE("스타일", 5),
    NEWS("뉴스", 6),
    SOCIAL_MOVEMENT("사회운동", 7),
    ANIMAL("동물", 8),
    ENTERTAINMENT("엔터테인먼트", 9),
    TRAVEL("여행", 10),
    MOVIE("영화", 11),
    BLOG("블로그", 12),
    CAR("자동차", 13),
    COMEDY("코미디", 14),
    ;

    private final String name;
    private final int code;

    @Getter
    private static final List<String> categoryList = Arrays.stream(VideoCategory.values())
        .map(VideoCategory::getName).toList();

    public static Boolean existByCode(int code) {
        return Arrays.stream(VideoCategory.values())
            .anyMatch(c -> c.getCode() == code);
    }

    public static String getNameByCode(int code) {
        return Arrays.stream(VideoCategory.values())
            .filter(c -> c.getCode() == code)
            .findFirst()
            .map(VideoCategory::getName)
            .orElse(null);
    }

}
