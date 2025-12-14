package com.wesley.ValidadorHostnames;

import com.wesley.ValidadorHostnames.services.WhitelistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ValidadorHostnamesApplication implements CommandLineRunner {

	@Autowired
	private WhitelistService whitelistService;

	public static void main(String[] args) {
		SpringApplication.run(ValidadorHostnamesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//		whitelistService.isAllowed("api.example.com");
//		whitelistService.isAllowed("api.example.com");

		System.out.println(whitelistService.listaDeHosts());
		System.out.println(whitelistService.listaDeHosts());


	}

}
