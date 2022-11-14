package com.example.firststringproject.student;
import com.example.firststringproject.Registration.Token.ConfirmationToken;
import com.example.firststringproject.Registration.Token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class StudentService implements UserDetailsService {

    private final StudentRepository studentRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    public void storeNewStudent(Student student) {
        Optional<Student> studentByEmail=studentRepository.findStudentByEmail(student.getEmail());
        if (studentByEmail.isPresent()){
            throw new IllegalStateException("Email already taken !");
        }
        studentRepository.save(student);
    }

    public void destroyStudent(Long studentID) {
        boolean studentExist=studentRepository.existsById(studentID);
        if (!studentExist){
            throw new IllegalStateException("Student Already not exist !");
        }
        studentRepository.deleteById(studentID);
    }

    @Transactional
    public void updateStudent(Long studentID,String name,String email) {
        Student student = studentRepository.findById(studentID)
                .orElseThrow(()->new IllegalStateException("Student with id "+studentID+" doesn't exists !"));

        if(name!=null && name.length()>0 && !Objects.equals(name,student.getName())){
            student.setName(name);
        }

        if(email!=null && email.length()>0 && !Objects.equals(email,student.getEmail())){
            Optional<Student> studentByEmail=studentRepository.findStudentByEmail(email);
            if (studentByEmail.isPresent()){
                throw new IllegalStateException("Email already taken !");
            }
            student.setEmail(email);
        }
    }

    public Student getStudentByID(Long studentID) {
        return studentRepository.findById(studentID)
                .orElseThrow(() -> new IllegalStateException("Student with id " + studentID + " doesn't exists !"));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return studentRepository.findStudentByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User with email "+email+" not found!"));
    }

    public String signUpStudent(Student student){
        Optional<Student> studentByEmail=studentRepository.findStudentByEmail(student.getEmail());
        if (studentByEmail.isPresent()){
            throw new IllegalStateException("Email already taken !");
        }
        String encodedPassword=bCryptPasswordEncoder.encode(student.getPassword());
        student.setPassword(encodedPassword);
        studentRepository.save(student);

        String token=UUID.randomUUID().toString();
        confirmationTokenService.saveConfirmationToken(new ConfirmationToken(token, LocalDateTime.now(),LocalDateTime.now().plusMinutes(15),student));


        //send email
        return token;
    }

    public int enableStudent(String email) {
        return studentRepository.enableStudent(email);
    }
}
