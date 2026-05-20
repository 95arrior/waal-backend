package com.waal.dto.kindergarten;

import com.waal.domain.Kindergarten;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class KindergartenResponse {
    private Long id;
    private String name;
    private String address;
    private String addressDetail;
    private String sggCode;
    private String phone;
    private String description;
    private String imageUrl;
    private int maxCapacity;
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

    private LocalDateTime createdAt;

    public static KindergartenResponse from(Kindergarten k) {
        return KindergartenResponse.builder()
                .id(k.getId())
                .name(k.getName())
                .address(k.getAddress())
                .addressDetail(k.getAddressDetail())
                .sggCode(k.getSggCode())
                .phone(k.getPhone())
                .description(k.getDescription())
                .imageUrl(k.getImageUrl())
                .maxCapacity(k.getMaxCapacity())
                .scheduleList(k.getScheduleList())
                .allowSmall(k.isAllowSmall())
                .smallMaxKg(k.getSmallMaxKg())
                .allowMedium(k.isAllowMedium())
                .mediumMaxKg(k.getMediumMaxKg())
                .allowLarge(k.isAllowLarge())
                .largeMaxKg(k.getLargeMaxKg())
                .hasShuttle(k.isHasShuttle())
                .allowShy(k.isAllowShy())
                .hasClass(k.isHasClass())
                .latitude(k.getLatitude())
                .longitude(k.getLongitude())
                .createdAt(k.getCreatedAt())
                .build();
    }
}
