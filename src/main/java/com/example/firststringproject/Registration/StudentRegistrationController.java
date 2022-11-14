package com.example.firststringproject.Registration;

import com.example.firststringproject.student.Student;
import com.example.firststringproject.student.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class StudentRegistrationController {

    private final RegistrationService registrationService;


    @PostMapping
    public String Register(@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }


}
