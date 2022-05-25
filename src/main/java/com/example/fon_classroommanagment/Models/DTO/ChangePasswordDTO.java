package com.example.fon_classroommanagment.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDTO {

    @NotNull(message = "password ne sme biti null")
    @Size(min = 4,message = "Password mora imate bar 4 slova")
    private String password;

    @NotNull(message = "id ne sme biti prazan")
    private UUID id;

    private Date timeStamp=new Date();

    public ChangePasswordDTO(String password, UUID id) {
        this.password = password;
        this.id = id;

    }
}
