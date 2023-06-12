package ru.gvozdev.emotrack.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KafkaEventDto {
    private Long id;
    private String title;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm")
    private Date endDate;
}
