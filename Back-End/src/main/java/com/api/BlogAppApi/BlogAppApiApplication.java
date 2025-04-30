package com.api.BlogAppApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class BlogAppApiApplication {

	public static void main(String[] args) {SpringApplication.run(BlogAppApiApplication.class, args);

	}

	@GetMapping("/")
	public String index() {
		return "Ola mundo";
}

}
