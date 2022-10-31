package com.example.firststringproject.student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

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
}
