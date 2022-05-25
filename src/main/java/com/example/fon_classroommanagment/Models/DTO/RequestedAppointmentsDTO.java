package com.example.fon_classroommanagment.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
public class RequestedAppointmentsDTO {

    private  byte[] image;

    @NotNull(message = "Ime ne sme biti prazno")
    @NotEmpty(message = "Ime mora sadrzati karaktere")
    private String firstName;

    @NotNull(message = "Ime ne sme biti prazno")
    @NotEmpty(message = "Ime mora sadrzati karaktere")
    private String lastName;

    @Positive(message = "Broj zahteva mora biti pozitivan broj")
    private Long number_of_requests;


}
