package com.example.firststringproject.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents(){
        return studentService.getStudents();
    }

    @GetMapping(path ="{studentID}")
    public Student getStudentByID(@PathVariable("studentID") Long studentID){return studentService.getStudentByID(studentID);}


    @PostMapping("/newStudent")
    public void storeNewStudent(@RequestBody Student student){
        studentService.storeNewStudent(student);
    }

    @DeleteMapping(path = "/deleteStudent/{studentID}")
    public void destroyStory(@PathVariable("studentID") Long studentID){
        studentService.destroyStudent(studentID);
    }

    @PutMapping(path = "/updateStudent/{studentID}")
    public void updateStudent(@PathVariable("studentID") Long studentID,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) String email){
        studentService.updateStudent(studentID,name,email);
    }


}
