package com.example.firststringproject.Registration;


import com.example.firststringproject.Email.EmailSender;
import com.example.firststringproject.Email.EmailService;
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

    private final EmailService emailService;
    public String register(RegistrationRequest request) {
        Boolean isValidEmail=emailValidator.test(request.getEmail());
        if(!isValidEmail){
            throw new IllegalStateException("email not valid");
        }
        String token= studentService.signUpStudent(new Student(request.getName(),request.getEmail(),request.getPassword(), AppStudentRole.Normal));

        String link="http://localhost:8080/api/v1/registration/confirm?token="+token;
        emailService.send(request.getEmail(),"<a href='"+link+"'>click to confirm</a>");

        return token;
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
