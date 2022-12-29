package com.example.firststringproject.Registration;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {

    private final String name;
    private final String email;
    private final String password;
    private final LocalDate dob;

    public String validateRequest(){
        String result="";
        if (this.getName()==null){
            result="Name required !";
        } else if (this.getEmail()==null) {
            result="Email required !";
        }
         else if (this.getPassword()==null) {
            result="Password required !";
        }
         else if (this.getDob()==null) {
            result="Date of birth required !";
        }
        return result;
    }

}
