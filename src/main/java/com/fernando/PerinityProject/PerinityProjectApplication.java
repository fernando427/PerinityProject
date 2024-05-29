package com.fernando.PerinityProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class PerinityProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(PerinityProjectApplication.class, args);
	}

}
