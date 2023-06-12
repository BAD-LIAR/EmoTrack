package ru.gvozdev.emotrack.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {

    private Long userId;
    private Long id;
    private String title;
    private String description;
    private Long emotionalCondition;
    private Long predictedEmotionalCondition;
    private Long dayEmotionalCondition;
    private Long predictedDayEmotionalCondition;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm")
    private Date endDate;
}
