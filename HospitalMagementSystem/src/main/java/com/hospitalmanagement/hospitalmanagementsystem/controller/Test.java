package com.hospitalmanagement.hospitalmanagementsystem.controller;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/testMap")
public class Test {

    @GetMapping
    public String testingApi(){
        System.out.println("Parveen Kaur");
        return "Returned form test api";
    }
}
