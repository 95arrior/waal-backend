package com.waal.dto.report;

import com.waal.domain.DailyReport;
import com.waal.domain.ReportPhoto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class DailyReportResponse {
    private Long id;
    private Long dogId;
    private String dogName;
    private Long kindergartenId;
    private String kindergartenName;
    private LocalDate reportDate;
    private String content;
    private Long teacherId;
    private String teacherNickname;
    private List<String> photoUrls;
    private LocalDateTime createdAt;

    public static DailyReportResponse from(DailyReport report) {
        List<String> photoUrls = report.getPhotos().stream()
                .sorted((a, b) -> Integer.compare(a.getSortOrder(), b.getSortOrder()))
                .map(ReportPhoto::getPhotoUrl)
                .toList();

        return DailyReportResponse.builder()
                .id(report.getId())
                .dogId(report.getDog().getId())
                .dogName(report.getDog().getName())
                .kindergartenId(report.getKindergarten().getId())
                .kindergartenName(report.getKindergarten().getName())
                .reportDate(report.getReportDate())
                .content(report.getContent())
                .teacherId(report.getTeacher().getId())
                .teacherNickname(report.getTeacher().getNickname())
                .photoUrls(photoUrls)
                .createdAt(report.getCreatedAt())
                .build();
    }
}
