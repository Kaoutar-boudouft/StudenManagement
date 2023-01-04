package com.example.firststringproject.student;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table
public class Student implements UserDetails {

    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )

    private Long id;
    private String name;
    private String email;

    private String password;
    private LocalDate dob;

    @Transient
    private Integer age;

    @Enumerated(EnumType.STRING)
    private AppStudentRole appStudentRole;

    private Boolean locked=false;

    private Boolean enabled=false;


    public Student(String name, String email, LocalDate dob, String password, AppStudentRole appStudentRole, Boolean locked, Boolean enabled) {
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.password = password;
        this.appStudentRole = appStudentRole;
        this.locked = locked;
        this.enabled = enabled;
    }

    public Student(Long id, String name, String email, LocalDate dob, String password, AppStudentRole appStudentRole, Boolean locked, Boolean enabled) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.password = password;
        this.appStudentRole = appStudentRole;
        this.locked = locked;
        this.enabled = enabled;
    }

    public Student(String name, String email, String password, AppStudentRole appStudentRole,LocalDate dob) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.appStudentRole = appStudentRole;
        this.dob=dob;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return Period.between(this.dob, LocalDate.now()).getYears();
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }



    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", dob=" + dob +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appStudentRole.name());
        return Collections.singletonList(authority);
    }
    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions=appStudentRole.getPermissions().stream()
                .map(appStudentPermissions -> new SimpleGrantedAuthority(appStudentPermissions.getPermission()))
                .collect(Collectors.toSet());
        return permissions;
    }

}