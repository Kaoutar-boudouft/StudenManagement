package com.example.firststringproject.Registration;

import com.example.firststringproject.student.Student;
import com.example.firststringproject.student.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class StudentRegistrationController {

    private final RegistrationService registrationService;


    @PostMapping
    public String Register(@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }

    @GetMapping(path = "confirm")
    public String ConfirmEmail(@RequestParam("token") String token){
      return   registrationService.confirmToken(token);
    }

}
