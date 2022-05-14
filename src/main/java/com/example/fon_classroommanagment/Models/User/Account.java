package com.example.fon_classroommanagment.Models.User;

import com.example.fon_classroommanagment.Models.Emplayee.EducationTitle;
import com.example.fon_classroommanagment.Models.Emplayee.EmployeeDepartment;
import com.example.fon_classroommanagment.Models.Emplayee.EmployeeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.example.fon_classroommanagment.Configuration.Constants.ACCOUNT_TABLE_NAME;

@Entity
@Table(name = ACCOUNT_TABLE_NAME)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    private String email;
    private String firstName;

    private String lastName;

    @ManyToOne( optional = false)
    private  EmployeeDepartment department;

    @ManyToOne( optional = false)
    private  EducationTitle title;


    @ManyToOne( optional = false)
    private  EmployeeType type;


    private String password;



}