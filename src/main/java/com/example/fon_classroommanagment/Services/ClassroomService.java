package com.example.fon_classroommanagment.Services;


import com.example.fon_classroommanagment.Configuration.ExceptionMessages;
import com.example.fon_classroommanagment.Exceptions.ClassroomExistsException;
import com.example.fon_classroommanagment.Models.Appointment.Appointment;
import com.example.fon_classroommanagment.Models.Appointment.AppointmentType;
import com.example.fon_classroommanagment.Models.Classroom.Classroom;
import com.example.fon_classroommanagment.Models.Classroom.ClassroomType;
import com.example.fon_classroommanagment.Models.DTO.*;
import com.example.fon_classroommanagment.Repository.AppointmentRepository;
import com.example.fon_classroommanagment.Repository.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.security.KeyPair;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.fon_classroommanagment.Configuration.Constants.*;

@Service
public class ClassroomService {
    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

//    public List<ClassroomCardDTO> filter(FilterDTO filterDTO) {
//
//
//        return CreateClassroomPagingDTOs(result);
//    }

    public List<ClassroomCardDTO> searchClassroom(int page,String name)  {
        //if(classrooms.isEmpty()) throw new ClassroomExistsException("Classroom with that name doesn't exist");
       List<Classroom> result= classroomRepository.findByNameContaining("%"+name+"%",PageRequest.of(page-1, PAGE_SIZE)).getContent();
        return CreateClassroomPagingDTOs(result);


    }

    private   List<ClassroomCardDTO> CreateClassroomPagingDTOs(List<Classroom> resultQuery ){
        return resultQuery.stream().map(x->new ClassroomCardDTO(x.getId(),x.getName(),x.getNumber_of_seats(),x.isProjector(),x.getType().getName().equals(RC_TYPE_NAME))).collect(Collectors.toList());

    }

    public ClassroomDetailsDTO classroomDetails(Long classroomId) throws ClassroomExistsException {
        Optional<Classroom> optional = classroomRepository.findById(classroomId);
        if (optional.isEmpty()) throw new ClassroomExistsException(ExceptionMessages.CLASSROOM_EXISTS);
        Classroom classroom = optional.get();
        HashMap<Pair<String,Integer>,Double> monthsPercentage=new HashMap<>(){{
            put(Pair.of("January",1), 0.0);
            put(Pair.of("February",2), 0.0);
            put(Pair.of("March",3),0.0);
            put(Pair.of("April",4),0.0);
            put(Pair.of("May",5), 0.0);
            put(Pair.of("June",6), 0.0);
            put(Pair.of("July",7),0.0);
            put(Pair.of("August",8), 0.0);
            put(Pair.of("September",9), 0.0);
            put(Pair.of("October",10),0.0);
            put(Pair.of("November",11),0.0);
            put(Pair.of("December",12),0.0);

        }};

        for (Map.Entry<Pair<String, Integer>, Double> ele: monthsPercentage.entrySet()
             ) {
           // System.out.println(appointmentRepository.reservationsByMonths(classroomId,ele.));
            int countApp=appointmentRepository.reservationsByMonths(classroomId,ele.getKey().getSecond());
            double calculated=(countApp/180.0)* 100;
            ele.setValue(calculated);


        };

//        for (Double[] month : monthsPercentage) {
//            for (int i = 1; i < month.length; i++) {
//                month[i] = (month[i] / 310) * 100;
//
//            }
//        }

        return new  ClassroomDetailsDTO(classroom.getName(),
                classroom.getNumber_of_seats(),
                classroom.getNumber_of_computers(),
                classroom.isAircondition(),
                classroom.isProjector(),
                classroom.getType(),
                classroom.getPovrsina(),
                classroom.getSprat(),
                classroom.getBr_tabli(), monthsPercentage);



    }


    public List<ClassroomCardDTO> getAllClassrooms(int page,FilterDTO filterDTO) {

        Page<Classroom> all;
        if(filterDTO.isSortByCapacity())
            all=classroomRepository.findAllSortedCapacity(PageRequest.of(page, PAGE_SIZE),filterDTO.getMin_capacity(),filterDTO.getMax_capacity() ,filterDTO.isAircondition(),filterDTO.isProjector(),filterDTO.getTypes());
        else all = classroomRepository.findAll(PageRequest.of(page, PAGE_SIZE),filterDTO.getMin_capacity(),filterDTO.getMax_capacity() ,filterDTO.isAircondition(),filterDTO.isProjector(),filterDTO.getTypes());
        return CreateClassroomPagingDTOs(all.getContent());
    }
    public List<GetForDateAppointmentDTO> getForDateClassroom(RequestIsClassroomAvailableForDateDTO requestAppointmetDateDTO) throws ClassroomExistsException {

    Optional<Classroom> classroomOpt=classroomRepository.findById(requestAppointmetDateDTO.getClassroomId());

    if(classroomOpt.isPresent()){
        Classroom classroom=classroomOpt.get();

      return getForDateAppointmentDTOS(appointmentRepository.findByDateAndClassroom(requestAppointmetDateDTO.getDate(), classroom));

    }
        throw new ClassroomExistsException(ExceptionMessages.CLASSROOM_EXISTS);
    }
    private List<GetForDateAppointmentDTO> getForDateAppointmentDTOS(   List<Appointment> appointments){
        return appointments.stream().map(x->new GetForDateAppointmentDTO(x.getStart_timeInHours(),x.getEnd_timeInHours(),x.getType().getName(),x.getClassroom().getName(),x.getDecription())).collect(Collectors.toList());
    }


    public List<ClassroomType> getAllClassroomTypes() {
        return classroomRepository.getAllClassroomTypes();
    }

    public List<AppointmentType> getAllAppointmentTypes() {
        return appointmentRepository.getAllAppointmentTypes();
    }

    public List<ClassroomChipDTO> getClassroomsAsChips(String name) {

      List<Classroom> result= classroomRepository.findByNameChips("%"+name+"%", Pageable.ofSize(CHIP_SEARCH_ELEMENTS)).getContent();
return CreateClassroomChipDTOs(result);
    }
    public List<ClassroomChipDTO> getAllClassroomsAsChips(int page) {

        Page<ClassroomChipDTO> all = classroomRepository.findAll(PageRequest.of(page-1, PAGE_SIZE)).map(x-> new ClassroomChipDTO(x.getId(),x.getName()));
        return all.getContent();
    }

    private   List<ClassroomChipDTO> CreateClassroomChipDTOs(List<Classroom> resultQuery ){
        return resultQuery.stream().map(x->new ClassroomChipDTO(x.getId(),x.getName())).collect(Collectors.toList());

    }


}
