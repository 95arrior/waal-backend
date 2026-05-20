package com.waal.dto.kindergarten;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class KindergartenUpdateRequest {

    @NotBlank(message = "유치원 이름은 필수입니다.")
    private String name;

    @NotBlank(message = "주소는 필수입니다.")
    private String address;

    private String addressDetail;
    private String sggCode;
    private String phone;
    private String description;
    private String imageUrl;

    @NotNull(message = "최대 수용 인원은 필수입니다.")
    @Min(value = 1, message = "최대 수용 인원은 1 이상이어야 합니다.")
    private Integer maxCapacity;

    private List<ScheduleItem> scheduleList;

    // 견종 크기
    private boolean allowSmall;
    private int smallMaxKg;
    private boolean allowMedium;
    private int mediumMaxKg;
    private boolean allowLarge;
    private int largeMaxKg;

    // 서비스 옵션
    private boolean hasShuttle;
    private boolean allowShy;
    private boolean hasClass;

    private Double latitude;
    private Double longitude;
}
