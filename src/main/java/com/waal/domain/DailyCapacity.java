package com.waal.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(
    name = "daily_capacities",
    uniqueConstraints = @UniqueConstraint(columnNames = {"kindergarten_id", "date"})
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class DailyCapacity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kindergarten_id", nullable = false)
    private Kindergarten kindergarten;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private int maxCapacity;

    @Column(nullable = false)
    @Builder.Default
    private int currentCount = 0;

    public void incrementCount() {
        this.currentCount++;
    }

    public void decrementCount() {
        if (this.currentCount > 0) {
            this.currentCount--;
        }
    }

    public boolean isFull() {
        return this.currentCount >= this.maxCapacity;
    }
}
