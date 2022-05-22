package com.example.fon_classroommanagment.Models.DTO;

import com.example.fon_classroommanagment.Models.Classroom.Classroom;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchReservationDTO {

    @NotNull(message = "Datum ne sme biti prazan")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date date;

    @NotNull(message = "classroomId ne sme biti null")
    private Long classroomId;
}
