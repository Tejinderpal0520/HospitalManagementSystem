package com.hospitalmanagement.hospitalmanagementsystem.enums;

public enum UsersRoleEnum {
    ADMIN,
    DOCTOR,
    RECEPTIONIST,
    PATIENT;

    public String getAuthority() {
        return "Role_" + this.name();
    }
}
