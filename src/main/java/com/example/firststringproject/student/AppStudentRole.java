package com.example.firststringproject.student;

import com.example.firststringproject.Security.AppStudentPermissions;

import java.util.Set;
import com.google.common.collect.Sets;
public enum AppStudentRole {
    Normal(Sets.newHashSet(AppStudentPermissions.STUDENT_READ)),
    Representative(Sets.newHashSet(AppStudentPermissions.STUDENT_READ,AppStudentPermissions.STUDENT_WRITE));

    private final Set<AppStudentPermissions> permissions;

    AppStudentRole(Set<AppStudentPermissions> permissions) {
        this.permissions = permissions;
    }
    public Set<AppStudentPermissions> getPermissions() {
        return permissions;
    }
}
