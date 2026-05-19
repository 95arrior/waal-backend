package com.waal.dto.reservation;

import com.waal.domain.DailyCapacity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class DailyCapacityResponse {
    private LocalDate date;
    private int maxCapacity;
    private int currentCount;
    private int remaining;
    private boolean full;

    public static DailyCapacityResponse from(DailyCapacity dc) {
        return DailyCapacityResponse.builder()
                .date(dc.getDate())
                .maxCapacity(dc.getMaxCapacity())
                .currentCount(dc.getCurrentCount())
                .remaining(dc.getMaxCapacity() - dc.getCurrentCount())
                .full(dc.isFull())
                .build();
    }

    public static DailyCapacityResponse empty(LocalDate date, int maxCapacity) {
        return DailyCapacityResponse.builder()
                .date(date)
                .maxCapacity(maxCapacity)
                .currentCount(0)
                .remaining(maxCapacity)
                .full(false)
                .build();
    }
}
