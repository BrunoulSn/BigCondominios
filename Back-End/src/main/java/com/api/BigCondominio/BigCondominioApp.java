package com.api.BigCondominio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class BigCondominioApp {

	public static void main(String[] args) {SpringApplication.run(BigCondominioApp.class, args);

	}

	@GetMapping("/")
	public String index() {
		return "Ola mundo";
}

}
