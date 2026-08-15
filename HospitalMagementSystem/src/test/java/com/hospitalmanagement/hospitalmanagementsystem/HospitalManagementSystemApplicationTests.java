package com.hospitalmanagement.hospitalmanagementsystem;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {"spring.sql.init.mode=never"})
class HospitalManagementSystemApplicationTests {

    @Test
    void contextLoads() {
    }

}
