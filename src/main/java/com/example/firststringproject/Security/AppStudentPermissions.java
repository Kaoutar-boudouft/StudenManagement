package com.example.firststringproject.Security;

public enum AppStudentPermissions {
    STUDENT_WRITE("student:write"),
    STUDENT_READ("student:read");

    private final String permission;
    AppStudentPermissions(String permission){
        this.permission=permission;
    }

    public String getPermission() {
        return permission;
    }
}
