package com.example.fon_classroommanagment.Controllers;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.fon_classroommanagment.Configuration.SecurityConfiguration;
import com.example.fon_classroommanagment.Configuration.UserProfileDetails;
import com.example.fon_classroommanagment.Filters.UserFilter;
import com.example.fon_classroommanagment.FonClassroomManagmentApplication;
import com.example.fon_classroommanagment.Models.DTO.AccountDTO;
import com.example.fon_classroommanagment.Models.DTO.ChangePasswordDTO;
import com.example.fon_classroommanagment.Models.Emplayee.EducationTitle;
import com.example.fon_classroommanagment.Models.Emplayee.EmployeeDepartment;
import com.example.fon_classroommanagment.Models.Emplayee.EmployeeType;
import com.example.fon_classroommanagment.Models.User.Account;
import com.example.fon_classroommanagment.Models.User.UserProfile;
import com.example.fon_classroommanagment.Models.User.UserRole;
import com.example.fon_classroommanagment.Models.User.ValidationToken;
import com.example.fon_classroommanagment.Repository.UserRepository;
import com.example.fon_classroommanagment.Services.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.fon_classroommanagment.Configuration.Constants.SECRET;
import static com.example.fon_classroommanagment.Configuration.Constants.VALIDATION_TOKEN_EXPIRATION;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = TestAuthenticationController.class)
@AutoConfigureMockMvc

@ContextConfiguration(classes= {FonClassroomManagmentApplication.class, SecurityConfiguration.class})
public class TestAuthenticationController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;



    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private UserRepository userRepository;


@MockBean
private UserFilter userFilter;

@Test
public void Test_RegisterRoute_Exists() throws Exception {

    final String expectedResponseContent = ConvertObjectToJson(CreateValidAccountDTO());

    mockMvc.perform(post("/register")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(expectedResponseContent));
}

@Test
public void Test_RegistrationConfirmed_Exists() throws Exception {
    mockMvc.perform(get("/registerConfirmed/{token}","gergwe1232g")
           );
}


@Test
public void Test_ChangePassword_Exists() throws Exception {
    final String expectedResponseContent = ConvertObjectToJson(CreateChangePasswordDTOValid());

    mockMvc.perform(post("/ChangePassword")
            .contentType(MediaType.APPLICATION_JSON).content(expectedResponseContent));

}


@Test
public void Test_Register_Valid() throws Exception {

}
    private String CreateJWTToken(String role) {
        Algorithm algorithm=Algorithm.HMAC256(SECRET.getBytes());

        Set<SimpleGrantedAuthority> admin = Collections.singleton(new SimpleGrantedAuthority(role));
        return JWT.create()
                .withSubject("radojkovicika@gmail.com")
                .withExpiresAt(new Date( Calendar.getInstance().getTimeInMillis() + (VALIDATION_TOKEN_EXPIRATION)))
                .withClaim("roles",admin.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()) )
                .sign(algorithm);
    }
//@ParameterizedTest
//@MethodSource("getInvalidRegisterObjects")
//public void Test_InvalidBodyRegister(AccountDTO dto) throws Exception {
//    final String expectedResponseContent = ConvertObjectToJson(dto);
//
//    mockMvc.perform(post("/register")
//            .contentType(MediaType.APPLICATION_JSON)
//            .accept(MediaType.APPLICATION_JSON)
//            .content(expectedResponseContent)).andExpect(status().isBadRequest());
//}

private static Stream<Arguments> getInvalidRegisterObjects(){
    return   Stream.of(



            Arguments.of(new AccountDTO(
                    "ilija",
                    "radojkovic",
                    new EmployeeDepartment(1L,"cao"),
                    new EducationTitle(1L,"cao"),
                    new EmployeeType(1L,"cao"),
                    "radojkovic@gmail.com",
                    "1"
            )),
            Arguments.of(new AccountDTO(
                    "ilija",
                    "radojkovic",
                    new EmployeeDepartment(1L,"cao"),
                    new EducationTitle(1L,"cao"),
                    new EmployeeType(1L,"cao"),
                    "radojkovic@gmail.com",
                    "12"
            )),
            Arguments.of(new AccountDTO(
                    "ilija",
                    "radojkovic",
                    new EmployeeDepartment(1L,"cao"),
                    new EducationTitle(1L,"cao"),
                    new EmployeeType(1L,"cao"),
                    "radojkovic@gmail.com",
                    "123"
            )),
            Arguments.of(new AccountDTO(
                    "ilija",
                    "radojkovic",
                   null,
                    new EducationTitle(1L,"cao"),
                    new EmployeeType(1L,"cao"),
                    "radojkovic@gmail.com",
                    "1"
            )),
            Arguments.of(new AccountDTO(
                    "ilija",
                    "radojkovic",
                    new EmployeeDepartment(1L,"cao"),
                  null,
                    new EmployeeType(1L,"cao"),
                    "radojkovic@gmail.com",
                    "1"
            )),  Arguments.of(  new AccountDTO(
            "ilija",
            "radojkovic",
            new EmployeeDepartment(1L,"cao"),
            new EducationTitle(1L,"cao"),
           null,
            "radojkovic@gmail.com",
            "1"
    ))
    );
}
    private AccountDTO CreateInvalidRegisterDTO() {
        return  new AccountDTO(
                "ilija",
                "radojkovic",
                new EmployeeDepartment(1L,"cao"),
                new EducationTitle(1L,"cao"),
                new EmployeeType(1L,"cao"),
                "radojkovic@gmail.com",
                "1"
        );
    }




    private String ConvertObjectToJson(Object dto) throws JsonProcessingException {
    return objectMapper.writeValueAsString(dto);
    }

    private AccountDTO CreateValidAccountDTO() {
    return  new AccountDTO(
            "ilija",
            "radojkovic",
            new EmployeeDepartment(1L,"cao"),
            new EducationTitle(1L,"cao"),
            new EmployeeType(1L,"cao"),
            "radojkovic@gmail.com",
            "1234"
         );
    }
    private ChangePasswordDTO CreateChangePasswordDTOValid(){
    return  new ChangePasswordDTO("1234",new Date());
    }



}
