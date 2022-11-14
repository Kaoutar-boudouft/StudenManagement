package com.example.firststringproject.Registration;


import com.example.firststringproject.student.AppStudentRole;
import com.example.firststringproject.student.Student;
import com.example.firststringproject.student.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final EmailValidator emailValidator;
    private final StudentService studentService;

    public String register(RegistrationRequest request) {
        Boolean isValidEmail=emailValidator.test(request.getEmail());
        if(!isValidEmail){
            throw new IllegalStateException("email not valid");
        }
        return studentService.signUpStudent(new Student(request.getName(),request.getEmail(),request.getPassword(), AppStudentRole.Normal));
    }
}
