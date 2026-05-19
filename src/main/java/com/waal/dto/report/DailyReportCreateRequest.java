package com.waal.dto.report;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class DailyReportCreateRequest {

    @NotNull(message = "반려견 ID는 필수입니다.")
    private Long dogId;

    @NotNull(message = "유치원 ID는 필수입니다.")
    private Long kindergartenId;

    @NotNull(message = "보고서 날짜는 필수입니다.")
    private LocalDate reportDate;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    private List<String> photoUrls;
}
