package com.example.fon_classroommanagment.Models.Emplayee;


import com.example.fon_classroommanagment.TypeClass;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.example.fon_classroommanagment.Models.Configuration.Constants.EMPLOYEE_DEPARTMENT_TABLE_NAME;


@Entity
@Table(name = EMPLOYEE_DEPARTMENT_TABLE_NAME)
@NoArgsConstructor
public class EmployeeDepartment extends TypeClass {

    public EmployeeDepartment(Long id, String name) {
        super(id, name);
    }


}