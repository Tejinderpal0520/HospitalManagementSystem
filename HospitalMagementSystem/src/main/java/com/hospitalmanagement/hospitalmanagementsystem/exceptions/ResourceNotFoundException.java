package com.hospitalmanagement.hospitalmanagementsystem.exceptions;


public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String departmentNotFound) {
        super(departmentNotFound);
    }

}
