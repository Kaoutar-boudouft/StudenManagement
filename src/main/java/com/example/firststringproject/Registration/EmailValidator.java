package com.example.firststringproject.Registration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class EmailValidator implements Predicate<String> {


    @Override
    public boolean test(String s) {
        return s.matches("^[a-zA-Z0-9_&.-]+@[a-zA-Z0-9.-]+$");
    }
}
