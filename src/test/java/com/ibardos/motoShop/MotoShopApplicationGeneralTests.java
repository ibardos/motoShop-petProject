package com.ibardos.motoShop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class MotoShopApplicationGeneralTests {
    @Autowired
    ApplicationContext applicationContext;

    /**
     * Check Application context is not empty, so dependencies are loaded.
     */
    @Test
    void contextLoads() {
        assertNotNull(applicationContext, "Application context is empty");

    }
}
