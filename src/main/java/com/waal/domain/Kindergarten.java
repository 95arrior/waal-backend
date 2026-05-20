package com.waal.domain;

import com.waal.dto.kindergarten.ScheduleItem;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "kindergartens")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Kindergarten extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    private String addressDetail;

    private String sggCode;

    private String phone;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String imageUrl;

    @Column(nullable = false)
    private int maxCapacity;

    // 견종 크기
    @Builder.Default private boolean allowSmall = false;
    @Builder.Default private int smallMaxKg = 0;
    @Builder.Default private boolean allowMedium = false;
    @Builder.Default private int mediumMaxKg = 0;
    @Builder.Default private boolean allowLarge = false;
    @Builder.Default private int largeMaxKg = 0;

    // 서비스 옵션
    @Builder.Default private boolean hasShuttle = false;
    @Builder.Default private boolean allowShy = false;
    @Builder.Default private boolean hasClass = false;

    // 운영 스케줄 (JSON)
    @Convert(converter = ScheduleListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<ScheduleItem> scheduleList;

    // 위치 (거리 계산용, 추후 사용)
    private Double latitude;
    private Double longitude;
}
