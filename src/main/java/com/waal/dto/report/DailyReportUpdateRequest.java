package com.waal.dto.report;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class DailyReportUpdateRequest {

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    private List<String> photoUrls;
}
