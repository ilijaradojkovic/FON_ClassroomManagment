package com.example.fon_classroommanagment.Models.classroom;

import com.example.fon_classroommanagment.FonClassroomManagmentApplication;
import com.example.fon_classroommanagment.Models.DTO.classroom.RequestClassroomDetailsDTO;
import com.example.fon_classroommanagment.Models.ModelTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.stream.Stream;

@SpringBootTest(
        classes = TestRequestClassroomDetailsDTO.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes= FonClassroomManagmentApplication.class)
public class TestRequestClassroomDetailsDTO extends ModelTest<RequestClassroomDetailsDTO> {
    @Override
    @ParameterizedTest
    @MethodSource("generateValid")
    protected void TestValid(RequestClassroomDetailsDTO entity) {
        Assertions.assertTrue(validator.validateProperty(entity,"id").isEmpty());

    }

    @Override
    @ParameterizedTest
    @MethodSource("generateInvalid")
    protected void TestInvalid(RequestClassroomDetailsDTO entity) {
        Assertions.assertFalse(validator.validateProperty(entity,"id").isEmpty());

    }

    private static Stream<Arguments> generateValid(){
        return  Stream.of(
                Arguments.of(new RequestClassroomDetailsDTO(1L))
        );
    }

    private static Stream<Arguments> generateInvalid(){
        return  Stream.of(
                Arguments.of(new RequestClassroomDetailsDTO(-1L))

        );
    }
}
