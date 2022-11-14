package com.example.firststringproject.Registration;


import com.example.firststringproject.Registration.Token.ConfirmationToken;
import com.example.firststringproject.Registration.Token.ConfirmationTokenRepository;
import com.example.firststringproject.Registration.Token.ConfirmationTokenService;
import com.example.firststringproject.student.AppStudentRole;
import com.example.firststringproject.student.Student;
import com.example.firststringproject.student.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final EmailValidator emailValidator;
    private final StudentService studentService;
    private final ConfirmationTokenService confirmationTokenService;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public String register(RegistrationRequest request) {
        Boolean isValidEmail=emailValidator.test(request.getEmail());
        if(!isValidEmail){
            throw new IllegalStateException("email not valid");
        }
        return studentService.signUpStudent(new Student(request.getName(),request.getEmail(),request.getPassword(), AppStudentRole.Normal));
    }

    @Transactional
    public String confirmToken(String token){
        ConfirmationToken confirmationToken=confirmationTokenService.getToken(token);
        if (confirmationToken.getConfirmedAt()!=null){
            throw new IllegalStateException("email already confirmed");
        }
        if(confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())){
            throw new IllegalStateException("token expired");
        }
        confirmationToken.setConfirmedAt(LocalDateTime.now());
        confirmationTokenRepository.save(confirmationToken);
        studentService.enableStudent(confirmationToken.getStudent().getEmail());

        return "confirmed";
    }
}
