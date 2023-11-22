package com.ibardos.motoShop;

import com.ibardos.motoShop.data.DatabaseManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MotoShopApplication {

    public static void main(String[] args) {
        DatabaseManager.initialiseDatabase();

        SpringApplication.run(MotoShopApplication.class, args);
    }
}