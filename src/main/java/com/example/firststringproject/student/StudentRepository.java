package com.example.firststringproject.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface StudentRepository extends JpaRepository<Student,Long> {

   // @Query("SELECT s FROM Student s WHERE s.email = ?1 ")
    Optional<Student> findStudentByEmail(String email);

}
