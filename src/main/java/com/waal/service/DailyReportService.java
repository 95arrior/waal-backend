package com.waal.service;

import com.waal.common.exception.ErrorCode;
import com.waal.common.exception.WaalException;
import com.waal.domain.*;
import com.waal.dto.report.DailyReportCreateRequest;
import com.waal.dto.report.DailyReportResponse;
import com.waal.dto.report.DailyReportUpdateRequest;
import com.waal.repository.DailyReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DailyReportService {

    private final DailyReportRepository reportRepository;
    private final DogService dogService;
    private final KindergartenService kindergartenService;
    private final UserService userService;

    @Transactional
    public DailyReportResponse create(Long teacherId, DailyReportCreateRequest request) {
        kindergartenService.validateMember(teacherId, request.getKindergartenId());

        Dog dog = dogService.findDog(request.getDogId());
        Kindergarten kindergarten = kindergartenService.findKindergarten(request.getKindergartenId());
        User teacher = userService.findUser(teacherId);

        if (reportRepository.existsByDogIdAndKindergartenIdAndReportDate(
                dog.getId(), kindergarten.getId(), request.getReportDate())) {
            throw new WaalException(ErrorCode.REPORT_ALREADY_EXISTS);
        }

        DailyReport report = reportRepository.save(DailyReport.builder()
                .dog(dog)
                .kindergarten(kindergarten)
                .reportDate(request.getReportDate())
                .content(request.getContent())
                .teacher(teacher)
                .build());

        if (request.getPhotoUrls() != null) {
            addPhotos(report, request.getPhotoUrls());
        }

        return DailyReportResponse.from(report);
    }

    public DailyReportResponse get(Long userId, Long reportId) {
        DailyReport report = findReport(reportId);
        boolean isTeacher = kindergartenService.isMember(userId, report.getKindergarten().getId());
        boolean isOwner = report.getDog().getOwner().getId().equals(userId);
        if (!isTeacher && !isOwner) {
            throw new WaalException(ErrorCode.FORBIDDEN);
        }
        return DailyReportResponse.from(report);
    }

    public List<DailyReportResponse> getDogReports(Long userId, Long dogId) {
        Dog dog = dogService.findDog(dogId);
        boolean isOwner = dog.getOwner().getId().equals(userId);
        // 유치원 선생님도 조회 가능 — 소속 유치원의 보고서라면
        if (!isOwner) {
            throw new WaalException(ErrorCode.FORBIDDEN);
        }
        return reportRepository.findByDogIdOrderByReportDateDesc(dogId).stream()
                .map(DailyReportResponse::from)
                .toList();
    }

    public List<DailyReportResponse> getKindergartenReports(Long userId, Long kindergartenId, LocalDate date) {
        kindergartenService.validateMember(userId, kindergartenId);
        return reportRepository.findByKindergartenIdAndReportDate(kindergartenId, date).stream()
                .map(DailyReportResponse::from)
                .toList();
    }

    @Transactional
    public DailyReportResponse update(Long userId, Long reportId, DailyReportUpdateRequest request) {
        DailyReport report = findReport(reportId);
        kindergartenService.validateMember(userId, report.getKindergarten().getId());

        report.updateContent(request.getContent());

        report.getPhotos().clear();
        if (request.getPhotoUrls() != null) {
            addPhotos(report, request.getPhotoUrls());
        }

        return DailyReportResponse.from(report);
    }

    private void addPhotos(DailyReport report, List<String> photoUrls) {
        for (int i = 0; i < photoUrls.size(); i++) {
            report.getPhotos().add(ReportPhoto.builder()
                    .dailyReport(report)
                    .photoUrl(photoUrls.get(i))
                    .sortOrder(i)
                    .build());
        }
    }

    private DailyReport findReport(Long reportId) {
        return reportRepository.findById(reportId)
                .orElseThrow(() -> new WaalException(ErrorCode.DAILY_REPORT_NOT_FOUND));
    }
}
