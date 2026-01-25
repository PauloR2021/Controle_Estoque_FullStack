package com.pauloricardo.api;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {

        //Carregando o ENV
        Dotenv dotenv = Dotenv.configure()
                .filename(".env.test")
                .load();
        //Definindo Variaveis
        System.setProperty("SPRING_PROFILE", dotenv.get("SPRING_PROFILE"));
        System.setProperty("SERVER_PORT", dotenv.get("SERVER_PORT"));
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USER", dotenv.get("DB_USER"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        System.setProperty("EMAIL", dotenv.get("EMAIL"));
        System.setProperty("SENHA_GOOGLE", dotenv.get("SENHA_GOOGLE"));


		SpringApplication.run(ApiApplication.class, args);
	}

}
