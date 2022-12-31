package com.example.firststringproject.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository){
        return args -> {
            Student kaoutar = new Student(1L,"kaoutar","kaoutarboudouft2@gmail.com", LocalDate.of(2001, Month.JULY,22),"$2a$10$cfSfevAflzUgE3XXRnv34O3jC58E.r3t4UWwWvIwY0ap0RkrDPzsi",AppStudentRole.Representative,false,true);
            Student tester = new Student(2L,"tester","tester@gmail.com", LocalDate.of(2001, Month.JULY,22),"abcd",AppStudentRole.Normal,false,true);
//            Student layla = new Student(2L,"layla","laylaboudouft2@gmail.com", LocalDate.of(1993, Month.JANUARY,10),"abcd",AppStudentRole.Normal,false,false);

            studentRepository.saveAll(List.of(kaoutar,tester));
        };
    }
}
